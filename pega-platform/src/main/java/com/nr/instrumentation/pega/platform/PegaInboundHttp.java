package com.nr.instrumentation.pega.platform;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;
import com.pega.platform.integrationcore.client.http.HttpResponse;

public class PegaInboundHttp implements InboundHeaders {
	
	HttpResponse response = null;
	
	public PegaInboundHttp(HttpResponse resp) {
		response = resp;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		return response.getHeader(name);
	}

}
