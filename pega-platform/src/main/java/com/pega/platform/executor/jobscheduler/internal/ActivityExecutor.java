package com.pega.platform.executor.jobscheduler.internal;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.Utils;
import com.pega.platform.executor.jobscheduler.internal.config.ActivityExecutionConfig;
import com.pega.platform.executor.jobscheduler.internal.config.JobScheduleConfig;

@Weave
public abstract class ActivityExecutor {

	@Trace
	public void executeActivity(JobScheduleConfig jsConfig, ActivityExecutionConfig activityConfig) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addJobScheduleConfig(attributes, jsConfig);
		Utils.addActivityExecutionConfig(attributes, activityConfig);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","ActivityExecutor","executeActivity");
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	private void runActivity(ActivityExecutionConfig activityConfig) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addActivityExecutionConfig(attributes, activityConfig);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","ActivityExecutor","runActivity");
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
