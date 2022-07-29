package com.pega.platform.remoteexecution.internal;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.MessageUtils;
import com.pega.platform.remoteexecution.ResponseMessage;

@Weave(type = MatchType.Interface)
public class ResponseHandler {

	@Trace(dispatcher = true)
	public <P extends Serializable, R extends Serializable> void sendResponseMessage(ResponseMessage<R> response, RequestMessage<P, R> request) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","ResponseHandler",getClass().getSimpleName(),"sendResponseMessage");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		MessageUtils.processOutboundMessage(attributes, response);
		MessageUtils.processInboundMessage(attributes, request);
		Weaver.callOriginal();
	}
}
