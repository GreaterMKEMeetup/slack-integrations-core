package org.gmjm.slack.core.rtm;

import org.gmjm.slack.api.rtm.Team;

public class TeamImpl implements Team {

	String domain;
	String id;
	String name;
	String enterprise_id;

	public TeamImpl() {
	}

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getEnterpriseId() {
		return enterprise_id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TeamImpl{");
		sb.append("domain='").append(domain).append('\'');
		sb.append(", id='").append(id).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
