package com.pega.pegarules.management.internal;

import java.util.HashMap;
import java.util.List;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;

@SuppressWarnings("rawtypes")
@Weave(type = MatchType.BaseClass)
public abstract class MBeanHelper {

	@Trace
	public String invokeOperation(String classname, String operation, List inputTypes, List inputObjects) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Classname", classname);
		Utils.addAttribute(attributes, "Operation", operation);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","MBeanHelper",getClass().getSimpleName(),"invokeOperation");
		return Weaver.callOriginal();
	}
	
	@Trace
	public Object invokeOperationWithObjectReturnValue(String classname, String operation, List inputTypes, List inputObjects) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Classname", classname);
		Utils.addAttribute(attributes, "Operation", operation);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","MBeanHelper",getClass().getSimpleName(),"invokeOperationWithObjectReturnValue");
		return Weaver.callOriginal();
	}
}
