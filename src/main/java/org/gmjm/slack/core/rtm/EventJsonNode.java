package org.gmjm.slack.core.rtm;

import org.gmjm.slack.api.rtm.Event;
import org.gmjm.slack.api.rtm.EventType;

import com.fasterxml.jackson.databind.JsonNode;

public class EventJsonNode implements Event<JsonNode> {

	private final EventType eventType;
	private final JsonNode jsonNode;

	public EventJsonNode(EventType eventType, JsonNode jsonNode) {
		this.eventType = eventType;
		this.jsonNode = jsonNode;
	}

	@Override
	public EventType getEventType() {
		return eventType;
	}

	@Override
	public JsonNode getPayload() {
		return jsonNode;
	}
}
