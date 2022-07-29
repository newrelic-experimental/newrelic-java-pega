package com.nr.instrumentation.pega.platform;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;
import com.pega.platform.integrationcore.client.http.HttpRequest;

public class PegaOutboundHttp implements OutboundHeaders {
	
	private HttpRequest request = null;
	
	public PegaOutboundHttp(HttpRequest req) {
		request = req;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		request.addHeader(name, value);
	}

}
