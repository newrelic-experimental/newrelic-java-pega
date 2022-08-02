package com.nr.agent.fit.instrumentation.httpclient40;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;
import com.pega.apache.http.Header;
import com.pega.apache.http.HttpResponse;

public class InboundWrapper implements InboundHeaders
{
	private final HttpResponse delegate;

	public InboundWrapper(HttpResponse response)
	{
		this.delegate = response;
	}

	public String getHeader(String name)
	{
		Header[] headers = this.delegate.getHeaders(name);
		if (headers.length > 0) {
			return headers[0].getValue();
		}
		return null;
	}

	public HeaderType getHeaderType()
	{
		return HeaderType.HTTP;
	}
}