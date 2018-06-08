package org.gmjm.slack.core.command;

import java.util.Map;

import org.gmjm.slack.api.command.SlackCommand;

public class SlackRequestFactoryMapImpl implements SlackRequestFactory {

	@Override
	public SlackCommand create(Map<String, String> requestParameters) {
		return new SlackCommandMapImpl(requestParameters);
	}
}
