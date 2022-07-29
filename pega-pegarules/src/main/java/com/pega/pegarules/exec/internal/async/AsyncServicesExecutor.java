package com.pega.pegarules.exec.internal.async;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.session.external.async.IBatchRequestorTask;

@Weave
public abstract class AsyncServicesExecutor {

	@Trace
	public void execute(IBatchRequestorTask aTask) {
		if(aTask instanceof AsyncServiceBatchTask) {
			AsyncServiceBatchTask aBatch = (AsyncServiceBatchTask)aTask;
			if(aBatch.token == null) {
				Token t = NewRelic.getAgent().getTransaction().getToken();
				if(t != null && t.isActive()) {
					aBatch.token = t;
				} else if(t != null) {
					t.expire();
					t = null;
				}
			}
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIBatchRequestorTask(attributes, aTask);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
