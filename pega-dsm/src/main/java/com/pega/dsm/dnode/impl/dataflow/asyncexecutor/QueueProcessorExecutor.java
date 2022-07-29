package com.pega.dsm.dnode.impl.dataflow.asyncexecutor;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.PublicAPI;

@Weave
public abstract class QueueProcessorExecutor {

	@Trace(dispatcher = true)
	public void execute(PublicAPI aTools, ClipboardPage aContextPage, ClipboardPage aDataFlowConfig) {
		Weaver.callOriginal();
	}
}
