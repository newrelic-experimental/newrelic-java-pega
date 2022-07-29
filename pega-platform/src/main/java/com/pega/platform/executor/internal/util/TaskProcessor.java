package com.pega.platform.executor.internal.util;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class TaskProcessor {
	
	@Trace
	public <T> T execute(TaskWithThread<T> task) {
		return Weaver.callOriginal();
	}

}
