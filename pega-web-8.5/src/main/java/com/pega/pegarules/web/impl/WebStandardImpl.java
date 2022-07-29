package com.pega.pegarules.web.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.pega.web_85.PegaHttpHeaders;
import com.newrelic.instrumentation.pega.web_85.Utils;
import com.pega.pegarules.internal.bootstrap.interfaces.IWebStandard;

@SuppressWarnings("rawtypes")
@Weave
public abstract class WebStandardImpl {
	
	private static boolean isHealthPing(HttpServletRequest aReq) {
		return Weaver.callOriginal();
	}

	@Trace
	public void doOptionsInner(IWebStandard aServlet, HttpServletRequest aReq, HttpServletResponse aResp) {
		Weaver.callOriginal();
	}
	
	@Trace
	public void doPostInner(IWebStandard aServlet, HttpServletRequest aRequest, HttpServletResponse aResp) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes,"RequestURI",aRequest.getRequestURI());
		Utils.addAttribute(attributes,"RemoteUser",aRequest.getRemoteUser());
		
		boolean isHealthPing = isHealthPing(aRequest);
		if(isHealthPing) {
			NewRelic.getAgent().getTransaction().ignore();
		}
		
		Utils.addAttribute(attributes,"Pega-HasStaticContent", aServlet.hasStaticContent());
		traced.addCustomAttributes(attributes);

		PegaHttpHeaders headers = new PegaHttpHeaders(aRequest, aResp);
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.HTTP, headers);
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(headers);
		
		Weaver.callOriginal();
	}
	
	@Trace
	public Map makeEtierRequest(IWebStandard aServlet, HttpServletRequest aReq, HttpServletResponse aResp,
			Map aRequestMapCopy, Object aRecallData, DirectStreamWriter aWriter) {
		
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	protected Map invokeEngine(String etierClass, Map requestMap)  {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","WebStandardImpl","invokeEngine",etierClass);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public boolean sendOutput(Map aResults, IWebStandard aServlet, HttpServletRequest aReq, HttpServletResponse aResp,
			DirectStreamWriter aWriter, boolean aFirst, boolean aFinal) {
		return Weaver.callOriginal();
	}
	
	protected boolean runStaticContentRequest(ServletContext aContext, HttpServletRequest aRequest,
			HttpServletResponse aResponse) {
		boolean returnValue = Weaver.callOriginal();
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-runStaticContentRequest", returnValue);
		if(returnValue) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-RunStaticContent", returnValue);
		}
		
		return returnValue;
	}
}
