package org.gmjm.slack.core.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class JsonBuilder implements BuilderBackingMap {

	protected Map<String, Object> jsonFields = new LinkedHashMap<>();

	DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("	", DefaultIndenter.SYS_LF);
	DefaultPrettyPrinter printer = new DefaultPrettyPrinter()
		.withArrayIndenter(indenter)
		.withObjectIndenter(indenter);

	private ObjectWriter objectWriter = new ObjectMapper()
		.writer(printer);

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
		return objectWriter.writeValueAsString(this.jsonFields);
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
