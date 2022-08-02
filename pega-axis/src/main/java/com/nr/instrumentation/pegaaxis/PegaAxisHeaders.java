package com.nr.instrumentation.pegaaxis;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.soap.MimeHeaders;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class PegaAxisHeaders implements Headers {
	
	InboundWrapper inbound = null;
	OutboundWrapper outbound = null;
	
	public PegaAxisHeaders(InboundWrapper in, OutboundWrapper out) {
		inbound = in;
		outbound = out;
	}
	
	public PegaAxisHeaders(MimeHeaders inHeaders, MimeHeaders outHeaders) {
		inbound = inHeaders != null ? new InboundWrapper(inHeaders) : null;
		outbound = outHeaders != null ? new OutboundWrapper(outHeaders) : null;
	}
	
	@Override
	public void addHeader(String name, String value) {
		if(outbound != null) outbound.setHeader(name, value);
	}

	@Override
	public boolean containsHeader(String name) {
		return inbound != null ? inbound.getHeader(name) != null : false;
	}

	@Override
	public String getHeader(String name) {
		return inbound != null ? inbound.getHeader(name) : null;
	}

	@Override
	public Collection<String> getHeaderNames() {
		return inbound != null ? inbound.getHeaderNames() : new ArrayList<>();
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		
		return inbound != null ? inbound.getHeaders(name) : new ArrayList<>();
	}

	@Override
	public void setHeader(String name, String value) {
		if(outbound != null) {
			outbound.setHeader(name, value);
		}
	}
	

}
