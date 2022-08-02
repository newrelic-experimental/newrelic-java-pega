package com.pega.apache.axis;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;

@Weave
public abstract class SimpleChain {
	
	@Trace
	public abstract void invoke(MessageContext msgContext);
}
