package com.pega.platform.executor.internal.util;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.external.mgmt.IPRThread;

@Weave(type=MatchType.Interface)
public abstract class TaskWithThread<T> {

	@Trace
	public T process(IPRThread var1) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","TaskWithThread",getClass().getSimpleName(),"process");
		return Weaver.callOriginal();
	}
}
