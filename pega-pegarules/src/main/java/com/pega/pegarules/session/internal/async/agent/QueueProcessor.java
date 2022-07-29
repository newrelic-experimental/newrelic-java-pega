package com.pega.pegarules.session.internal.async.agent;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.session.external.async.IAgentQueue;
import com.pega.pegarules.session.external.mgmt.IPRThread;
import com.pega.pegarules.session.internal.async.BatchRequestorTask;

@Weave
public abstract class QueueProcessor {

	@Trace
	public static IAgentQueue execute(IPRThread aThread, String aRuleSetName, String aAgentName, String aClassName,
			String aActivityName, char aMode) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "RuleSetName", aRuleSetName);
		Utils.addAttribute(attributes, "AgentName", aAgentName);
		Utils.addAttribute(attributes, "ActivityName", aActivityName);
		Utils.addAttribute(attributes, "ClassName", aClassName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace
	public static void execute(BatchRequestorTask aTask, IPRThread aThread, String aClassName, String aActivityName,
			ClipboardPage aPrimaryPage, ParameterPage aParameterPage) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "ActivityName", aActivityName);
		Utils.addAttribute(attributes, "ClassName", aClassName);
		Utils.addClipboardPage(attributes, aPrimaryPage);
		Utils.addBatchRequestorTask(attributes, aTask);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}

//	@Trace
//	private static void executeBatchTask(IBatchRequestorTask aTask, IPRThread aThread, String aClassName,
//			String aActivityName, ClipboardPage aPrimaryPage, ParameterPage aParameterPage) {
//		HashMap<String, Object> attributes = new HashMap<String, Object>();
//		Utils.addAttribute(attributes, "ActivityName", aActivityName);
//		Utils.addAttribute(attributes, "ClassName", aClassName);
//		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
//		Weaver.callOriginal();
//	}
//	
//	@Trace
//	private static boolean runActivity(IExecutable aExecutable, String aClass, String aName, ClipboardPage aPrimaryPage,
//			ParameterPage aParameterPage, boolean aIsQueueProcessing) {
//		HashMap<String, Object> attributes = new HashMap<String, Object>();
//		Utils.addAttribute(attributes, "ActivityName", aName);
//		Utils.addAttribute(attributes, "ClassName", aClass);
//		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
//		return Weaver.callOriginal();
//	}
}
