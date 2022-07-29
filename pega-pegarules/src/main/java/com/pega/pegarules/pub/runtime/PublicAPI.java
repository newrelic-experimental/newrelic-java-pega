package com.pega.pegarules.pub.runtime;

import java.io.InputStream;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.PegaConfiguration;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.PRRequestor;
import com.pega.pegarules.pub.context.PRThread;
import com.pega.pegarules.pub.util.PRFile;
import com.pega.pegarules.pub.util.StringMap;

@Weave(type=MatchType.Interface)
public abstract class PublicAPI {

	public void doAction(StringMap aKeys, ClipboardPage var2, ParameterPage var3) {
		String actionName = aKeys.getString("pyActivityName");
		Segment segment = null;
		if(actionName != null && !actionName.isEmpty()) {
			segment = NewRelic.getAgent().getTransaction().startSegment("PublicAPI/"+getClass().getSimpleName()+"/doAction/"+actionName);
		} else {
			segment = NewRelic.getAgent().getTransaction().startSegment("PublicAPI/"+getClass().getSimpleName()+"/doAction");			
		}

		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, var2);
		Utils.addAttribute(attributes, "ActivityClassName", getActivityClassName());
		Utils.addPRThread(attributes, getThread());
		Utils.addPRRequestor(attributes, getRequestor());
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if (end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD || PegaConfiguration.includeActivity(actionName)) {
			Utils.addStringMap(attributes, aKeys);
			Utils.addParameterPage(attributes, var3);
			Utils.addAttribute(attributes, "ActionName", actionName);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		} 
	}

	public void doActivity(StringMap aKeys, ClipboardPage aNewPrimaryPage, ParameterPage aNewParam) {
		String activityName = aKeys.getString("pyActivityName");

		Segment segment = null;
		if(activityName != null && !activityName.isEmpty()) {
			segment = NewRelic.getAgent().getTransaction().startSegment("PublicAPI/"+getClass().getSimpleName()+"/doActivity/"+activityName);
		} else {
			segment = NewRelic.getAgent().getTransaction().startSegment("PublicAPI/"+getClass().getSimpleName()+"/doActivity");			
		}

		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, aNewPrimaryPage);
		Utils.addPRThread(attributes, getThread());
		Utils.addPRRequestor(attributes, getRequestor());
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD || (activityName != null && PegaConfiguration.includeActivity(activityName))) {

			Utils.addStringMap(attributes, aKeys);
			Utils.addParameterPage(attributes, aNewParam);
			Utils.addAttribute(attributes, "ActivityName", activityName);
			String activityClass = aKeys.getString("pxObjClass");
			Utils.addAttribute(attributes, "ActivityClass", activityClass);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
	}

	@Trace
	public String getStream(StringMap var1, ClipboardPage var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"getStream");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, var2);
		Utils.addPRThread(attributes, getThread());
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}

	@Trace
	public String getStream(StringMap var1, ClipboardPage var2, long var3) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"getStream");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, var2);
		Utils.addPRThread(attributes, getThread());
		Utils.addPRRequestor(attributes, getRequestor());
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}

	@Trace
	public String sendFile(PRFile aFile, boolean aDeleteFile, StringMap aHttpHeaders, boolean aSendForDownload) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"sendFile");
		traced.addCustomAttribute("Pega-PRFile", aFile.getName());
		return Weaver.callOriginal();
	}

	@Trace
	public String sendFile(byte[] aFileData, String aFileName, boolean aPersistFileToServiceExport, StringMap aHttpHeaders, boolean aSendForDownload) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"sendFile");
		traced.addCustomAttribute("Pega-FileName", aFileName);
		return Weaver.callOriginal();
	}

	@Trace
	public String sendFile(InputStream aFileInputStream, String aFileName, StringMap aHttpHeaders, boolean aSendForDownload) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"sendFile");
		traced.addCustomAttribute("Pega-FileName", aFileName);
		return Weaver.callOriginal();
	}

	@Trace
	public String sendFile(StringMap aInstanceKeys, String aFileSourceReference, boolean aIsBase64Encoded, String aFileNameReference, String aFileName, 
			boolean aPersistFileToServiceExport, StringMap aHttpHeaders, boolean aSendForDownload) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"sendFile");
		traced.addCustomAttribute("Pega-FileName", aFileName);
		traced.addCustomAttribute("Pega-FileNameReference", aFileNameReference);
		traced.addCustomAttribute("Pega-FileSourceReference", aFileSourceReference);
		return Weaver.callOriginal();
	}

	@Trace
	public String sendFile(String aInstanceHandle, String aFileSourceReference, boolean aIsBase64Encoded, String aFileNameReference, String aFileName, 
			boolean aPersistFileToServiceExport, StringMap aHttpHeaders,
			boolean aSendForDownload) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"sendFile");
		traced.addCustomAttribute("Pega-FileName", aFileName);
		traced.addCustomAttribute("Pega-FileNameReference", aFileNameReference);
		traced.addCustomAttribute("Pega-FileSourceReference", aFileSourceReference);
		traced.addCustomAttribute("Pega-InstanceHandle", aInstanceHandle);
		return Weaver.callOriginal();
	}

	@Trace
	public String sendFile(ClipboardPage aInstancePage, String aFileSourceReference, boolean aIsBase64Encoded, String aFileNameReference, String aFileName, boolean aPersistFileToServiceExport,
			StringMap aHttpHeaders, boolean aSendForDownload) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PublicAPI",getClass().getSimpleName(),"sendFile");
		traced.addCustomAttribute("Pega-FileName", aFileName);
		traced.addCustomAttribute("Pega-FileNameReference", aFileNameReference);
		traced.addCustomAttribute("Pega-FileSourceReference", aFileSourceReference);
		return Weaver.callOriginal();
	}


	public abstract PRThread getThread();
	public abstract String getActivityClassName();
	public abstract ClipboardPage getPrimaryPage();

	public abstract ClipboardPage getStepPage();
	public abstract PRRequestor getRequestor();

}
