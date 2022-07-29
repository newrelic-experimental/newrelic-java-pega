package com.pega.platform.executor.jobscheduler.scheduler.internal;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform86.Utils;
import com.pega.platform.executor.jobscheduler.scheduler.JobRunTime;

@Weave(type=MatchType.BaseClass)
public abstract class JobExecutionTemplate {

	@Trace(dispatcher = true)
	public void executeJob(JobRunTime job) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "ScheduledJob", "ScheduledJob");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addJobRuntime(attributes, job);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}

	@Trace
	public void beforeExecute() {
		Weaver.callOriginal();
	}

	public void handleException(Throwable var1) {
		NewRelic.noticeError(var1);
		Weaver.callOriginal();
	}

	@Trace
	public void execute() {
		Weaver.callOriginal();
	}

	@Trace
	public void afterExecute(JobRunTime var1) {
		Weaver.callOriginal();
	}

}
