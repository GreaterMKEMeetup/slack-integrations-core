package org.gmjm.slack.core.command;

import java.util.Map;

import org.gmjm.slack.api.command.SlackCommand;
import org.gmjm.slack.api.command.SlackCommandFactory;

public class SlackRequestFactoryMapImpl implements SlackCommandFactory<Map<String,String>> {

	@Override
	public SlackCommand create(Map<String, String> requestParameters) {
		return new SlackCommandMapImpl(requestParameters);
	}
}
