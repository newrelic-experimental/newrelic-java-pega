package com.pega.pegarules.session.external.async;

import java.lang.reflect.Method;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.internal.etier.AsyncResource;

@Weave
public abstract class AbstractDaemon {

	public abstract String getDaemonName();
	protected abstract AsyncResource getPPRAResourceName();
	protected abstract Method getExecutionMethod();
	
	@Trace(dispatcher = true)
	private void performProcessing() {
		String daemonName = getDaemonName();
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(daemonName != null && !daemonName.isEmpty()) {
			traced.setMetricName("Custom","AbstractDaemon",getDaemonName(),"performProcessing");
		}
		Method eMethod = getExecutionMethod();
		if(eMethod != null) {
			traced.addCustomAttribute("Pega-ExecutionMethod", eMethod.getName());
			traced.addCustomAttribute("Pega-ExecutionMethod-Class", eMethod.getDeclaringClass().toString());
		}
		Weaver.callOriginal();
	}
}
