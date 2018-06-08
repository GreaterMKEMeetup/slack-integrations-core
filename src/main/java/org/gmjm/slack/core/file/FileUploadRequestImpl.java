package org.gmjm.slack.core.file;

import java.io.IOException;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.gmjm.slack.api.common.Channel;
import org.gmjm.slack.api.file.FileUpload;
import org.gmjm.slack.api.file.FileUploadRequest;
import org.gmjm.slack.api.file.FileUploadResponse;
import org.gmjm.slack.core.response.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

class FileUploadRequestImpl implements FileUploadRequest {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private final String token;
	private final String fileUploadUrl;
	private final HttpClient httpClient;

	public FileUploadRequestImpl(HttpClient httpClient, String token, String fileUploadUrl) {
		this.token = token;
		this.fileUploadUrl = fileUploadUrl;
		this.httpClient = httpClient;
	}

	@Override
	public FileUploadResponse upload(FileUpload fileUpload) {

		try {

			HttpPost post = new HttpPost(fileUploadUrl);

			writePostBody(post, fileUpload);

			HttpResponse httpResponse = httpClient.execute(post);

			String rawResponse = IOUtils.toString(httpResponse.getEntity().getContent());

			Response response = OBJECT_MAPPER.readValue(rawResponse, Response.class);

			int responseCode = httpResponse.getStatusLine().getStatusCode();

			if(response.isOk()) {
				if(response.hasWarning()) {
					FileUploadResponseFactory.success(fileUpload, responseCode, response.getWarning());
				}
				return FileUploadResponseFactory.success(fileUpload, responseCode);
			} else {
				if(response.hasError()) {
					return FileUploadResponseFactory.fail(fileUpload, response.getError(), responseCode);
				} else if (response.hasWarning()) {
					return FileUploadResponseFactory.fail(fileUpload, response.getWarning(), responseCode);
				} else {
					return FileUploadResponseFactory.fail(fileUpload, "Unknown Error", responseCode);
				}
			}

		}
		catch (Exception e) {
			return FileUploadResponseFactory.fail(fileUpload, e.getMessage(), 500, e);
		}
	}

	void writePostBody(HttpPost post, FileUpload fileUpload) throws IOException {
		if(fileUpload.getOContent().isPresent()) {
			post.setHeader("Content-Type","application/x-www-form-urlencoded");

			LinkedList<NameValuePair> list = new LinkedList<>();

			list.add(new BasicNameValuePair("token",token));

			addParts((key, value) -> {
				list.add(new BasicNameValuePair(key, value));
				return null;
			}, fileUpload);

			HttpEntity entity = new UrlEncodedFormEntity(list);

			post.setEntity(entity);
		} else if (fileUpload.getOInputStreamSupplier().isPresent()){
			post.setHeader("ContentType","multipart/form-data");
			MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create()
				.addTextBody("token", token)
				.addBinaryBody(
					"file",
					fileUpload
						.getOInputStreamSupplier()
						.get()
						.get(),
					ContentType.APPLICATION_OCTET_STREAM, "filename");

			addParts((key, value) -> {
				multipartBuilder.addTextBody(key, value);
				return null;
			}, fileUpload);

			post.setEntity(multipartBuilder.build());
		} else {
			throw new RuntimeException("No content, or file specified.");
		}
	}

	static void addParts(BiFunction<String,String,Void> partAdder, FileUpload fileUpload) {

		fileUpload.getOFileName().ifPresent(fileName -> {
			partAdder.apply("filename", fileName);
		});

		fileUpload.getOContent().ifPresent(content -> {
			partAdder.apply("content", content);
		});

		fileUpload.getOFileType().ifPresent(fileType -> {
			partAdder.apply("filetype", fileType);
		});

		fileUpload.getOInitialComment().ifPresent(initialComment -> {
			partAdder.apply("initial_comment", initialComment);
		});

		fileUpload.getOTitle().ifPresent(title -> {
			partAdder.apply("title", title);
		});

		String joinedChannelNames = joinChannelNames(fileUpload);

		partAdder.apply("channels", joinedChannelNames);
	}



	private static String joinChannelNames(FileUpload fileUpload) {
		return fileUpload
			.getChannels().stream()
			.map(Channel::getIdentifier)
			.collect(Collectors.joining(","));
	}



}
