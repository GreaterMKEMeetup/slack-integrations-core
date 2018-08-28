package org.gmjm.slack.core.hook;

import java.util.concurrent.CompletableFuture;
import org.gmjm.slack.api.hook.AsyncHookRequest;
import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookRequestFactory;
import org.gmjm.slack.api.hook.HookResponse;

public class HttpsHookRequestFactory implements HookRequestFactory {

	@Override
	public HookRequest createHookRequest(String url) {
		return new HookRequestHttpsUrlConnectionImpl(url);
	}

	@Override
	public AsyncHookRequest createAsyncHookRequest(String url) {
		return new AsyncHookRequest() {
			HookRequest hr = createHookRequest(url);
			@Override
			public CompletableFuture<HookResponse> send(String message) {
				return CompletableFuture.supplyAsync(() -> hr.send(message));
			}
		};
	}
}
