package org.gmjm.slack.core.rtm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.gmjm.slack.api.rtm.Event;
import org.gmjm.slack.api.rtm.EventConsumer;
import org.gmjm.slack.api.rtm.EventConsumerID;
import org.gmjm.slack.api.rtm.EventType;
import org.gmjm.slack.api.rtm.RtmSession;
import org.gmjm.slack.api.rtm.RtmSessionFactory;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import static org.junit.Assert.*;

public class RtmSessionTest {

	private String channelId = System.getenv("SLACK_TEST_CHANNEL_ID");
	private String token = System.getenv("SLACK_BOT_TOKEN");

	@Test
	public void testConnect() throws InterruptedException {
		RtmSessionFactory sessionFactory = new WebsocketSessionFactory(token);

		RtmSession slackSession = sessionFactory.createSession();

		int messageId = 5;

		String msg =
			"{\n" +
				"    \"id\": " +  "\"" + messageId +  "\",\n" +
				"    \"channel\": \"" + channelId + "\",\n" +
				"    \"type\": \"message\",\n" +
				"    \"user\": \"U7P0GP1DM\",\n" +
				"    \"text\": \"RtmSession Test Message\"\n" +
				"}";

		final CountDownLatch latch = new CountDownLatch(2);
		final AtomicReference<Event<JsonNode>> atomicHello = new AtomicReference<>();
		final AtomicReference<Event<JsonNode>> atomicReceipt = new AtomicReference<>();


		slackSession.registerConsumer(new EventConsumer<JsonNode>() {
			@Override
			public EventConsumerID getId() {
				return new EventConsumerID("latch-consumer");
			}

			@Override
			public void consume(Event<JsonNode> event) {
				switch (event.getEventType()) {
					case HELLO:
						atomicHello.set(event);
						latch.countDown();
						break;
					case RECEIPT:
						atomicReceipt.set(event);
						latch.countDown();
						break;
					case ERROR:
						throw new RuntimeException("Event type was ERROR: " + event.getPayload().toString());
					default:
						break;
				}
			}
		});

		slackSession.send(msg);

		latch.await(10, TimeUnit.SECONDS);

		Event<JsonNode> hello = atomicHello.get();
		assertNotNull("Hello event must not be null", hello);
		assertEquals(EventType.HELLO, hello.getEventType());

		Event<JsonNode> receipt = atomicReceipt.get();
		assertNotNull("Receipt must not be null.", receipt);
		assertEquals("messageId should equal reply_to filed in receipt message", messageId, receipt.getPayload().findValue("reply_to").asInt());
		assertEquals("Receipt text should match what was sent.", "RtmSession Test Message", receipt.getPayload().findValue("text").asText());
	}

}
