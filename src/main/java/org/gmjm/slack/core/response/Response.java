package org.gmjm.slack.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

	private boolean ok;
	private String warning;
	private String error;

	public Response() {}

	public boolean isOk() {
		return ok;
	}

	public boolean hasError() {
		return error != null;
	}

	public boolean hasWarning() {
		return warning != null;
	}

	public String getWarning() {
		return warning;
	}

	public String getError() {
		return error;
	}
}
