package org.gmjm.slack.core.message;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.gmjm.slack.api.message.ActionBuilder;
import org.gmjm.slack.api.message.Attachment;
import org.gmjm.slack.api.message.AttachmentBuilder;
import org.gmjm.slack.api.message.Button;
import org.gmjm.slack.api.message.FieldBuilder;

class AttachmentBuilderJsonImpl extends JsonBuilder implements AttachmentBuilder {

	private static class AttachmentMap extends LinkedHashMap<String, Object> implements Attachment {

		public AttachmentMap(Map<? extends String, ?> m) {
			super(m);
		}

		private static final JsonWriter writer = new JsonWriter();

		@Override
		public String toString() {
			return writer.toJson(this);
		}
	}

	List<FieldBuilder> fields = new LinkedList<>();
	List<ActionBuilder> actions = new LinkedList<>();

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
	public AttachmentBuilder setTitleLink(String titleLink) {
		setField("title_link", titleLink, false);
		return this;
	}

	@Override
	public AttachmentBuilder setImageUrl(String imageUrl) {
		setField("image_url", imageUrl, false);
		return this;
	}

	@Override
	public AttachmentBuilder setThumbUrl(String thumbUrl) {
		setField("thumb_url", thumbUrl, false);
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
		setField("fallback", fallbackText, false);
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
	public AttachmentBuilder addField(FieldBuilder fieldBuilder) {
		fields.add(fieldBuilder);
		return this;
	}

	@Override
	public AttachmentBuilder addFields(FieldBuilder... fieldBuilders) {
		Collections.addAll(fields, fieldBuilders);
		return this;
	}

	@Override
	public AttachmentBuilder addFields(Collection<FieldBuilder> fieldBuilders) {
		fields.addAll(fieldBuilders);
		return this;
	}

	@Override
	public AttachmentBuilder setFields(Collection<FieldBuilder> fieldBuilders) {
		fields = new LinkedList<>();
		fields.addAll(fieldBuilders);
		return this;
	}

	@Override
	public AttachmentBuilder setFooter(String footerText) {
		setField("footer", footerText, false);
		return this;
	}

	@Override
	public AttachmentBuilder setFooterIcon(String footerIconUrl) {
		setField("footer_icon", footerIconUrl, false);
		return this;
	}

	@Override
	public AttachmentBuilder setAttachmentType(String attachmentType) {
		setField("attachment_type", attachmentType);
		return this;
	}

	@Override
	public AttachmentBuilder addAction(ActionBuilder actionBuilder) {
		actions.add(actionBuilder);
		return this;
	}

	@Override
	public AttachmentBuilder addActions(ActionBuilder... actionBuilders) {
		Collections.addAll(actions, actionBuilders);
		return this;
	}

	@Override
	public AttachmentBuilder addActions(Collection<ActionBuilder> actionBuilders) {
		actions.addAll(actionBuilders);
		return this;
	}

	@Override
	public AttachmentBuilder addButton(Button button) {
		actions.add(new ActionBuilderJsonImpl(button));
		return this;
	}

	@Override
	public AttachmentBuilder setActions(Collection<ActionBuilder> actionBuilders) {
		actions = new LinkedList<>();
		actions.addAll(actionBuilders);
		return this;
	}

	@Override
	public AttachmentBuilder setAttribute(String key, Object value) {
		setField(key, value);
		return this;
	}

	@Override
	public AttachmentBuilder setCallbackId(String callbackId) {
		setField("callback_id", callbackId);
		return this;
	}

	@Override
	public Attachment build() {

		AttachmentMap attachmentMap = new AttachmentMap(jsonFields);

		try {
			if(fields.size() > 0) {
				attachmentMap.put(
						"fields",
						fields.stream()
							.map(FieldBuilder::build)
							.collect(toList()));
			}
			if(actions.size() > 0) {
				attachmentMap.put(
						"actions",
						actions.stream()
								.map(ActionBuilder::build)
								.collect(toList()));
			}
			return attachmentMap;
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to build JSON string.", e);
		}
	}
}
