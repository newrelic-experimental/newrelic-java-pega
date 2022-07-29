package com.nr.instrumentation.pega.pegarules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

@SuppressWarnings("rawtypes")
public class HttpAPIHeaders implements Headers {
	
	private Map headers = null;
	
	public HttpAPIHeaders(Map map) {
		headers = map;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		if(headers != null) {
			Object value = headers.get(name);
			
			return value != null ? value.toString() : null;
		}
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> list = new ArrayList<String>();
		String value = getHeader(name);
		if(value != null && !value.isEmpty()) {
			list.add(value);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setHeader(String name, String value) {
		if(headers != null) {
			headers.put(name, value);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		setHeader(name, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getHeaderNames() {
		List<String> list = new ArrayList<String>();
		Set<Object> keys = headers.keySet();
		for(Object key : keys) {
			list.add(key.toString());
		}
		return list;
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

}
