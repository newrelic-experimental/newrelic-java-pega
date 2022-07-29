package com.pega.pegarules.management.internal.system.operations.jobs;

import java.io.Serializable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.management.internal.system.operations.jobinput.SystemOperationsJobInput;

@Weave(type = MatchType.BaseClass)
public abstract class AbstractSystemOperationsJob<T extends SystemOperationsJobInput, R extends Serializable> {

	@Trace
	public R execute(T aJobInput) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractSystemOperationsJob",getClass().getSimpleName(),"execute");
		return Weaver.callOriginal();
	}
	
	@Trace
	public R invokeOperation(T var1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractSystemOperationsJob",getClass().getSimpleName(),"execute");
		return Weaver.callOriginal();
	}
}
