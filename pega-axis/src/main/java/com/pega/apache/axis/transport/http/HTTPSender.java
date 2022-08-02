package com.pega.apache.axis.transport.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pegaaxis.PegaAxisHeaders;
import com.pega.apache.axis.MessageContext;

@Weave
public abstract class HTTPSender {

	public abstract String getName();

	@Trace(dispatcher=true)
	public void invoke(MessageContext msgContext) {
		String handlerName = getName();
		if(handlerName == null) {
			handlerName = "UnknownHandler";
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HTTPSender",handlerName});

		SOAPMessage reqMessage = msgContext.getRequestMessage();
		if(reqMessage != null) {
			MimeHeaders mimeHeaders = reqMessage.getMimeHeaders();
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(new PegaAxisHeaders(null, mimeHeaders));
		}
        URL targetURL = null;
        
        try {
        	String targetStr = msgContext.getStrProp(MessageContext.TRANS_URL);
        	if(targetStr != null)
        		targetURL = new URL(msgContext.getStrProp(MessageContext.TRANS_URL));
		} catch (Exception e) {
			AgentBridge.getAgent().getLogger().log(Level.FINEST,e, "Exception getting URL");
		}
        if(targetURL != null) {
        	try {
				URI uri = targetURL.toURI();
				HttpParameters params = HttpParameters.library("Pega-Axis").uri(uri).procedure(handlerName).noInboundHeaders().build();
				NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
			} catch (URISyntaxException e) {
				AgentBridge.getAgent().getLogger().log(Level.FINEST,e, "Exception getting URI from URL");
			}
        }
		Weaver.callOriginal();
		SOAPMessage respMessage = msgContext.getResponseMessage();
		if(respMessage != null) {
			MimeHeaders mimeHeaders = respMessage.getMimeHeaders();
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.HTTP, new PegaAxisHeaders(mimeHeaders, null));
		}
	}
	
}
