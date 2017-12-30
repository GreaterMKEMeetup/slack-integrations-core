package org.gmjm.slack.core.file;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.gmjm.slack.api.file.FileUploadBuilder;
import org.gmjm.slack.api.file.FileUploadRequest;
import org.gmjm.slack.api.file.FileUploadRequestFactory;

public class HttpsFileUploadRequestFactory implements FileUploadRequestFactory {

	public static final String DEFAULT_FILE_UPLOAD_URL = "https://slack.com/api/files.upload";

	private final String token;
	private final String fileUploadUrl;
	private final HttpClient httpClient;

	public HttpsFileUploadRequestFactory(String token) {
		this.token = token;
		this.fileUploadUrl = DEFAULT_FILE_UPLOAD_URL;
		this.httpClient = defaultClient();
	}

	public HttpsFileUploadRequestFactory(String token, String fileUploadUrl) {
		this.token = token;
		this.fileUploadUrl = fileUploadUrl;
		this.httpClient = defaultClient();
	}

	public HttpsFileUploadRequestFactory(String token, String fileUploadUrl, HttpClient httpClient) {
		this.token = token;
		this.fileUploadUrl = fileUploadUrl;
		this.httpClient = httpClient;
	}

	private HttpClient defaultClient() {
		return HttpClientBuilder.create().build();
	}

	@Override
	public FileUploadRequest createFileUploadRequest() {
		return new FileUploadRequestImpl(httpClient, token, this.fileUploadUrl);
	}

	public FileUploadBuilder createFileUploadBuilder() {
		return new FileUploadBuilderImpl();
	}
}
