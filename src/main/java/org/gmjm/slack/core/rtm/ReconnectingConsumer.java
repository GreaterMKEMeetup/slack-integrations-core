package org.gmjm.slack.core.rtm;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.gmjm.slack.api.rtm.Event;
import org.gmjm.slack.api.rtm.EventConsumer;
import org.gmjm.slack.api.rtm.EventConsumerID;
import org.gmjm.slack.api.rtm.EventType;

import com.fasterxml.jackson.databind.JsonNode;

class ReconnectingConsumer implements EventConsumer<JsonNode> {

	private final SlackRtmWebsocketClient slackRtmWebsocketClient;
	private final AtomicReference<URI> reconnectUri = new AtomicReference<>();

	public ReconnectingConsumer(SlackRtmWebsocketClient slackRtmWebsocketClient) {
		this.slackRtmWebsocketClient = slackRtmWebsocketClient;
		// TODO: implement retry thread here
	}

	@Override
	public EventConsumerID getId() {
		return new EventConsumerID(this.getClass().getCanonicalName());
	}

	@Override
	public void consume(Event<JsonNode> event) {
		if(EventType.RECONNECT_URL.equals(event.getEventType())) {
			try {
				Optional<URI> oReconnectUri = Optional.ofNullable(event.getPayload().findValue("url"))
					.map(JsonNode::asText)
					.map(URI::create);

				if(oReconnectUri.isPresent()) {
					reconnectUri.set(oReconnectUri.get());
					System.out.println("reconnectUri = " + reconnectUri.get());
					//TODO: INFO Log Success
				} else {
					// TODO: WARN Log
				}
			} catch (Exception e) {
				System.err.print(e);
			}

		}
	}

}
