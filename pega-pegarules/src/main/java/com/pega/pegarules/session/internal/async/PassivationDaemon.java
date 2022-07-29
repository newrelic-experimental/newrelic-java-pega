package com.pega.pegarules.session.internal.async;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@Weave
public abstract class PassivationDaemon {

	@SuppressWarnings("rawtypes")
	@Trace
	public Object run(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "PassivationDaemon", "PassivationDaemon","run");
		return Weaver.callOriginal();
	}
}
