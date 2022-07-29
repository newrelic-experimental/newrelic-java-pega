package com.pega.platform.executor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class PostExecutionHandler<T> {

	@Trace
	public void afterExecution(T var1) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PostExecutionHandler",getClass().getSimpleName(),"afterExecution");
		traced.addCustomAttribute("Pega-PostExecutionHandler-Class", getClass().getName());
		Weaver.callOriginal();
	}
}
