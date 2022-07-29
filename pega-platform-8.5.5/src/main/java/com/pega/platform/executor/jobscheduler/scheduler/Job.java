package com.pega.platform.executor.jobscheduler.scheduler;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Job {

	public abstract String getId();

	@Trace(dispatcher = true)
	public void execute() {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Job",getClass().getSimpleName(),"execute");
		traced.addCustomAttribute("Pega-Job-ID", getId());
		Weaver.callOriginal();
	}

}
