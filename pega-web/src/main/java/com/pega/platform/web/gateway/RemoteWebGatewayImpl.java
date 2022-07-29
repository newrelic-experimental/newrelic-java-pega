package com.pega.platform.web.gateway;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.internal.bootstrap.interfaces.IWebStandard;

@Weave
public abstract class RemoteWebGatewayImpl {

	@Trace
	public void doPost(IWebStandard aServlet, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		
		Weaver.callOriginal();
	}

	@Trace
	protected CloseableHttpResponse fireRemoteRequest(HttpClientWrapper clientWrapper, HttpUriRequest remoteRequest,String authToken) {
		
		return Weaver.callOriginal();
	}
	
	@Trace
	protected CloseableHttpResponse processRequest(HttpServletRequest servletRequest) {
		return Weaver.callOriginal();
	}
}
