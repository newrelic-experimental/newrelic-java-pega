package com.pega.pegarules.session.external.engineinterface.service;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@Weave(type=MatchType.BaseClass)
public abstract class EngineAPI {
	
	public abstract ClipboardPage getPrimaryPage();
	public abstract IPRRequestor getRequestor();
	public abstract String getRequestorId();
	public abstract ClipboardPage getRequestorPage();
	
	@Trace
	public void processRequest() {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","EngineAPI",getClass().getSimpleName(),"processRequest");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, "Primary",getPrimaryPage());
		//Utils.addClipboardPage(attributes, "Requestor",getRequestorPage());
		Utils.addAttribute(attributes, "RequestID", getRequestorId());
		Utils.addIPRRequestor(attributes, getRequestor());
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@SuppressWarnings("rawtypes")
	@Trace
	public void processRequestInner(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","EngineAPI",getClass().getSimpleName(),"processRequestInner");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, "Primary",getPrimaryPage());
		Utils.addIPRRequestor(attributes, getRequestor());
		Utils.addAttribute(attributes, "aBase1", aBase1 != null ? aBase1.getClass().getName() : "null");
		Utils.addAttribute(attributes, "aBase2", aBase2 != null ? aBase2.getClass().getName() : "null");
		if(aInputs == null || aInputs.length == 0) {
			Utils.addAttribute(attributes,"aInputs","No Inputs");
		} else {
			for(int i=0;i<aInputs.length;i++) {
				Utils.addAttribute(attributes, "aInputs["+i+"]", aInputs[i]);
			}
		}
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public void runActivities() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","EngineAPI",getClass().getSimpleName(),"runActivities");
		Weaver.callOriginal();
		
	}
	
	@Trace
	protected void processRequestInnerWithThreadLock(IPRRequestor aRequestor) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","EngineAPI",getClass().getSimpleName(),"processRequestInnerWithThreadLock");
		Weaver.callOriginal();
	}
}
