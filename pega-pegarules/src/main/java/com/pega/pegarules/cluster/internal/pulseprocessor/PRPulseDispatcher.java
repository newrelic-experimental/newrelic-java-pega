package com.pega.pegarules.cluster.internal.pulseprocessor;

import java.util.Collection;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.context.PRPulseMessage;

@Weave
public abstract class PRPulseDispatcher {

	@Trace(dispatcher = true)
	public void processPulseMessages(Collection<PRPulseMessage> aPulseMessages) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, true, "PRPulseDispatcher", "ProcessPulseMessages");
		if(aPulseMessages.isEmpty()) {
			NewRelic.getAgent().getTransaction().ignore();
		}
		Weaver.callOriginal();
	}
}
