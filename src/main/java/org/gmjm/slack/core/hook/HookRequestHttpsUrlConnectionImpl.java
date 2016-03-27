package org.gmjm.slack.core.hook;

import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookResponse;



class HookRequestHttpsUrlConnectionImpl implements HookRequest
{

	private String slackHookUrl;

	public HookRequestHttpsUrlConnectionImpl(){}

	public HookRequestHttpsUrlConnectionImpl(String slackHookUrl) {
		this.slackHookUrl = slackHookUrl;
	}

	@Override
	public HookResponse send(String string) {
		try
		{
			URL url = new URL(slackHookUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();

			os.write(string.getBytes());

			os.flush();

			String response = IOUtils.toString(con.getInputStream());

			int responseCode = con.getResponseCode();

			con.disconnect();

			return HookResponseFactory.success(response,responseCode);
		} catch (Exception e) {
			return HookResponseFactory.fail(e.getMessage(),500,e);
		}


	}
}
