package com.pega.platform.executor.jobscheduler.internal.eventservice;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.platform.executor.PulseEvent;

@Weave(type = MatchType.Interface)
public abstract class EventProcessor {

	@Trace
	public void process(PulseEvent var1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","EventProcessor",getClass().getSimpleName(),"process");
		Weaver.callOriginal();
	}
}
