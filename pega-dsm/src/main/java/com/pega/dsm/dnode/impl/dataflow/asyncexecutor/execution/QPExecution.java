package com.pega.dsm.dnode.impl.dataflow.asyncexecutor.execution;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class QPExecution {

	@Trace(dispatcher = true)
	public void run() {
		Weaver.callOriginal();
	}
}
