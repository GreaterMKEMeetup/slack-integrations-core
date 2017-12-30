package org.gmjm.slack.core.file;

import java.io.InputStream;

import org.gmjm.slack.api.file.FileType;
import org.gmjm.slack.api.file.FileUploadBuilder;
import org.gmjm.slack.api.file.FileUploadRequest;
import org.gmjm.slack.api.file.FileUploadRequestFactory;
import org.gmjm.slack.api.file.FileUploadResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileUploadTest {

	private String token = System.getenv("slack.token");

	@Test
	public void testContentUpload() throws Throwable {
		FileUploadRequestFactory uploadRequestFactory = new HttpsFileUploadRequestFactory(token);

		FileUploadRequest uploadRequest = uploadRequestFactory.createFileUploadRequest();

		FileUploadBuilder uploadBuilder = uploadRequestFactory.createFileUploadBuilder();

		uploadBuilder
			.setChannels("general")
			.setTitle("Hello Title")
			.setFiletype(FileType.TEXT)
			.setFilename("hello_file_upload.txt")
			.setContent("Hello, this is the file upload content.")
			.setInitialComment("Hello initial comment.");

		FileUploadResponse response = uploadRequest.upload(uploadBuilder);

		if(response.getStatus() == FileUploadResponse.Status.FAILED) {
			System.out.println(response);
		}

		assertEquals(FileUploadResponse.Status.SUCCESS, response.getStatus());
	}

	@Test
	public void testFileUpload() throws Throwable {
		FileUploadRequestFactory uploadRequestFactory = new HttpsFileUploadRequestFactory(token);

		FileUploadRequest uploadRequest = uploadRequestFactory.createFileUploadRequest();

		FileUploadBuilder uploadBuilder = uploadRequestFactory.createFileUploadBuilder();

		InputStream inputStream = this.getClass().getResourceAsStream("/uploads/cat.jpg");


		uploadBuilder
			.setChannels("general")
			.setTitle("Hello Cat")
			.setFiletype("jpg")
			.setFilename("hello_cat.jpg")
			.setFile(inputStream)
			.setInitialComment("It's cat time!");

		FileUploadResponse response = uploadRequest.upload(uploadBuilder);

		if(response.getStatus() == FileUploadResponse.Status.FAILED) {
			System.out.println(response);
		}

		assertEquals(FileUploadResponse.Status.SUCCESS, response.getStatus());
	}

}
