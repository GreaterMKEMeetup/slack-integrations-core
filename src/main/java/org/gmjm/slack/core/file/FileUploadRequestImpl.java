package org.gmjm.slack.core.file;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.gmjm.slack.api.file.FileUploadBuilder;
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
	public FileUploadResponse upload(FileUploadBuilder fileUploadBuilder) {

		try {

			FileUploadBuilderImpl builder = (FileUploadBuilderImpl) fileUploadBuilder;

			HttpPost post = new HttpPost(fileUploadUrl);

			writePostBody(post, builder);

			HttpResponse httpResponse = httpClient.execute(post);

			String rawResponse = IOUtils.toString(httpResponse.getEntity().getContent());

			Response response = OBJECT_MAPPER.readValue(rawResponse, Response.class);

			int responseCode = httpResponse.getStatusLine().getStatusCode();

			if(response.isOk()) {
				if(response.hasWarning()) {
					FileUploadResponseFactory.success(responseCode, response.getWarning());
				}
				return FileUploadResponseFactory.success(responseCode);
			} else {
				if(response.hasError()) {
					return FileUploadResponseFactory.fail(response.getError(), responseCode);
				} else if (response.hasWarning()) {
					return FileUploadResponseFactory.fail(response.getWarning(), responseCode);
				} else {
					return FileUploadResponseFactory.fail("Unknown Error", responseCode);
				}
			}

		}
		catch (Exception e) {
			return FileUploadResponseFactory.fail(e.getMessage(), 500, e);
		}
	}

	void writePostBody(HttpPost post, FileUploadBuilderImpl builder) throws IOException {
		if(builder.params.get("content") != null) {
			post.setHeader("Content-Type","application/x-www-form-urlencoded");
			HttpEntity entity = new UrlEncodedFormEntity(getNameValuePairs(token, builder));
			post.setEntity(entity);
		} else if (builder.fileInputStream != null){
			post.setHeader("ContentType","multipart/form-data");
			MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create()
				.addTextBody("token", token)
				.addBinaryBody("file", builder.fileInputStream, ContentType.APPLICATION_OCTET_STREAM, "filename");

			addTextBodies(multipartBuilder, builder);

			post.setEntity(multipartBuilder.build());
		} else {
			throw new RuntimeException("No content, or file specified.");
		}
	}

	static void addTextBodies(MultipartEntityBuilder multipartEntityBuilder, FileUploadBuilderImpl fileUploadBuilder) {
		for(Map.Entry<String,String> entry : fileUploadBuilder.params.entrySet()) {
			if(entry.getValue() != null)
				multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue());
		}
	}

	static List<NameValuePair> getNameValuePairs(String token, FileUploadBuilderImpl fileUploadBuilder) throws IOException {

		LinkedList<NameValuePair> list = new LinkedList<>();

		list.add(new BasicNameValuePair("token",token));

		for(Map.Entry<String,String> entry : fileUploadBuilder.params.entrySet()) {
			if(entry.getValue() != null)
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		return list;
	}

}
