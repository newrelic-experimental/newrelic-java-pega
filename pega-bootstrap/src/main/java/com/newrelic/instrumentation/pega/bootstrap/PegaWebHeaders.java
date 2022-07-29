package com.newrelic.instrumentation.pega.bootstrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class PegaWebHeaders implements Headers {
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	
	public PegaWebHeaders(HttpServletRequest req, HttpServletResponse resp) {
		request = req;
		response = resp;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		if(request != null) {
			return request.getHeader(name);
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

	@Override
	public void setHeader(String name, String value) {
		if(response != null) {
			response.setHeader(name, value);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		if(response != null) {
			response.addHeader(name, value);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Collection<String> getHeaderNames() {
		List<String> list = new ArrayList<String>();
		if(request != null) {
			Enumeration headerNames = request.getHeaderNames();
			while(headerNames.hasMoreElements()) {
				String value = headerNames.nextElement().toString();
				list.add(value);
			}
		}
		return list;
	}

	@Override
	public boolean containsHeader(String name) {
		Collection<String> headers = getHeaderNames();
		
		return headers.contains(name);
	}

}
