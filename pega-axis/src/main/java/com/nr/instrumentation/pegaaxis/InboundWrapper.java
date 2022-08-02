package com.nr.instrumentation.pegaaxis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

public class InboundWrapper implements InboundHeaders {

	private MimeHeaders headers;
	
	public InboundWrapper(MimeHeaders h) {
		headers = h;
	}
	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		if(headers != null) {
			return headers.getHeader(name)[0];
		}
		return null;
	}

	public Collection<String> getHeaders(String name) {
		String[] values = headers.getHeader(name);
		return Arrays.asList(values);
	}
	
	public Collection<String> getHeaderNames() {
		@SuppressWarnings("rawtypes")
		Iterator addHeadersIterator = headers.getAllHeaders();
		List<String> list = new ArrayList<>();
		while(addHeadersIterator.hasNext()) {
			MimeHeader header = (MimeHeader) addHeadersIterator.next();
			if(header != null) {
				list.add(header.getName());
			}
		}
		return list;

	}
}
