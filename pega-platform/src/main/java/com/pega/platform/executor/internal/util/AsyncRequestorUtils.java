package com.pega.platform.executor.internal.util;

import java.util.function.Predicate;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.context.PRThread;
import com.pega.platform.executor.internal.IExecuteInAsyncRequestorType;

@Weave
public abstract class AsyncRequestorUtils {

	@Trace
	public Object executeInAsyncRequestor(IExecuteInAsyncRequestorType executeInAsyncReqType,
			Predicate<PRThread> condition) { 
		
		return Weaver.callOriginal();
	}
}
