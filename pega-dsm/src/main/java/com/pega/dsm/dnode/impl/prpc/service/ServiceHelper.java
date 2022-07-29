package com.pega.dsm.dnode.impl.prpc.service;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.dsm.dnode.util.OperationWithLock;
import com.pega.dsm.dnode.util.PrpcRunnable;
import com.pega.pegarules.pub.runtime.PublicAPI;

@Weave
public abstract class ServiceHelper {
	
	@Trace(dispatcher = true)
	public <T> T executeInPrpcContextInternal(PrpcRunnable<T> runnable) {
		if(runnable.token == null) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				runnable.token = t;
			} else if(t != null) {
				t.expire();
				t = null;
			}
			
		}
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public boolean executeWithLockInternal(PublicAPI tools, OperationWithLock.LockedOperation operation, String lockName) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-LockName", lockName);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public boolean executeWithLockInternal(PublicAPI tools, OperationWithLock.LockedOperation operation,String lockName, int retries) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-LockName", lockName);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-LockRetries", retries);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public boolean executeWithLockInternal(PublicAPI tools, OperationWithLock.LockedOperation operation, String lockName, int retries, long lockExpireTimeMinutes) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-LockName", lockName);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-LockRetries", retries);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Pega-LockExpireMinutes", lockExpireTimeMinutes);
		return Weaver.callOriginal();
	}
	

}
