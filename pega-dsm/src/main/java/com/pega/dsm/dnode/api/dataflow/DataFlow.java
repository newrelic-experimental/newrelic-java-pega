package com.pega.dsm.dnode.api.dataflow;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.dsm.dnode.api.data.LookupKeys;
import com.pega.dsm.dnode.api.stream.DataObservable;
import com.pega.dsm.dnode.impl.dataflow.DataFlowThreadContext;
import com.pega.dsm.dnode.impl.dataflow.RunConfiguration;
import com.pega.pegarules.pub.clipboard.ClipboardPage;

@Weave
public abstract class DataFlow {

	@Trace(dispatcher = true)
	public DataObservable<DataFlowMetrics> execute(DataFlowThreadContext threadContext) {
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public DataObservable<ClipboardPage> executeBrowseByKeys(LookupKeys keys, int maxRecords, RunConfiguration runConfig) {
		
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public DataObservable<ClipboardPage> executeOnPages(DataObservable<ClipboardPage> pages, RunConfiguration runConfiguration) {
		
		return Weaver.callOriginal();
	}
}
