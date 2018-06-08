package org.gmjm.slack.core.file;

import java.io.InputStream;
import java.util.function.Supplier;

import org.gmjm.slack.api.file.FileType;
import org.gmjm.slack.api.file.FileUpload;
import org.gmjm.slack.api.file.FileUploadBuilder;
import org.gmjm.slack.api.file.FileUploadRequest;
import org.gmjm.slack.api.file.FileUploadRequestFactory;
import org.gmjm.slack.api.file.FileUploadResponse;
import org.junit.Test;

import static org.gmjm.slack.core.common.ChannelImpl.*;
import static org.junit.Assert.*;

public class FileUploadTest {

    private String testChannel = System.getenv("SLACK_TEST_CHANNEL_NAME");
    private String token = System.getenv("SLACK_BOT_TOKEN");

    @Test
    public void testContentUpload() throws Throwable {
        FileUploadRequestFactory uploadRequestFactory = new HttpsFileUploadRequestFactory(token);

        FileUploadRequest uploadRequest = uploadRequestFactory.createFileUploadRequest();

        FileUploadBuilder uploadBuilder = uploadRequestFactory.createFileUploadBuilder();

        uploadBuilder
            .setChannels(withName(testChannel))
            .setTitle("Hello Title")
            .setFiletype(FileType.TEXT)
            .setFilename("hello_file_upload.txt")
            .setContent("Hello, this is the file upload content.")
            .setInitialComment("Hello initial comment.");

        FileUploadResponse response = uploadRequest.upload(uploadBuilder.build());

        if (response.getStatus() == FileUploadResponse.Status.FAILED) {
            throw response.getThrowable();
        }

        assertEquals(FileUploadResponse.Status.SUCCESS, response.getStatus());
    }

    @Test
    public void testFileUpload() throws Throwable {
        FileUploadRequestFactory uploadRequestFactory = new HttpsFileUploadRequestFactory(token);

        Supplier<InputStream> inputStreamSupplier = () -> this.getClass().getResourceAsStream("/uploads/cat.jpg");

        FileUpload fileUpload =
            uploadRequestFactory.createFileUploadBuilder()
                .setChannels(withName(testChannel))
                .setTitle("Hello Cat")
                .setFiletype("jpg")
                .setFilename("hello_cat.jpg")
                .setInputStreamSupplier(inputStreamSupplier)
                .setInitialComment("It's cat time!")
                .build();

        FileUploadResponse response =
            uploadRequestFactory
                .createFileUploadRequest()
                .upload(fileUpload);

        if (response.getStatus() == FileUploadResponse.Status.FAILED) {
            throw response.getThrowable();
        }

        assertEquals(FileUploadResponse.Status.SUCCESS, response.getStatus());
        assertEquals("Hello Cat", response.getFileUpload().getOTitle().get());
    }

}
