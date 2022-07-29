package com.pega.pegarules.monitor.internal;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@SuppressWarnings("rawtypes")
@Weave
public abstract class UsageDaemonImpl {

	@Trace
	public void processUsageData(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "UsageDaemon","UsageDaemon", "ProcessUsageData");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, aRequestor);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public void processSnapshotOnDemand(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "UsageDaemon","UsageDaemon", "ProcessSnapshotOnDemand");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, aRequestor);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
