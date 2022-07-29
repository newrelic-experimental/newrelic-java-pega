package com.nr.instrumentation.pega.pegarules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class PegaHeaders implements Headers {
	
	private HashMap<String, List<String>> headerValues;
	
	public PegaHeaders() {
		headerValues = new HashMap<String, List<String>>();
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		List<String> values = headerValues.get(name);
		if(values != null) {
			if(!values.isEmpty()) return values.get(0);
		}
		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> values = headerValues.get(name);
		if(values != null) {
			return values;
		}
		return new ArrayList<String>();
	}

	@Override
	public void setHeader(String name, String value) {
		List<String> values = headerValues.get(name);
		if(values == null) {
			values = new ArrayList<String>();
		}
		values.add(0, value);
		headerValues.put(name, values);
	}

	@Override
	public void addHeader(String name, String value) {
		List<String> values = headerValues.get(name);
		if(values == null) {
			values = new ArrayList<String>();
		}
		values.add(value);
		headerValues.put(name, values);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return headerValues.keySet();
	}

	@Override
	public boolean containsHeader(String name) {
		return headerValues.keySet().contains(name);
	}

	public boolean isEmpty() {
		return headerValues.isEmpty();
	}
}
