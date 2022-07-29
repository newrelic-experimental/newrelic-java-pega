package com.pega.pegarules.priv.context;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;

@Weave(type = MatchType.BaseClass)
public abstract class JNDIEnvironment {

	@Trace
	public Object invokeEngine(String aClassName, Object aInput) throws Exception {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Etier-Class", aClassName);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","JNDIEnvironment",getClass().getSimpleName(),"invokeEngine");
		return Weaver.callOriginal();
	}

	@Trace
	public Object invokeEngineBMT(String aClassName, Object aInput) throws Exception {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Etier-Class", aClassName);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","JNDIEnvironment",getClass().getSimpleName(),"invokeEngineBMT");
		return Weaver.callOriginal();
	}

	@Trace
	public Object invokeEngineCMT(String aClassName, Object aInput) throws Exception {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Etier-Class", aClassName);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","JNDIEnvironment",getClass().getSimpleName(),"invokeEngineCMT");
		return Weaver.callOriginal();
	}

}
