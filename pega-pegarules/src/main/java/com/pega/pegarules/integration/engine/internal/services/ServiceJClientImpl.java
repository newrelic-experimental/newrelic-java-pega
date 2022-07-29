package com.pega.pegarules.integration.engine.internal.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ServiceJClientImpl {

	
	@SuppressWarnings("rawtypes")
	@Trace
	public ArrayList invoke(String aServiceURI, HashMap aServiceProperties, ArrayList aReqParams)  {
		if(aServiceURI != null && !aServiceURI.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-ServiceURI", aServiceURI);
		}
		return Weaver.callOriginal();
	}
}
