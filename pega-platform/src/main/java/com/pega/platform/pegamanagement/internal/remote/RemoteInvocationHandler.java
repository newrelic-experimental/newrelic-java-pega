package com.pega.platform.pegamanagement.internal.remote;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.UUID;

import com.google.common.util.concurrent.ListenableFuture;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class RemoteInvocationHandler {

	@Trace(dispatcher = true)
	public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(aMethod != null) {
			String methodClass = aMethod.getDeclaringClass().getName();
			String methodName = aMethod.getName();
			traced.addCustomAttribute("Pega-MethodClass", methodClass);
			traced.addCustomAttribute("Pega-MethodName", methodName);
		}
		return Weaver.callOriginal();

	}
	
	@Trace
	public <R extends Serializable> R executeOnNode(final TaskInput aTaskInput, final String aNodeId) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public <P extends Serializable, R extends Serializable> ListenableFuture<R> executeJobOnNode(TaskInput aTaskInput, UUID uuid) {
		
		return Weaver.callOriginal();
	}
}
