package com.pega.pegarules.internal.etier.interfaces;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class EngineBI {

	@Trace
	public Object[] invokeEngine(String var1, Object[] var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","EngineBI",getClass().getSimpleName(),"invokeEngine",var1);
		return Weaver.callOriginal();
	}
}
