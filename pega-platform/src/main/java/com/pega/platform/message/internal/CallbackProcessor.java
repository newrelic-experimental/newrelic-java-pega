package com.pega.platform.message.internal;

import java.io.Serializable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class CallbackProcessor {

	@Trace(dispatcher = true)
	public <E extends Serializable> void addMessage(String aSource, E aMessage) {
		
		Weaver.callOriginal();
	}
	
	@Weave
	static class ExecutionTask<E extends Serializable> {
		
		@NewField
		private Token token = null;
		
		public ExecutionTask(final String aSource, final E aMessage) {
			token = NewRelic.getAgent().getTransaction().getToken();
			
		}
		
		@Trace(async = true)
		public void run() {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}

	}
}
