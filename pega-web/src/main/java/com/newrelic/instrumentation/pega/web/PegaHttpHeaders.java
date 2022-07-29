package com.newrelic.instrumentation.pega.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class PegaHttpHeaders implements Headers {
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	
	public PegaHttpHeaders(HttpServletRequest req, HttpServletResponse resp) {
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
			response.addHeader(name, value);
		}
	}

	@Override
	public void addHeader(String name, String value) {
		if(response != null) {
			response.addHeader(name, value);
		}
	}

	@Override
	public Collection<String> getHeaderNames() {
		List<String> list = new ArrayList<String>();
		if(request != null) {
			Enumeration<?> names = request.getHeaderNames();
			while(names.hasMoreElements()) {
				String name = names.nextElement().toString();
				list.add(name);
			}
		}
		return list;
	}

	@Override
	public boolean containsHeader(String name) {
		boolean contains = false;
		if(request != null) {
			Enumeration<?> names = request.getHeaderNames();
			while(names.hasMoreElements() && !contains) {
				String headerName = names.nextElement().toString();
				contains = headerName.equalsIgnoreCase(name);
			}
		}
		if(!contains && response != null) {
			contains = response.containsHeader(name);
		}
		return contains;
	}

}
