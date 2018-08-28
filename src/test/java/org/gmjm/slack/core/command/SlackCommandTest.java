package org.gmjm.slack.core.command;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.gmjm.slack.api.command.SlackCommand;
import org.gmjm.slack.api.command.SlackCommandFactory;
import org.junit.Test;

public class SlackCommandTest {

    @Test
    public void testCreateCommand() {
        SlackCommandFactory slackCommandFactory = new SlackRequestFactoryMapImpl();

        Map<String,String> request = new HashMap<>();

        request.put("text", "/run ok");

        SlackCommand slackCommand = slackCommandFactory.create(request);

        assertEquals("/run ok", slackCommand.getText());
    }

}
