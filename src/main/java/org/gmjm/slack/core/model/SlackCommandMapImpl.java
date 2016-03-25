package org.gmjm.slack.core.model;

import java.util.Map;

import org.gmjm.slack.api.model.SlackCommand;

public class SlackCommandMapImpl implements SlackCommand
{
	private final Map<String,String> requestParams;

	public SlackCommandMapImpl(Map<String, String> requestParams)
	{
		this.requestParams = requestParams;
	}

	@Override
	public String getText() {
		return get("text");
	}

	@Override
	public String getUserName() {
		return get("user_name");
	}

	@Override
	public String getToken() {
		return get("token");
	}

	@Override
	public String getUserId() {
		return get("user_id");
	}

	@Override
	public String getResponseUrl() {
		return get("response_url");
	}

	/**
	 * User returend in message friendly format.
	 * @return
	 */
	@Override
	public String getMsgFriendlyUser() {
		return String.format("<@%s|%s>",getUserId(),getUserName());
	}

	@Override
	public Map<String,String> getAll() {
		return requestParams;
	}

	private String get(String key) {
		String value = this.requestParams.get(key);
		return value != null ? value : "";
	}
}
