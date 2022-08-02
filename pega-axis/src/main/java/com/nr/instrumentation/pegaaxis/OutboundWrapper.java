package com.nr.instrumentation.pegaaxis;

import javax.xml.soap.MimeHeaders;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;

public class OutboundWrapper implements OutboundHeaders {

	private MimeHeaders headers;
	
	public OutboundWrapper(MimeHeaders h) {
		headers = h;
	}
	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		if(headers != null) {
			headers.setHeader(name, value);
		}
	}

	public void addHeader(String name, String value) {
		if(headers != null) {
			headers.addHeader(name, value);
		}
	}
}
