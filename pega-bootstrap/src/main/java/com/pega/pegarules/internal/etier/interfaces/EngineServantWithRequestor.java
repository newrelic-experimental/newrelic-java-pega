package com.pega.pegarules.internal.etier.interfaces;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class EngineServantWithRequestor {

	@SuppressWarnings("rawtypes")
	@Trace
	public Object[] invoke(Object[] var1, Map var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","EngineServantWithRequestor",getClass().getSimpleName(),"invoke");
		return Weaver.callOriginal();
	}
}
