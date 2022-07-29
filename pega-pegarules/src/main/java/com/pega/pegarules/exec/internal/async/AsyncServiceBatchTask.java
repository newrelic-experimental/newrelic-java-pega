package com.pega.pegarules.exec.internal.async;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.exec.external.async.AsyncServiceInputData;
import com.pega.pegarules.exec.external.async.ExecutionStateData;
import com.pega.pegarules.session.external.async.IBatchRequestorTask;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;
import com.pega.pegarules.session.external.mgmt.IPRThread;

@Weave(type = MatchType.BaseClass)
public abstract class AsyncServiceBatchTask implements IBatchRequestorTask {

	public AsyncServiceBatchTask(AsyncServiceInputData aInputData, ExecutionStateData aExecStateData,
			IPRThread aThread) {

	}

	@NewField
	public Token token = null;

	public abstract String getId();

	@Trace
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "ID", getId());
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","AsyncServiceBatchTask",getClass().getSimpleName(),"run");
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}

	@SuppressWarnings("rawtypes")
	@Trace
	public Object run(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "ID", getId());
		Utils.addIPRRequestor(attributes, aRequestor);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();

		traced.setMetricName("Custom","AsyncServiceBatchTask",getClass().getSimpleName(),"run");
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
