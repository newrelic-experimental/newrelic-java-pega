package com.pega.pegarules.priv.util.logging;

import java.util.HashMap;

import org.apache.logging.log4j.core.LogEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.logging.Utils;

@Weave
public abstract class SOAPAppenderPega {
	
	public abstract String getEndPointURL();
	public abstract String getServicePackage();
	public abstract String getServiceClass();
	public abstract String getUserName();
	
	@Trace(dispatcher = true)
	public void append(LogEvent aEvent) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addLogEvent(attributes, aEvent,this);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getInsights().recordCustomEvent("PegaError", attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	private void sendEvent(String aEventText, String aExceptionText) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("EventText", aEventText);
		traced.addCustomAttribute("ExceptionText", aExceptionText);
		
		Weaver.callOriginal();
	}
}
