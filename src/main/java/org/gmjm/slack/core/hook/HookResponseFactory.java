package org.gmjm.slack.core.hook;

import org.gmjm.slack.api.hook.HookResponse;

class HookResponseFactory {

	static HookResponse success(String webhookUrl, String sentMessage, String receivedMessage, int statusCode) {
		return new HookResponseImpl(webhookUrl, sentMessage, receivedMessage, statusCode, HookResponse.Status.SUCCESS, null);
	}

	static HookResponse fail(String webhookUrl, String sentMessage, String receivedMessage, int statusCode, Throwable throwable) {
		return new HookResponseImpl(webhookUrl, sentMessage, receivedMessage, statusCode, HookResponse.Status.FAILED, throwable);
	}

	private static class HookResponseImpl implements HookResponse {

		String webhookUrl;
		private String sentMessage;
		private String receivedMessage;
		private int statusCode;
		private Status status;
		private Throwable throwable;

		public HookResponseImpl(
			String webhookUrl,
			String sentMessage,
			String receivedMessage,
			int statusCode,
			Status status,
			Throwable throwable
		) {
			this.webhookUrl = webhookUrl;
			this.sentMessage = sentMessage;
			this.receivedMessage = receivedMessage;
			this.statusCode = statusCode;
			this.status = status;
			this.throwable = throwable;
		}

		@Override
		public String getWebhookUrl() {
			return webhookUrl;
		}

		@Override
		public String getSentMessage() {
			return sentMessage;
		}

		@Override
		public String getReceivedMessage() {
			return receivedMessage;
		}

		@Override
		public String getMessage() {
			return receivedMessage;
		}

		@Override
		public int getStatusCode() {
			return statusCode;
		}

		@Override
		public Status getStatus() {
			return status;
		}

		@Override
		public Throwable getThrowable() {
			return throwable;
		}
	}
}
