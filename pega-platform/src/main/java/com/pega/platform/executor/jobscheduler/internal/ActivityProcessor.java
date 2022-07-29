package com.pega.platform.executor.jobscheduler.internal;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.Utils;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;
import com.pega.pegarules.session.external.mgmt.IPRThread;
import com.pega.platform.executor.jobscheduler.internal.config.JobScheduleConfig;

@Weave
public abstract class ActivityProcessor {

	protected final IPRThread currentThread = Weaver.callOriginal();
	private final JobScheduleConfig jsConfig = Weaver.callOriginal();

	@Trace
	public void execute() {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		if(!transaction.isTransactionNameSet()) {
			
			if(jsConfig != null) {
				String activityName = jsConfig.getActivityName();
				if(activityName != null && !activityName.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, true, "PegaCore", "Activity",activityName);
				}
			}
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRThread(attributes, currentThread);
		Utils.addJobScheduleConfig(attributes, jsConfig);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","ActivityProcessor","execute");
		Weaver.callOriginal();
	}
	
	@SuppressWarnings("rawtypes")
	@Trace
	public Object run(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		if(!transaction.isTransactionNameSet()) {
			
			if(jsConfig != null) {
				String activityName = jsConfig.getActivityName();
				if(activityName != null && !activityName.isEmpty()) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "PegaCore", "Pega","Activity",activityName);
				}
			}
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, aRequestor);
		Utils.addIPRThread(attributes, currentThread);
		Utils.addJobScheduleConfig(attributes, jsConfig);
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","ActivityProcessor","run");
		return Weaver.callOriginal();
	}
}
