package org.gmjm.slack.core.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gmjm.slack.api.message.AttachmentBuilder;
import org.gmjm.slack.api.message.FieldBuilder;

class AttachmentBuilderJsonImpl extends JsonBuilder implements AttachmentBuilder {

	List<Map<String, Object>> fields = new ArrayList<>();

	public AttachmentBuilderJsonImpl() {
		super();
		jsonFields.put("fields", fields);
	}

	@Override
	public AttachmentBuilder setTitle(String title, String link) {
		setField("title", title, true);
		setField("title_link", link, false);
		return this;
	}

	@Override
	public AttachmentBuilder setTitle(String title) {
		setField("title", title, true);
		return this;
	}

	/**
	 * Sets text, defaulted to use markdown.
	 *
	 * @param text
	 */
	@Override
	public AttachmentBuilder setText(String text) {
		return setText(text, true);
	}

	@Override
	public AttachmentBuilder setText(String text, boolean markdownEnabled) {
		setField("text", text, markdownEnabled);
		return this;
	}

	/**
	 * Sets pretext, defaulted to use markdown.
	 *
	 * @param preText
	 */
	@Override
	public AttachmentBuilder setPreText(String preText) {
		return setPreText(preText, true);
	}

	@Override
	public AttachmentBuilder setPreText(String preText, boolean markdownEnabled) {
		setField("pretext", preText, markdownEnabled);
		return this;
	}

	@Override
	public AttachmentBuilder setColor(String color) {
		setField("color", color, false);
		return this;
	}

	@Override
	public AttachmentBuilder setAuthorName(String name) {
		setField("author_name", name, false);
		return this;
	}

	@Override
	public AttachmentBuilder setAuthorLink(String url) {
		setField("author_link", url, false);
		return this;
	}

	@Override
	public AttachmentBuilder setAuthorIcon(String url) {
		setField("author_icon", url, false);
		return this;
	}

	@Override
	public AttachmentBuilder addField(FieldBuilder builder) {
		fields.add(((FieldBuilderJsonImpl) builder).getBackingMap());
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
