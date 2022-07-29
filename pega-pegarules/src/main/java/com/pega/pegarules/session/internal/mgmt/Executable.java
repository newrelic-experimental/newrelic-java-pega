package com.pega.pegarules.session.internal.mgmt;

import java.util.HashMap;
import java.util.function.BiConsumer;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.PegaConfiguration;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.priv.generator.LibrarySupport.LibraryRuntimeMethod;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.PRThread;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.pub.util.StringMap;
import com.pega.pegarules.session.external.mgmt.IExecutable;

@Weave
public class Executable {
	
	private String mActivityClassName = Weaver.callOriginal();
	private String mActivityRuleSet = Weaver.callOriginal();
	private String mActivityStepDetails = Weaver.callOriginal();
	private String mActivityType = Weaver.callOriginal();
	private String mPresumedClassName = Weaver.callOriginal();

	
	@Trace
	public ParameterPage doAutomation(StringMap aKeys, ParameterPage input) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "ActivityClassName", mActivityClassName);
		Utils.addAttribute(attributes, "ActivityRuleSet", mActivityRuleSet);
		Utils.addAttribute(attributes, "ActivityStepDetails", mActivityStepDetails);
		Utils.addAttribute(attributes, "ActivityType", mActivityType);
		Utils.addAttribute(attributes, "PresumedClassName", mPresumedClassName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace
	public void doDecision(StringMap aKeys, IExecutable context, ClipboardPage stepPage, boolean ignoreCircumstancing, BiConsumer<Executable, String> decisionResultConsumer) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "ActivityClassName", mActivityClassName);
		Utils.addAttribute(attributes, "ActivityRuleSet", mActivityRuleSet);
		Utils.addAttribute(attributes, "ActivityStepDetails", mActivityStepDetails);
		Utils.addAttribute(attributes, "ActivityType", mActivityType);
		Utils.addAttribute(attributes, "PresumedClassName", mPresumedClassName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	public ClipboardPage executeActivityInContext(String aThreadName, StringMap aKeys, ParameterPage aParams,
			String aPageName, boolean aEnableDeclarativeProcessing) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Pega/Executable/executeActivityInContext");
		
		long start = System.currentTimeMillis();
		ClipboardPage result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes, "ActivityClassName", mActivityClassName);
			Utils.addAttribute(attributes, "ActivityRuleSet", mActivityRuleSet);
			Utils.addAttribute(attributes, "ActivityStepDetails", mActivityStepDetails);
			Utils.addAttribute(attributes, "ActivityType", mActivityType);
			Utils.addAttribute(attributes, "ThreadName", aThreadName);
			Utils.addAttribute(attributes, "PageName", aPageName);
			
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
		
		return result;
	}
	
	@Trace
	public void executeCEPEventAction(ClipboardPage actionPage) {
		Weaver.callOriginal();
	}
	
	@Trace
	public void executeCommandLineExtract(String[] args, PRThread thread) {
		Weaver.callOriginal();
	}
	
	@Trace
	public void executeReport(ClipboardPage contentPage, ParameterPage paramPage) {
		Weaver.callOriginal();
	}
	
	@Trace
	public ClipboardPage invokeConnector(ClipboardPage aConnector, ClipboardPage aStepPage, ParameterPage aParams) {
		return Weaver.callOriginal();
	}
	
	public Object invokeLibraryRuntime(Object aArg1, Object aArg2, Object aArg3) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Pega/Executable/invokeLibraryRuntime");
		long start = System.currentTimeMillis();
		Object result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(start - end >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			segment.end();
		} else {
			segment.ignore();
		}
		return result;
	}
	
	public Object invokeLibraryRuntimeMethod(LibraryRuntimeMethod libraryRuntimeMethod, Object... aArgs) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Pega/Executable/invokeLibraryRuntimeMethod");
		
		long start = System.currentTimeMillis();
		Object result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(start - end >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			segment.addCustomAttribute("Pega-LibraryRuntimeMethod", libraryRuntimeMethod.name());
			segment.end();
		} else {
			segment.ignore();
		}
		return result;
	}
	
	@Trace
	public void performArchive() {
		Weaver.callOriginal();
	}
	
	@Trace
	public void performIndexing() {
		Weaver.callOriginal();
	}
	
	@Trace
	public void performPurge() {
		Weaver.callOriginal();
	}
	
	@Trace
	public void performVacuum(String schemaName, String tableName) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "schemaName", schemaName);
		Utils.addAttribute(attributes, "tableName", tableName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public boolean queueActivityForAsynchronousExecution(StringMap aActivityKeys, ClipboardPage aPrimaryPage,
			ParameterPage aParameterPage) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public boolean queueActivityForAsynchronousExecution(StringMap aActivityKeys, ClipboardPage aPrimaryPage,
			ParameterPage aParameterPage, String aPoolID) {
		return Weaver.callOriginal();
	}
}
