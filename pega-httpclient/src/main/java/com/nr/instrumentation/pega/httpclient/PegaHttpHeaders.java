package com.nr.instrumentation.pega.httpclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;
import com.pega.apache.commons.httpclient.Header;
import com.pega.apache.commons.httpclient.HttpMethod;

public class PegaHttpHeaders implements Headers {
	
	private HttpMethod method = null;
	
	public PegaHttpHeaders(HttpMethod m) {
		method = m;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		 Header header = method.getResponseHeader(name);
		 if(header != null) {
			 return header.getValue();
		 }
		 return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> list = new ArrayList<String>();
		String value = getHeader(name);
		if(value != null) {
			list.add(value);
		}
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		 if(method != null) {
			 method.setRequestHeader(name, value);
		 }
	}

	@Override
	public void addHeader(String name, String value) {
		if(method != null) {
			method.addRequestHeader(name, value);
		}
	}

	@Override
	public Collection<String> getHeaderNames() {
		
		List<String> list = new ArrayList<String>();
		if (method != null) {
			Header[] headers = method.getRequestHeaders();
			for(Header header : headers) {
				list.add(header.getName());
			}
			
		}
		return list;
	}

	@Override
	public boolean containsHeader(String name) {
		
		return getHeaderNames().contains(name);
	}

}
