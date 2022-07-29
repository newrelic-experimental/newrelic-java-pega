package com.pega.pegarules.integration.engine.internal.services;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.BaseClass)
abstract class ServiceHandler {

	@Trace
	public void processRequest() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","ServiceHandler",getClass().getSimpleName(),"processRequest");
		Weaver.callOriginal();
	}
}
