package com.pega.pegarules.session.internal.async;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.internal.async.agent.PRTimerTask;

@Weave
public abstract class MasterForRequestors extends PRTimerTask {

	@Override
	@Trace(dispatcher = true)
	public void run() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","MasterForRequestors","run");
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, false, "PRTimerTask", "PRTimerTask","MasterForRequestors");
		Weaver.callOriginal();
	}

}
