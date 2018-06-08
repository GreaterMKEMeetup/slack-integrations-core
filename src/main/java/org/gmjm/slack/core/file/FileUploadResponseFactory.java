package org.gmjm.slack.core.file;

import org.gmjm.slack.api.file.FileUpload;
import org.gmjm.slack.api.file.FileUploadResponse;

import static org.gmjm.slack.api.file.FileUploadResponse.Status.*;

class FileUploadResponseFactory {

	static FileUploadResponse success(FileUpload fileUpload, int responseCode) {
		return new FileUploadResponseImpl(fileUpload, "Upload Successful", responseCode, SUCCESS, null);
	}

	static FileUploadResponse success(FileUpload fileUpload, int responseCode, String message) {
		return new FileUploadResponseImpl(fileUpload, message, responseCode, SUCCESS, null);
	}

	static FileUploadResponse fail(FileUpload fileUpload, Throwable throwable) {
		return new FileUploadResponseImpl(fileUpload, throwable.getMessage(), 500, FAILED, throwable);
	}

	static FileUploadResponse fail(FileUpload fileUpload, String message, int statusCode) {
		return new FileUploadResponseImpl(fileUpload, message, statusCode, FAILED, null);
	}

	static FileUploadResponse fail(FileUpload fileUpload, String message, int statusCode, Throwable throwable) {
		return new FileUploadResponseImpl(fileUpload, message, statusCode, FAILED, throwable);
	}

	private static class FileUploadResponseImpl implements FileUploadResponse {

		private final FileUpload fileUpload;
		private final String message;
		private final int statusCode;
		private final Status status;
		private final Throwable throwable;

		public FileUploadResponseImpl(
			FileUpload fileUpload,
			String message,
			int statusCode,
			Status status,
			Throwable throwable) {

			this.fileUpload = fileUpload;
			this.message = message;
			this.statusCode = statusCode;
			this.status = status;
			this.throwable = throwable;
		}

		@Override
		public FileUpload getFileUpload() {
			return this.fileUpload;
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
			sb.append("fileUpload=").append(fileUpload);
			sb.append(", message='").append(message).append('\'');
			sb.append(", statusCode=").append(statusCode);
			sb.append(", status=").append(status);
			sb.append(", throwable=").append(throwable);
			sb.append('}');
			return sb.toString();
		}
	}
}
