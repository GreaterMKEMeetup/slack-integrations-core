package org.gmjm.slack.core.file;

import org.gmjm.slack.api.file.FileUploadResponse;

import static org.gmjm.slack.api.file.FileUploadResponse.Status.*;

public class FileUploadResponseFactory {

	static FileUploadResponse success(int responseCode) {
		return new FileUploadResponseImpl("Upload Successful", responseCode, SUCCESS, null);
	}

	static FileUploadResponse success(int responseCode, String message) {
		return new FileUploadResponseImpl(message, responseCode, SUCCESS, null);
	}

	static FileUploadResponse fail(Throwable throwable) {
		return new FileUploadResponseImpl(throwable.getMessage(), 500, FAILED, throwable);
	}

	static FileUploadResponse fail(String message, int statusCode) {
		return new FileUploadResponseImpl(message, statusCode, FAILED, null);
	}

	static FileUploadResponse fail(String message, int statusCode, Throwable throwable) {
		return new FileUploadResponseImpl(message, statusCode, FAILED, throwable);
	}

	private static class FileUploadResponseImpl implements FileUploadResponse {

		private String message;
		private int statusCode;
		private Status status;
		private Throwable throwable;

		public FileUploadResponseImpl(String message, int statusCode, Status status, Throwable throwable) {
			this.message = message;
			this.statusCode = statusCode;
			this.status = status;
			this.throwable = throwable;
		}

		@Override
		public String getMessage() {
			return this.message;
		}

		@Override
		public int getStatusCode() {
			return this.statusCode;
		}

		@Override
		public Status getStatus() {
			return this.status;
		}

		@Override
		public Throwable getThrowable() {
			return this.throwable;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("FileUploadResponseImpl{");
			sb.append("message='").append(message).append('\'');
			sb.append(", statusCode=").append(statusCode);
			sb.append(", status=").append(status);
			sb.append(", throwable=").append(throwable);
			sb.append('}');
			return sb.toString();
		}
	}
}
