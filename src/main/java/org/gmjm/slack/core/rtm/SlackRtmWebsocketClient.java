package org.gmjm.slack.core.rtm;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.gmjm.slack.api.rtm.Event;
import org.gmjm.slack.api.rtm.EventConsumer;
import org.gmjm.slack.api.rtm.EventConsumerID;
import org.gmjm.slack.api.rtm.EventType;
import org.gmjm.slack.api.rtm.RtmSession;
import org.gmjm.slack.api.rtm.RtmSessionDetails;
import org.gmjm.slack.api.rtm.RtmSessionException;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ClientEndpoint
public class SlackRtmWebsocketClient implements RtmSession<JsonNode> {


	static private ObjectMapper OBJECT_MAPPER;

	{
		OBJECT_MAPPER = new ObjectMapper()
			.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
	}

	Session session;
	RtmSessionDetails rtmSessionDetails;
	List<EventConsumer<JsonNode>> consumerList = new LinkedList<>();


	public SlackRtmWebsocketClient(RtmSessionDetails rtmSessionDetails) {
		this.rtmSessionDetails = rtmSessionDetails;
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage (String txt) {
		try {
			System.out.println(txt);
			JsonNode eventNode = OBJECT_MAPPER.reader().readTree(new StringReader(txt));

			String type = Optional.ofNullable(eventNode.findValue("type"))
				.map(JsonNode::asText)
				.map(String::toUpperCase)
				.orElse("RECEIPT");

			EventType eventType;
			try {
				eventType = EventType.valueOf(type);
			} catch(IllegalArgumentException e) {
				// TODO: Warn Log
				eventType = EventType.OTHER;
			}

			if(eventType != null)
			{
				Event<JsonNode> event = new EventJsonNode(eventType,eventNode);
				// TODO : Info log
				consumerList.forEach(consumer -> consumer.consume(event));
			} else {
				// TODO: Error LOG
			}

		}
		catch (IOException e) {
			// TODO: Error Log - Unable to read onMessage String into JsonNode
		}
	}


	@Override
	public RtmSessionDetails getDetails() {
		return rtmSessionDetails;
	}

	public void send(String msg) throws RtmSessionException {
		try {
			if (session != null && this.session.isOpen()) {
				session.getBasicRemote().sendText(msg);
			} else {
				// TODO: error
			}
		} catch (Exception e) {
			throw new RtmSessionException(e.getMessage(), e);
		}
	}

	@Override
	public void registerConsumer(EventConsumer<JsonNode> eventConsumer) {
		consumerList.add(eventConsumer);
	}

	@Override
	public void removeConsumer(EventConsumerID id) {

	}
}
