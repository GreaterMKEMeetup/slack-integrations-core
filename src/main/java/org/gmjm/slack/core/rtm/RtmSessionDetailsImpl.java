package org.gmjm.slack.core.rtm;

import java.net.URI;

import org.gmjm.slack.api.rtm.RtmSessionDetails;
import org.gmjm.slack.api.rtm.Self;
import org.gmjm.slack.api.rtm.Team;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RtmSessionDetailsImpl implements RtmSessionDetails {
	boolean ok;
	String error;
	SelfImpl self;
	TeamImpl team;

	@JsonProperty
	URI url;

	public RtmSessionDetailsImpl() {
	}

	@Override
	public Self getSelf() {
		return self;
	}

	@Override
	public Team getTeam() {
		return team;
	}

	public URI getUri() {
		return url;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RtmSessionDetailsImpl{");
		sb.append("ok=").append(ok);
		sb.append(", error='").append(error).append('\'');
		sb.append(", self=").append(self);
		sb.append(", team=").append(team);
		sb.append(", url=").append(url);
		sb.append('}');
		return sb.toString();
	}
}
