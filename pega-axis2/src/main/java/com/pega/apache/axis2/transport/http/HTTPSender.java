package com.pega.apache.axis2.transport.http;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis2.context.MessageContext;

import java.net.URL;

@Weave
public abstract class HTTPSender extends AbstractHTTPSender {

    @Trace
    public void send(MessageContext msgContext, URL url, String soapActionString) {
    	TracedMethod traced = NewRelic.getAgent().getTracedMethod();
    	if(httpVersion != null) {
    		if(!httpVersion.isEmpty()) {
    			traced.addCustomAttribute("HttpVersion", httpVersion);
    		} else {
    			traced.addCustomAttribute("HttpVersion", "Empty");
    		}
    	}
    	traced.addCustomAttribute("Chunked", chunked);
    	if(url != null) {
    		traced.addCustomAttribute("URL", url.toString());
    	}
    	if(soapActionString != null) {
    		if(!soapActionString.isEmpty()) {
    			traced.addCustomAttribute("soapActionString", soapActionString);
    		} else {
    			traced.addCustomAttribute("soapActionString", "Empty");
    		}
    	}
        Weaver.callOriginal();
    }

}
