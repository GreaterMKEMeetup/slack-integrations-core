package org.gmjm.slack.core.message;

import static junit.framework.TestCase.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.gmjm.slack.api.hook.AsyncHookRequest;
import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookResponse;
import org.gmjm.slack.api.message.SlackMessageFactory;
import org.gmjm.slack.core.hook.HttpsHookRequestFactory;
import org.junit.Test;

public class SendMessageTest {

    private String testChannel = System.getenv("SLACK_TEST_CHANNEL_NAME");
    private String url = System.getenv("SLACK_WEBHOOK_URL");
    private String version = System.getenv("version");

    @Test
    public void testSendMessage() throws Throwable {

        SlackMessageFactory messageFactory = new JsonMessageFactory();

        String message = messageFactory.createMessageBuilder()

            .setText("Integration test for version: " + version)
            .setResponseType("ephemeral")
            .setChannel(testChannel)
            .setIconUrl("https://slack-files2.s3-us-west-2.amazonaws.com/bot_icons/2016-08-31/74918376646_48.png")

            .addAttachment(
                messageFactory.createAttachmentBuilder()
                    .setTitle("Update Catalan translation to e38cb41", "http://example.com/mike/")
                    .setText("Modified *18* files"))

            .addAttachment(
                messageFactory.createAttachmentBuilder()
                    .setTitle("mmk", "http://example.com/mike/")
                    .setText("Modified *13* files"))

            .addAttachment(
                messageFactory.createAttachmentBuilder()
                    .setTitle("nowerk", "http://example.com/mike/")
                    .setText("Modified *5* files")
                    .addField(
                        messageFactory.createFieldBuilder()
                            .setShort(true)
                            .setTitle("Example Field 1")
                            .setValue("Value 1"))
                    .addField(
                        messageFactory.createFieldBuilder()
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

    @Test
    public void testSendMessageAsync() throws Throwable {

        SlackMessageFactory messageFactory = new JsonMessageFactory();

        String message = messageFactory.createMessageBuilder()

            .setText("Integration test for version: " + version)
            .setResponseType("ephemeral")
            .setChannel(testChannel)
            .setIconUrl("https://slack-files2.s3-us-west-2.amazonaws.com/bot_icons/2016-08-31/74918376646_48.png")

            .addAttachment(
                messageFactory.createAttachmentBuilder()
                    .setTitle("Update Catalan translation to e38cb41", "http://example.com/mike/")
                    .setText("Modified *18* files"))

            .addAttachment(
                messageFactory.createAttachmentBuilder()
                    .setTitle("mmk", "http://example.com/mike/")
                    .setText("Modified *13* files"))

            .addAttachment(
                messageFactory.createAttachmentBuilder()
                    .setTitle("nowerk", "http://example.com/mike/")
                    .setText("Modified *5* files")
                    .addField(
                        messageFactory.createFieldBuilder()
                            .setShort(true)
                            .setTitle("Example Field 1")
                            .setValue("Value 1"))
                    .addField(
                        messageFactory.createFieldBuilder()
                            .setShort(true)
                            .setTitle("Example Field 2")
                            .setValue("Value 2 extra long value")))
            .build();

        AsyncHookRequest asyncHookRequest = new HttpsHookRequestFactory().createAsyncHookRequest(url);

        CompletableFuture<HookResponse> futureResponse = asyncHookRequest.send(message);

        final CountDownLatch responeCountdownLatch = new CountDownLatch(1);

        futureResponse
            .thenApply(hookResponse -> hookResponse.getStatus())
            .whenComplete((responseStatus, throwable) -> {
                assertTrue(HookResponse.Status.SUCCESS.equals(responseStatus));
                System.out.println("Success!");
                responeCountdownLatch.countDown();
            });

        System.out.println("Waiting!");

        assertTrue(responeCountdownLatch.await(5,TimeUnit.SECONDS));


    }
}
