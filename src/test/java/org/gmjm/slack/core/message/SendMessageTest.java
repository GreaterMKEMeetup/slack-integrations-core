package org.gmjm.slack.core.message;

import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookResponse;
import org.gmjm.slack.core.hook.HttpsHookRequestFactory;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class SendMessageTest {

    private String testChannel = System.getenv("SLACK_TEST_CHANNEL_NAME");
    private String url = System.getenv("SLACK_WEBHOOK_URL");
    private String version = System.getenv("version");

    @Test
    public void testSendMessage() throws Throwable {

        String message = new SlackMessageBuilderJsonImpl()

            .setText("Integration test for version: " + version)
            .setResponseType("ephemeral")
            .setChannel(testChannel)
            .setIconUrl("https://slack-files2.s3-us-west-2.amazonaws.com/bot_icons/2016-08-31/74918376646_48.png")

            .addAttachment(
                new AttachmentBuilderJsonImpl()
                    .setTitle("Update Catalan translation to e38cb41", "http://example.com/mike/")
                    .setText("Modified *18* files"))

            .addAttachment(
                new AttachmentBuilderJsonImpl()
                    .setTitle("mmk", "http://example.com/mike/")
                    .setText("Modified *13* files"))

            .addAttachment(
                new AttachmentBuilderJsonImpl()
                    .setTitle("nowerk", "http://example.com/mike/")
                    .setText("Modified *5* files")
                    .addField(
                        new FieldBuilderJsonImpl()
                            .setShort(true)
                            .setTitle("Example Field 1")
                            .setValue("Value 1"))
                    .addField(
                        new FieldBuilderJsonImpl()
                            .setShort(true)
                            .setTitle("Example Field 2")
                            .setValue("Value 2 extra long value")))
            .build();

        HookRequest hookRequest = new HttpsHookRequestFactory().createHookRequest(url);

        HookResponse response = hookRequest.send(message);

        if(response.getThrowable() != null) {
            throw response.getThrowable();
        }

        assertTrue(HookResponse.Status.SUCCESS.equals(response.getStatus()));

    }
}
