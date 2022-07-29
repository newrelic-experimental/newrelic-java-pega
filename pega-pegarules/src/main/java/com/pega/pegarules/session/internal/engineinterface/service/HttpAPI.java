package com.pega.pegarules.session.internal.engineinterface.service;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.HttpAPIHeaders;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.session.external.engineinterface.service.EngineAPI;

@Weave
public abstract class HttpAPI extends EngineAPI {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Trace
	public Object[] invoke(Object[] aInput, Map aInfo) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "RequestorID", getRequestorId());
		Utils.addClipboardPage(attributes,"PrimaryPage", getPrimaryPage());
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","HttpAPI","invoke");
		Map theRequest = (Map) aInput[0];
		if (theRequest != null) {
			Map headers = new HashMap((Map) ((Map) theRequest.get("headers")));
			if(headers != null) {
				HttpAPIHeaders apiHeaders = new HttpAPIHeaders(headers);
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.HTTP, apiHeaders);
			}
			
		}
		Object[] result =  Weaver.callOriginal();
		return result;
	}
	
	@Trace
	public void handleAuthentication() {
		Weaver.callOriginal();
	}
	
}
