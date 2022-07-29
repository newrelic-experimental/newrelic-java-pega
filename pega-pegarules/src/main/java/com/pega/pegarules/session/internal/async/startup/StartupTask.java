package com.pega.pegarules.session.internal.async.startup;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.external.async.IStartupTask.ACTION;

@Weave(type = MatchType.BaseClass)
public abstract class StartupTask {

	@Trace(dispatcher = true)
	public void run() {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "StartupTask", getType().name());
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Pega-Action", getType().name());
		traced.addCustomAttribute("Pega-StartupTask-Class", getClass().getName());
		traced.setMetricName("Custom","StartupTask",getClass().getSimpleName(),"run");
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void runTask() {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Pega-Action", getType().name());
		traced.addCustomAttribute("Pega-StartupTask-Class", getClass().getName());
		traced.setMetricName("Custom","StartupTask",getClass().getSimpleName(),"runTAsk");
		Weaver.callOriginal();
	}
	
	public abstract ACTION getType();
}
