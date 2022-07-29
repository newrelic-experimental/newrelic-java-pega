package com.pega.platform.remoteexecution.internal;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.Utils;

@Weave(type = MatchType.Interface)
public abstract class RequestMessageProcessor {

	@Trace
	public <P extends Serializable, R extends Serializable> void process(RequestMessage<P, R> var1) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addRequestMessage(attributes, var1);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","RequestMessageProcessor",getClass().getSimpleName(),"process");
		
		Weaver.callOriginal();
	}
}
