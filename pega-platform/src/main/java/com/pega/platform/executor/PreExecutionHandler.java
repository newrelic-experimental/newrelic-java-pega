package com.pega.platform.executor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class PreExecutionHandler<T> {

	@Trace
	public void beforeExecution(T var1) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PreExecutionHandler",getClass().getSimpleName(),"beforeExecution");
		traced.addCustomAttribute("Pega-PreExecutionHandler-Class", getClass().getName());
		Weaver.callOriginal();
	}
}
