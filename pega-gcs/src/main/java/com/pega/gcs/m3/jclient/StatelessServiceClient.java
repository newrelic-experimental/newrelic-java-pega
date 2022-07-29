package com.pega.gcs.m3.jclient;

import java.util.ArrayList;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class StatelessServiceClient {
	
	@SuppressWarnings("rawtypes")
	@Trace(dispatcher = true)
	public ArrayList invoke(String aServiceURI, HashMap aServiceProperties, ArrayList aReqParams) throws Exception {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","StatelessServiceClient","invoke");
		traced.addCustomAttribute("Pega-ServiceURI", aServiceURI);
		
		return Weaver.callOriginal();
	}

}
