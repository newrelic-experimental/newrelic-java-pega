package com.pega.platform.executor.jobscheduler.eventservice;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class JobSchedulerEventListener {

	@Trace
	public void process(JobSchedulerEvent var1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","JobSchedulerEventListener",getClass().getSimpleName(),"process");
		Weaver.callOriginal();
	}
}
