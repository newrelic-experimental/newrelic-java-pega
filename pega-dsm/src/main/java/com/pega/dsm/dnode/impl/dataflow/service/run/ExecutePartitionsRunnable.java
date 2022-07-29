package com.pega.dsm.dnode.impl.dataflow.service.run;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.pega.dsm.Utils;
import com.pega.dsm.dnode.api.dataflow.service.DataFlowRunConfig;
import com.pega.dsm.dnode.impl.dataflow.model.RuntimeData.RunType;

@Weave
public abstract class ExecutePartitionsRunnable {
	
	private final DataFlowRunConfig runConfig = Weaver.callOriginal();
	private final RunType runType = Weaver.callOriginal();

	@Trace(dispatcher = true)
	public ThreadExecutionResult call() {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addDataFlowRunConfig(attributes, runConfig);
		Utils.addRunType(attributes, runType);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		if(runConfig != null) {
			String instanceName = runConfig.getServiceInstanceName();
			if(instanceName != null && !instanceName.isEmpty()) {
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "ExecutePartitions", "ExecutePartitionsRunnable",instanceName);
			}
		}
		return Weaver.callOriginal();
	}
}
