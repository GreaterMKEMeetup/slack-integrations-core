package org.gmjm.slack.core.message;


public class ValidChannelName {

	private static final int MAX_CHANNEL_LENGTH = 21;
	private final String value;

	public ValidChannelName(String channelName) {
		if (channelName == null || channelName.trim().isEmpty()) {
			throw new IllegalArgumentException("channelName cannot be null or empty.");
		}

		String intermediateValue = channelName
			.trim()
			.toLowerCase()
			.replaceAll("[^a-z]+", "-");

		intermediateValue = intermediateValue.substring(0, intermediateValue.length() < MAX_CHANNEL_LENGTH ? intermediateValue.length() : MAX_CHANNEL_LENGTH);

		intermediateValue = checkRemoveFirst(intermediateValue);
		intermediateValue = checkRemoveLast(intermediateValue);

		if (intermediateValue.length() > 0) {
			this.value = intermediateValue;
		}
		else {
			throw new IllegalArgumentException("Was not able to create a valid channelName from: " + channelName);
		}
	}

	private String checkRemoveFirst(String intermediateValue) {
		if (intermediateValue.length() == 0) {
			throw new IllegalArgumentException("Could not check character, out of bounds.");
		}

		if (intermediateValue.charAt(0) == '-') {
			return intermediateValue.substring(1, intermediateValue.length());
		}

		return intermediateValue;
	}

	private String checkRemoveLast(String intermediateValue) {
		if (intermediateValue.length() == 0) {
			throw new IllegalArgumentException("Could not check character, out of bounds.");
		}

		if (intermediateValue.charAt(intermediateValue.length() - 1) == '-') {
			return intermediateValue.substring(0, intermediateValue.length() - 1);
		}

		return intermediateValue;
	}

	public String getValue() {
		return value;
	}
}
