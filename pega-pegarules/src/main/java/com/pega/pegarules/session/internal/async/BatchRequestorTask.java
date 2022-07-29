package com.pega.pegarules.session.internal.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.session.external.async.IBatchRequestorTask;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@SuppressWarnings("rawtypes")
@Weave
public abstract class BatchRequestorTask implements IBatchRequestorTask {

	@NewField
	public Token token = null;
	
	public abstract String getId();
	String mClassName = Weaver.callOriginal();
	
	String mActivityName = Weaver.callOriginal();

	public BatchRequestorTask(String aClassName, String aActivityName, ParameterPage aParameterPage,
			ClipboardPage aPrimaryPage, List aOtherPages, AgentQueue aAgentTask, String aRequestorType,
			IPRRequestor aCurrentRequestor, boolean aIsDetachedTask, boolean aSuppressShowHTML,
			ClipboardPage aQueueItem, String aTenantUserId, String aTenantId) {

	}

	@SuppressWarnings("unused")
	private BatchRequestorTask() {
	}

	@Trace(async = true)
	public void run() {
		if(token != null ) {
			token.linkAndExpire();
			token = null;
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		if(mActivityName != null && !mActivityName.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "PegaBatch", "Pega","Activity",mActivityName);
			traced.setMetricName("Custom","BatchRequestorTask","run",mActivityName);
			Utils.addAttribute(attributes, "ActivityName", mActivityName);
			
		} else {
			traced.setMetricName("Custom","BatchRequestorTask","run");
			Utils.addAttribute(attributes, "ActivityName", "null");
		}
		Utils.addAttribute(attributes, "ID", getId());
		Utils.addAttribute(attributes, "Run-Method", "No args");
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}

	@Trace(async = true)
	public Object run(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInput, Map aInfo) {
		if(token != null ) {
			token.linkAndExpire();
			token = null;
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, aRequestor);
		Utils.addAttribute(attributes, "Run-Method", "Has args");
		if(mActivityName != null && !mActivityName.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "PegaBatch", "Pega","Activity",mActivityName);
			traced.setMetricName("Custom","BatchRequestorTask","run",mActivityName);
			Utils.addAttribute(attributes, "ActivityName", mActivityName);
			
		} else {
			traced.setMetricName("Custom","BatchRequestorTask","run");
			Utils.addAttribute(attributes, "ActivityName", "null");
		}
		traced.addCustomAttributes(attributes);
		
		return Weaver.callOriginal();
	}

	public abstract boolean isExecutionRequestTask();
	public abstract boolean isAgent();
}
