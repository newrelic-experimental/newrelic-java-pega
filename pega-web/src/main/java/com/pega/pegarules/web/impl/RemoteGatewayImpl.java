package com.pega.pegarules.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.internal.bootstrap.interfaces.IWebStandard;

@Weave
public abstract class RemoteGatewayImpl {

	@Trace
	public void doPost(IWebStandard aServlet, HttpServletRequest aReq, HttpServletResponse aResp) {
		Weaver.callOriginal();
	}
}
