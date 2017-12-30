package org.gmjm.slack.core.rtm;

import org.gmjm.slack.api.rtm.Event;
import org.gmjm.slack.api.rtm.EventConsumer;
import org.gmjm.slack.api.rtm.EventConsumerID;
import org.gmjm.slack.api.rtm.RtmSession;
import org.gmjm.slack.api.rtm.RtmSessionFactory;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class RtmSessionTest {

	private String token = System.getenv("slack.token");

	@Test
	public void testConnect() throws InterruptedException {
		RtmSessionFactory sessionFactory = new WebsocketSessionFactory(token);

		RtmSession slackSession = sessionFactory.createSession();


		String msg =
			"{\n" +
				"    \"id\": " +  "\"" + 5 +  "\",\n" +
				"    \"channel\": \"C0VPRF1HU\",\n" +
				"    \"type\": \"message\",\n" +
				"    \"user\": \"U7P0GP1DM\",\n" +
				"    \"text\": \"Test Message\"\n" +
				"}";

		slackSession.registerConsumer(new EventConsumer<JsonNode>() {
			@Override
			public EventConsumerID getId() {
				return new EventConsumerID("system-out");
			}

			@Override
			public void consume(Event<JsonNode> event) {
				System.out.println(event.getEventType());
			}
		});

		slackSession.send(msg);

		Thread.sleep(100000);
	}

}
