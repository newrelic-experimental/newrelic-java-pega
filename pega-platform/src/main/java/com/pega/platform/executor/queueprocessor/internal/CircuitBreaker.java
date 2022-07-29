package com.pega.platform.executor.queueprocessor.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class CircuitBreaker {

	@Trace
	public boolean tryAndExecute(BatchTask task, long wait) {
		if(task.token == null) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				task.token = t;
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		return Weaver.callOriginal();
	}
}
