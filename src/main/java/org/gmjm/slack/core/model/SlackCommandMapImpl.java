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
	public String getCommand()
	{
		return get("command");
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


	@Override
	public String getTeamId()
	{
		return get("team_id");
	}


	@Override
	public String getTeamDomain()
	{
		return get("team_domain");
	}


	@Override
	public String getChannelId()
	{
		return get("channel_id");
	}


	@Override
	public String getChannelName()
	{
		return get("channel_name");
	}


	private String get(String key) {
		String value = this.requestParams.get(key);
		return value != null ? value : "";
	}
}
