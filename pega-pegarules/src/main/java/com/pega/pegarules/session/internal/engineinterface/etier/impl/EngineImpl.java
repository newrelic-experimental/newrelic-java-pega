package com.pega.pegarules.session.internal.engineinterface.etier.impl;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@SuppressWarnings("rawtypes")
@Weave
public abstract class EngineImpl {

	@Trace
	public Object[] invokeEngine(String aClassName, Object[] aInput, Map aInfo) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","EngineImpl","invokeEngine");
		traced.addCustomAttribute("Pega-ClassName", aClassName);
		return Weaver.callOriginal();
	}
	
	@Trace
	public Object run(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","EngineImpl","run");
		return Weaver.callOriginal();
	}
}
