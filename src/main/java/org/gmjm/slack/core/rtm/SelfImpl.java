package org.gmjm.slack.core.rtm;

import org.gmjm.slack.api.rtm.Self;

public class SelfImpl implements Self {
	String id;
	String name;

	public SelfImpl() {
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SelfImpl{");
		sb.append("id='").append(id).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
