package com.nr.agent.fit.instrumentation.httpclient40;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;
import com.pega.apache.http.HttpRequest;

public class OutboundWrapper implements OutboundHeaders
{
	private final HttpRequest delegate;

	public OutboundWrapper(HttpRequest request)
	{
		delegate = request;
	}

	public void setHeader(String name, String value)
	{
		delegate.setHeader(name, value);
	}

	public HeaderType getHeaderType()
	{
		return HeaderType.HTTP;
	}
}