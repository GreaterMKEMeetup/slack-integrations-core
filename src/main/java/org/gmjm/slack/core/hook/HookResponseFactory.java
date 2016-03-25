package org.gmjm.slack.core.hook;

import org.gmjm.slack.api.hook.HookResponse;

public class HookResponseFactory
{
	static HookResponse success(
		String message,
		int statusCode) {
		return new HookResponse()
		{
			@Override
			public String getMessage()
			{
				return message;
			}


			@Override
			public int getStatusCode()
			{
				return statusCode;
			}


			@Override
			public Status getStatus()
			{
				return Status.SUCCESS;
			}


			@Override
			public Throwable getThrowable()
			{
				return null;
			}
		};
	}

	static HookResponse fail(
		String message,
		int statusCode,
		Throwable throwable) {
		return new HookResponse()
		{
			@Override
			public String getMessage()
			{
				return message;
			}


			@Override
			public int getStatusCode()
			{
				return statusCode;
			}


			@Override
			public Status getStatus()
			{
				return Status.SUCCESS;
			}


			@Override
			public Throwable getThrowable()
			{
				return throwable;
			}
		};
	}
}
