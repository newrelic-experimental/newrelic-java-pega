package com.pega.platform.pegamanagement.internal.remote;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ClusterInvocationHandler {

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
	
	@Trace(dispatcher = true)
	public <P extends Serializable, R extends Serializable> Map<String, ResultFromOneNode<R>> executeOnCluster(
			TaskInput aTaskInput) {
		return Weaver.callOriginal();
	}
}
