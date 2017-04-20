package org.gmjm.slack.core.message;

import org.gmjm.slack.api.message.FieldBuilder;

public class FieldBuilderJsonImpl extends JsonBuilder implements FieldBuilder {

	public FieldBuilderJsonImpl() {
		super();
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
	public String build() {
		try {
			return super.buildJsonString();
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to build JSON string.", e);
		}
	}
}
