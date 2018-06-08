package org.gmjm.slack.core.command;

import java.util.Map;

import org.gmjm.slack.api.command.SlackCommand;

public interface SlackRequestFactory {

	SlackCommand create(Map<String, String> requestParameters);
}
