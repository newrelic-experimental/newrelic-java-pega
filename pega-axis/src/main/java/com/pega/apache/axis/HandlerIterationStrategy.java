package com.pega.apache.axis;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class HandlerIterationStrategy {

	@Trace
	public void visit(Handler handler, MessageContext msgContext) {
		String handlerName = handler.getName() != null ? handler.getName() : "UnknownHandler";
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HandlerIterationStrategy",getClass().getSimpleName(),handlerName});
		Weaver.callOriginal();
	}

}
