package com.pega.platform.executor.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.context.PRThread;

@Weave(type = MatchType.Interface)
public class IExecuteInAsyncRequestorType {

	@Trace
	public Object execute(PRThread thread) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","IExecuteInAsyncRequestorType",getClass().getName(),"execute");
		return Weaver.callOriginal();
	}
}
