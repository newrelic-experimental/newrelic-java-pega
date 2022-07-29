package com.pega.pegarules.integration.engine.internal.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.priv.services.jsr94.ServiceInfo;

@Weave
public abstract class ServiceSessionImpl {

	private ServiceInfo mServiceInfo = Weaver.callOriginal();
	
	@SuppressWarnings("rawtypes")
	@Trace
	public ArrayList executeRules(ArrayList aInputObjects) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addServiceInfo(attributes, mServiceInfo);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
