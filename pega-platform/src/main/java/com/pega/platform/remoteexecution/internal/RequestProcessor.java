package com.pega.platform.remoteexecution.internal;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.MessageUtils;
import com.nr.instrumentation.pega.platform.Utils;

@Weave
public class RequestProcessor <P extends Serializable, R extends Serializable> {

	
	private RequestMessage<P, R> requestMessage = Weaver.callOriginal();
	//private ResponseHandler responseHandler = Weaver.callOriginal();
	
	@Trace(dispatcher = true)
	public void run() {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		
		
	
		Utils.addAttribute(attributes, "RequestMessage-Class", requestMessage != null ? requestMessage.getClass().getName() : "null");
		if(requestMessage != null) {
			MessageUtils.processInboundMessage(attributes, requestMessage);
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
