package com.pega.platform.executor.jobscheduler.scheduler.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.platform.executor.jobscheduler.scheduler.JobRunTime;

@Weave(type=MatchType.BaseClass)
public abstract class JobExecutionTemplate {

	@Trace(dispatcher = true)
	public void executeJob(JobRunTime job) {
		Weaver.callOriginal();
	}

	@Trace
	void beforeExecute() {
		Weaver.callOriginal();
	}

	void handleException(Throwable var1) {
		NewRelic.noticeError(var1);
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	void execute() {
		Weaver.callOriginal();
	}

	@Trace
	void afterExecute(JobRunTime var1) {
		Weaver.callOriginal();
	}

}
