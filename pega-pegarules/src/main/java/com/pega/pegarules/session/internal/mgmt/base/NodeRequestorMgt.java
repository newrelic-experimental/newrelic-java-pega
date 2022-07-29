package com.pega.pegarules.session.internal.mgmt.base;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;
import com.pega.pegarules.session.external.mgmt.IPRThread;

@Weave(type = MatchType.BaseClass)
public abstract class NodeRequestorMgt {

	@SuppressWarnings("rawtypes")
	@Trace
	public static Object cleanupInner(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs,Map aInfo) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "NodeRequestorMgt", "NodeRequestorMgt","CleanupInner");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, aRequestor);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace
	public static void cleanupRequestor(IPRRequestor req, int aDestroy, IPRThread aThread) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "NodeRequestorMgt", "NodeRequestorMgt","CleanupRequestor");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, req);
		Utils.addIPRThread(attributes, aThread);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public static void executeActivity(IPRRequestor aRequestor, String activityName) {
		if(activityName != null && !activityName.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "NodeRequestorMgt", "NodeRequestorMgt","ExecuteActivity",activityName);
		} else {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "NodeRequestorMgt", "NodeRequestorMgt","ExecuteActivity","UnknownActivity");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, aRequestor);
		Utils.addAttribute(attributes, "ActivityName", activityName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
