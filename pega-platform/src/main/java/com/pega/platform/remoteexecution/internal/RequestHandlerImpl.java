package com.pega.platform.remoteexecution.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.util.concurrent.ListenableFuture;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.MessageUtils;
import com.nr.instrumentation.pega.platform.Utils;
import com.pega.platform.remoteexecution.ResponseBucket;

@SuppressWarnings({ "rawtypes" })
@Weave
public abstract class RequestHandlerImpl {

	@Trace(dispatcher = true)
	public void handleRequest(RequestMessage requestMessage) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "RequestMessage-Class", requestMessage != null ? requestMessage.getClass().getName() : "null");
		MessageUtils.processInboundMessage(attributes, requestMessage);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public <P extends Serializable, R extends Serializable> ResponseBucket<R> sendRequest(RequestMessage<P, R> request) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "RequestMessage-Class", request != null ? request.getClass().getName() : "null");
		MessageUtils.processOutboundMessage(attributes, request);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace
	public void processJobWithAcknowledgments(RequestMessage requestMessage) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "RequestMessage-Class", requestMessage != null ? requestMessage.getClass().getName() : "null");
		MessageUtils.processInboundMessage(attributes, requestMessage);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public <P extends Serializable, R extends Serializable> Map<UUID, ListenableFuture<R>> sendRequest(RequestMessage<P, R> request, Collection<UUID> members) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "RequestMessage-Class", request != null ? request.getClass().getName() : "null");
		MessageUtils.processOutboundMessage(attributes, request);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
