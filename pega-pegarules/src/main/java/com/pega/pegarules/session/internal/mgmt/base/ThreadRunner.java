package com.pega.pegarules.session.internal.mgmt.base;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.context.ProcessingStatus;

@Weave
public abstract class ThreadRunner  extends ThreadPassivation {
	
	public abstract ProcessingStatus getActivityStatus();
	public abstract ProcessingStatus getStepStatus();
	
	@Trace
	public void runActivities() {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "PRThread-Name", getName());
		Utils.addAttribute(attributes, "PrimaryPageName", 	getMyPrimaryPageName());
		Utils.addClipboardPage(attributes,"ParentPage",getParentPage());
		Utils.addProcessingStatus(attributes, "Activity", getActivityStatus());
		Utils.addProcessingStatus(attributes, "Step", getStepStatus());
		
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public void runActivitiesAlt() {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "PRThread-Name", getName());
		Utils.addAttribute(attributes, "PrimaryPageName", 	getMyPrimaryPageName());
		Utils.addClipboardPage(attributes,"ParentPage",getParentPage());
		Utils.addProcessingStatus(attributes, "Activity", getActivityStatus());
		Utils.addProcessingStatus(attributes, "Step", getStepStatus());
		
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
