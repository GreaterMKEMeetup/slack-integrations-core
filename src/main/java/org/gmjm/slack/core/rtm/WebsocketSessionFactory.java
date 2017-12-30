package org.gmjm.slack.core.rtm;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.HttpsURLConnection;

import org.gmjm.slack.api.rtm.ConnectionError;
import org.gmjm.slack.api.rtm.ConnectionErrorType;
import org.gmjm.slack.api.rtm.RtmSession;
import org.gmjm.slack.api.rtm.RtmSessionCreationException;
import org.gmjm.slack.api.rtm.RtmSessionDetails;
import org.gmjm.slack.api.rtm.RtmSessionFactory;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.lang.String.*;

public class WebsocketSessionFactory implements RtmSessionFactory {

	static private ObjectMapper OBJECT_MAPPER;

	{
		OBJECT_MAPPER = new ObjectMapper()
			.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
	}

	final String token;
	final String connectUrl = "https://slack.com/api/rtm.connect";
	final String paramsTemplate = "?token=%s&batch_presence_aware=%s&scope=%s";
	final Boolean batch_presence_aware;
	final AtomicReference<String> reconnectUri = new AtomicReference<>();

	public WebsocketSessionFactory(String token) {
		this.token = token;
		this.batch_presence_aware = false;
	}

	private String encodedParams() throws UnsupportedEncodingException {
		return format(
			paramsTemplate,
			URLEncoder.encode(token,"UTF-8"),
			URLEncoder.encode(batch_presence_aware.toString(),"UTF-8"),
			URLEncoder.encode("bot","UTF-8"));
	}

	public RtmSession createSession() throws RtmSessionCreationException {
		try {
			URL url = new URL(connectUrl + encodedParams());
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			con.disconnect();

			RtmSessionDetails details = OBJECT_MAPPER.readValue(con.getInputStream(), RtmSessionDetailsImpl.class);

			SlackRtmWebsocketClient slackRtmClient = new SlackRtmWebsocketClient(details);

			javax.websocket.WebSocketContainer container = javax.websocket.ContainerProvider.getWebSocketContainer();
			container.connectToServer(slackRtmClient, details.getUri());

			return slackRtmClient;

		} catch (Exception e) {
			throw new RtmSessionCreationException(
				new ConnectionError() {
					@Override
					public ConnectionErrorType getConnectionErrorType() {
						return ConnectionErrorType.EXCEPTION;
					}
				}, e);
		}
	}


}
