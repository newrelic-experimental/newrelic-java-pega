package com.pega.pegarules.integration.engine.internal.connect.rest;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.platform.integrationcore.client.http.HttpRequest;
import com.pega.platform.integrationcore.client.http.HttpResponse;

@Weave
public abstract class RESTConnector {
	
	public String operatorID = Weaver.callOriginal();
	String mServiceURL = Weaver.callOriginal();
	

	public abstract String getPageClass();
	
	@Trace
	protected void executeRequest() {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "PageClass", getPageClass());
		Utils.addAttribute(attributes, "OperatorID", operatorID);
		Utils.addAttribute(attributes, "ServiceURL", mServiceURL);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	HttpResponse invokeREST(HttpRequest httpRequest)  {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "PageClass", getPageClass());
		Utils.addAttribute(attributes, "OperatorID", operatorID);
		Utils.addAttribute(attributes, "ServiceURL", mServiceURL);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace
	protected void perform() {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "PageClass", getPageClass());
		Utils.addAttribute(attributes, "OperatorID", operatorID);
		Utils.addAttribute(attributes, "ServiceURL", mServiceURL);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
