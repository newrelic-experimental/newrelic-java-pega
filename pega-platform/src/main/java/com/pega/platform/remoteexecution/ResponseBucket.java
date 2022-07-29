package com.pega.platform.remoteexecution;

import java.io.Serializable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class ResponseBucket<R extends Serializable> {

	@Trace(dispatcher = true)
	public R get() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","ResponseBucket",getClass().getSimpleName(),"get");
		return Weaver.callOriginal();
	}
}
