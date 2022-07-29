package com.pega.pegarules.session.internal.async.backgroundprocessing;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.external.mgmt.IPRThread;
import com.pega.pegarules.session.internal.async.agent.PRTimerTask;

@Weave(type = MatchType.BaseClass)
public abstract class AbstractQueueProcessorTimerTask extends PRTimerTask {

	@Trace(dispatcher = true)
	public void run() {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "PRTimerTask", "PRTimerTask","AbstractQueueProcessorTimerTask",getClass().getSimpleName());
		Weaver.callOriginal();
	}
	
	public void run(IPRThread var1) {
		Weaver.callOriginal();
	}
}
