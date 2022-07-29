package com.pega.platform.messaging.messagebus;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.MessageUtils;
import com.nr.instrumentation.pega.platform.Utils;

@Weave(type = MatchType.Interface, originalName = "com.pega.platform.messaging.messagebus.MessageHandler")
public abstract class MessageHandler_instrumentation<E extends Serializable> {
	
	@Trace
	public void onMessage(E message) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","MessageHandler",getClass().getSimpleName(),"onMessage");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Pega-MessageHandlerClass", getClass().getName());
		Utils.addAttribute(attributes,"Pega-MessageClass", message.getClass().getName());
		MessageUtils.processInboundMessage(attributes, message);
		traced.addCustomAttributes(attributes);
		
		NewRelic.getAgent().getInsights().recordCustomEvent("Pega_OnMessage", attributes);
		Weaver.callOriginal();
	}

}
