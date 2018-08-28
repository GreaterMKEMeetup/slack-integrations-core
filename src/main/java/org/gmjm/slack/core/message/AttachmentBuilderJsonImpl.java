package org.gmjm.slack.core.message;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gmjm.slack.api.message.AttachmentBuilder;
import org.gmjm.slack.api.message.FieldBuilder;

class AttachmentBuilderJsonImpl extends JsonBuilder implements AttachmentBuilder {

	List<Map<String, Object>> fields = null;

	public AttachmentBuilderJsonImpl() {
		super();
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

	@Override
	public AttachmentBuilder setTitleLink(String link) {
		return this;
	}

	@Override
	public AttachmentBuilder setImageUrl(String imageUrl) {
		return this;
	}

	@Override
	public AttachmentBuilder setThumbUrl(String thumbUrl) {
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

	@Override
	public AttachmentBuilder setFallbackText(String fallbackText) {
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
	public AttachmentBuilder setTimestamp(int time) {
		setField("ts", time);
		return this;
	}

	@Override
	public AttachmentBuilder setCurrentTimestamp() {
		return setTimestamp((int) (System.currentTimeMillis() / 1000L));
	}

	@Override
	public AttachmentBuilder addField(FieldBuilder builder) {
		if(fields == null) {
			fields = new LinkedList<>();
			jsonFields.put("fields", fields);
		}
		fields.add(((FieldBuilderJsonImpl) builder).getBackingMap());
		return this;
	}

	@Override
	public AttachmentBuilder setFooter(String footerText) {
		return this;
	}

	@Override
	public AttachmentBuilder setFooterIcon(String footerIconUrl) {
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
