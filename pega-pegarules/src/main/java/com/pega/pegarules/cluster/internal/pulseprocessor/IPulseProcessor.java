package com.pega.pegarules.cluster.internal.pulseprocessor;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.context.PRPulseMessage;
import com.pega.pegarules.session.external.mgmt.IExecutable;

@Weave(type = MatchType.Interface)
public abstract class IPulseProcessor {

	@Trace
	public void execute(IExecutable var1) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","IPulseProcessor",getClass().getSimpleName(),"execute");
		String pulseType = getPulseType();
		if(pulseType != null) {
			traced.addCustomAttribute("Pega-PulseType", pulseType);
		}
		traced.addCustomAttribute("Pega-PulseProcessorClass", getClass().toString());
		Weaver.callOriginal();
	}
	
	public void preprocessMessage(PRPulseMessage var1, IExecutable var2) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","IPulseProcessor",getClass().getSimpleName(),"preprocessMessage");
		Weaver.callOriginal();
	}

	public abstract String getPulseType();

}
