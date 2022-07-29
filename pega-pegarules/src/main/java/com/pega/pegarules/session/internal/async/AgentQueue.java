package com.pega.pegarules.session.internal.async;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.session.internal.async.agent.PRTimerTask;

@Weave
public abstract class AgentQueue extends PRTimerTask {
	
	public abstract String getActivityName();
	public abstract String getAgentName();
	public abstract String getRuleSetName();

	@Override
	@Trace(dispatcher = true)
	public void run() {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new  HashMap<String, Object>();
		String activityName = getActivityName();
		Utils.addAttribute(attributes, "ActivityName", activityName);
		Utils.addAttribute(attributes, "AgentName", getAgentName());
		Utils.addAttribute(attributes, "RuleSetName", getRuleSetName());
		Utils.addAttribute(attributes, "ID", getId());
		Utils.addAttribute(attributes, "Label", mLabel);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","AgentQueue","run");
		
		if(activityName != null && !activityName.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, false, "PRTimerTask", "PRTimerTask","AgentQueue",activityName);
		} else {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, false, "PRTimerTask", "PRTimerTask","AgentQueue");
		}
		Weaver.callOriginal();
	}

	void runEnd(final Throwable aProblem, final boolean aTemporaryProblem, final boolean aManualTermination) {
		Weaver.callOriginal();
	}
		
}
