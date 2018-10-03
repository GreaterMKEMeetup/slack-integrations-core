package org.gmjm.slack.core.message;

import java.util.LinkedHashMap;
import java.util.Map;
import org.gmjm.slack.api.message.Field;
import org.gmjm.slack.api.message.FieldBuilder;

public class FieldBuilderJsonImpl extends JsonBuilder implements FieldBuilder {

	private static class FieldMap extends LinkedHashMap<String, Object> implements Field {
		public FieldMap(Map<? extends String, ?> m) {
			super(m);
		}

		private static final JsonWriter writer = new JsonWriter();

		@Override
		public String toString() {
			return writer.toJson(this);
		}
	}

	public FieldBuilderJsonImpl() {
		super(false);
	}

	@Override
	public FieldBuilder setTitle(String title) {
		setField("title", title, false);
		return this;
	}

	@Override
	public FieldBuilder setValue(String text) {
		setField("value", text, false);
		return this;
	}

	@Override
	public FieldBuilder setShort(boolean isShort) {
		setField("short", isShort);
		return this;
	}

	@Override
	public FieldBuilder setAttribute(String key, Object value) {
		setField(key, value);
		return this;
	}

	@Override
	public Field build() {
		try {
			return new FieldMap(jsonFields);
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to build JSON string.", e);
		}
	}
}
