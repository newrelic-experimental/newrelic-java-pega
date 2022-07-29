package com.pega.platform.executor.jobscheduler.internal;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform85.Utils;
import com.pega.platform.executor.jobscheduler.internal.config.JobScheduleConfig;

@Weave
public abstract class JobSchedulerProcessor {
	
	private JobScheduleConfig config = Weaver.callOriginal();

	@Trace(dispatcher = true)
	public void execute() {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addJobScheduleConfig(attributes, config);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","JobScheduleProcessor","execute");
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
}
