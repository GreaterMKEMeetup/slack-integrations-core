package org.gmjm.slack.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class JsonBuilder implements BuilderBackingMap {

	protected Map<String, Object> jsonFields = new HashMap<>();
	private ObjectMapper om = new ObjectMapper();
	private Set<String> markdownEnabledFields = new HashSet<>();

	public JsonBuilder() {
		jsonFields.put("mrkdwn_in", markdownEnabledFields);
	}

	public JsonBuilder(boolean allowMarkdown) {
		if (allowMarkdown) {
			jsonFields.put("mrkdwn_in", markdownEnabledFields);
		}
	}

	public String buildJsonString() throws JsonProcessingException {
		return om.writeValueAsString(this.jsonFields);
	}

	protected void setField(String fieldName, String fieldText, boolean markdownEnabled) {
		jsonFields.put(fieldName, fieldText);
		if (markdownEnabled) {
			markdownEnabledFields.add(fieldName);
		}
	}

	protected void setField(String fieldName, int value) {
		jsonFields.put(fieldName, value);
	}

	protected void setField(String fieldName, boolean value) {
		jsonFields.put(fieldName, value);
	}

	@Override
	public Map<String, Object> getBackingMap() {
		return jsonFields;
	}
}
