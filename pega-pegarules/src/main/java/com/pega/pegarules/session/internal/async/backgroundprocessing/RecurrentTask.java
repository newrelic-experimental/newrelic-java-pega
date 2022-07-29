package com.pega.pegarules.session.internal.async.backgroundprocessing;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.BaseClass)
public abstract class RecurrentTask<T> {

	@Trace
	protected void execute(T var1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","RecurrentTask",getClass().getSimpleName(),"execute");
		Weaver.callOriginal();
	}
}
