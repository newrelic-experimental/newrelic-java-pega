package com.pega.platform.remoteexecution.internal;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.MessageUtils;
import com.nr.instrumentation.pega.platform.Utils;
import com.pega.platform.remoteexecution.ResponseMessage;

@Weave
public abstract class MessagingBroker {

	@Trace(dispatcher = true)
	public <P extends Serializable, R extends Serializable> void publishToTopic(RequestMessage<P, R> message) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		if (message != null) {
			MessageUtils.processOutboundMessage(attributes, message);
		}
		Utils.addAttribute(attributes, "RequestMessage-Class", message != null ? message.getClass().getName() : "null");
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public <S extends Serializable> void sendResponse(ResponseMessage<S> response, String remoteResponseChannel) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		if(remoteResponseChannel != null) {
			Utils.addAttribute(attributes, "RemoteResponseChannel", remoteResponseChannel);
		}
		Utils.addResponseMessage(attributes, response);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
