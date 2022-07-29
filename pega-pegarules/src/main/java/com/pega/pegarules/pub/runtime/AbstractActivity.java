package com.pega.pegarules.pub.runtime;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
//import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
//import com.nr.instrumentation.pega.pegarules.InstrumentationUtils;
import com.nr.instrumentation.pega.pegarules.PegaConfiguration;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.priv.LogHelper;
import com.pega.pegarules.pub.clipboard.ClipboardPage;

@Weave(type=MatchType.BaseClass)
public abstract class AbstractActivity {

	protected void pageRemove(ClipboardPage aPage) {
		
		String pegaName = Utils.getPegaName(getClass());
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+pegaName+"/pageRemove");
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			segment.end();
		} else {
			segment.ignore();
		}
	}

	protected void objOpen(ClipboardPage aTarget, String aStepPageName, String aLockInfoPageName, boolean aLock, boolean aUnlockOnCommit) {
		String pegaName = Utils.getPegaName(getClass());
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Activity",pegaName,"objOpen");
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+pegaName+"/objOpen");
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addClipboardPage(attributes, aTarget);
			ClipboardPage parent = aTarget.getParentPage();
			if(parent != null)
				Utils.addClipboardPage(attributes, "ParentPage", parent);
			Utils.addAttribute(attributes,"StepPageName", aStepPageName);
			Utils.addAttribute(attributes,"LockInfoPageName", aLockInfoPageName);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
	}

	protected ClipboardPage objOpenByHandle(String aHandle, String aStepPageName, String aLockInfoPageName, boolean aLock, boolean aUnlockOnCommit) {
		String pegaName = Utils.getPegaName(getClass());
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+pegaName+"/objOpenByHandle");
		long start = System.currentTimeMillis();
		ClipboardPage resultPage = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes, "Handle", aHandle);
			Utils.addAttribute(attributes, "StepPageName", aStepPageName);
			Utils.addAttribute(attributes, "LockInfoPageName", aLockInfoPageName);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
		
		return resultPage;
	}

	protected ClipboardPage objOpenByHandle(String aHandle, String aStepPageName, String aLockInfoPageName, boolean aLock, boolean aUnlockOnCommit, boolean aCheckSecondaryStorage) {
		String pegaName = Utils.getPegaName(getClass());
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+pegaName+"/objOpenByHandle");
		long start = System.currentTimeMillis();
		ClipboardPage resultPage = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes, "Handle", aHandle);
			Utils.addAttribute(attributes, "StepPageName", aStepPageName);
			Utils.addAttribute(attributes, "LockInfoPageName", aLockInfoPageName);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
		
		return resultPage;
	}

	protected void showApplet(ClipboardPage aStepPage, String aStepPageName) {
		String pegaName = Utils.getPegaName(getClass());
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+pegaName+"/showApplet");
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes, "ClipboardPage", aStepPage.getName());
			ClipboardPage parent = aStepPage.getParentPage();
			if(parent != null)
				Utils.addAttribute(attributes, "ParentClipboardPage", parent.getName());
			Utils.addAttribute(attributes, "StepPageName", aStepPageName);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
	}

	protected void showStream(ClipboardPage aStepPage, LogHelper aLog, String aClassDef, String aStreamName, String aKeyName, String aStreamType, boolean aHTMLReadOnly, String aFrameName) {
		String pegaName = Utils.getPegaName(getClass());
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+pegaName+"/showStream");
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes, "ClipboardPage", aStepPage.getName());
			ClipboardPage parent = aStepPage.getParentPage();
			if(parent != null)
				Utils.addAttribute(attributes, "ParentClipboardPage", parent.getName());
			Utils.addAttribute(attributes, "ClassDef", aClassDef);
			Utils.addAttribute(attributes, "StreamName", aStreamName);
			Utils.addAttribute(attributes, "KeyName", aKeyName);
			Utils.addAttribute(attributes, "StreamType", aStreamType);
			Utils.addAttribute(attributes, "FrameName", aFrameName);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
	}

	protected void showHtml(ClipboardPage aStepPage, LogHelper aLog, String aHtmlStream, boolean isReadOnly, String aHtmlFrame) {
		String pegaName = Utils.getPegaName(getClass());
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+pegaName+"/showHtml");
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes, "ClipboardPage", aStepPage.getName());
			ClipboardPage parent = aStepPage.getParentPage();
			if(parent != null)
				Utils.addAttribute(attributes, "ParentClipboardPage", parent.getName());
			Utils.addAttribute(attributes, "HtmlStream", aHtmlStream);
			Utils.addAttribute(attributes, "HtmlFrame", aHtmlFrame);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
	}

}