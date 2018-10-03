package org.gmjm.slack.core.message;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.gmjm.slack.api.message.AttachmentBuilder;
import org.gmjm.slack.api.message.Message;
import org.gmjm.slack.api.message.SlackMessageBuilder;

class SlackMessageBuilderJsonImpl extends JsonBuilder implements SlackMessageBuilder {

	private static class MessageMap extends LinkedHashMap<String, Object> implements Message {
		public MessageMap(Map<? extends String, ?> m) {
			super(m);
		}

		private static final JsonWriter writer = new JsonWriter();

		@Override
		public String toString() {
			return writer.toJson(this);
		}
	}

	List<AttachmentBuilder> attachments = new ArrayList<>();

	public SlackMessageBuilderJsonImpl() {
		super(false);
	}

	/**
	 * Sets text, defaulted to use markdown.
	 *
	 * @param text
	 */
	@Override
	public SlackMessageBuilder setText(String text) {
		setText(text, true);
		return this;
	}

	@Override
	public SlackMessageBuilder setIconEmoji(String iconEmoji) {
		setField("icon_emoji", ":" + iconEmoji + ":", false);
		return this;
	}

	@Override
	public SlackMessageBuilder setIconUrl(String iconUrl) {
		setField("icon_url", iconUrl, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setText(String text, boolean markdownEnabled) {
		setField("text", text, markdownEnabled);
		return this;
	}

	@Override
	public SlackMessageBuilder setChannelId(String channelId) {
		setField("channel", channelId, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setChannel(String channelName) {
		setField("channel", "#" + channelName, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setUserAsChannel(String userName) {
		setField("channel", "@" + userName, false);
		return this;
	}

	@Override
	public SlackMessageBuilder addAttachment(AttachmentBuilder attachmentBuilder) {
		attachments.add(attachmentBuilder);
		return this;
	}

	@Override
	public SlackMessageBuilder setResponseType(String responseType) {
		setField("response_type", responseType, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setUsername(String username) {
		setField("username", username, false);
		return this;
	}

	@Override
	public SlackMessageBuilder replaceOriginal(boolean replaceOriginal) {
		setField("replace_original", replaceOriginal);
		return this;
	}

	@Override
	public SlackMessageBuilder deleteOriginal(boolean deleteOriginal) {
		setField("delete_original", deleteOriginal);
		return this;
	}

	@Override
	public SlackMessageBuilder setAttribute(String key, Object value) {
		setField(key, value);
		return this;
	}

	@Override
	public Message build() {

		MessageMap messageMap = new MessageMap(jsonFields);

		try {
			if(attachments.size() > 0) {
				messageMap.put(
						"attachments",
						attachments.stream()
								.map(attachmentBuilder -> attachmentBuilder.build())
								.collect(Collectors.toList()));
			}
			return messageMap;
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to build JSON string.", e);
		}
	}

}
