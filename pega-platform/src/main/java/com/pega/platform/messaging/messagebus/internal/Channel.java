package com.pega.platform.messaging.messagebus.internal;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.MessageUtils;
import com.nr.instrumentation.pega.platform.Utils;
import com.pega.platform.messaging.messagebus.MessageHandler;

@Weave(type = MatchType.Interface)
public abstract class Channel<E extends Serializable> {
	
	public abstract String getName();

	@Trace
	public void publish(E message) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Channel",getClass().getSimpleName(),"publish");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		MessageUtils.processOutboundMessage(attributes, message);
		Utils.addAttribute(attributes, "Pega-ChannelClass", getClass().getName());
		Utils.addAttribute(attributes, "Message-Class", message.getClass().getName());
		Utils.addAttribute(attributes, "Channel-Name", getName());
		traced.addCustomAttributes(attributes);
		MessageProduceParameters params = MessageUtils.getProduceParameters(this, message);
		if(params != null) {
			traced.reportAsExternal(params);
		}
		
		NewRelic.getAgent().getInsights().recordCustomEvent("Pega_ChannelPublish", attributes);
		Weaver.callOriginal();
	}

	@SuppressWarnings("unchecked")
	public void registerMessageHandler(MessageHandler<E> handler) {
		handler = (MessageHandler<E>) MessageUtils.getWrapper(handler, this);
		Weaver.callOriginal();
	}

	public boolean unRegisterMessageHandler(MessageHandler<E> handler) {
		MessageUtils.removeDelegate(handler);
		boolean b = Weaver.callOriginal();
		
		return b;
	}

}
