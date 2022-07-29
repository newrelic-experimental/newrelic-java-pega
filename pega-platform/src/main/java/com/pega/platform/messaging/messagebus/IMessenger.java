package com.pega.platform.messaging.messagebus;

import java.io.Serializable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class IMessenger<E extends Serializable> {

	@Trace
	public void publish(E message) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","IMessenger",getClass().getSimpleName(),"publish");
		
		Weaver.callOriginal();
	}

}
