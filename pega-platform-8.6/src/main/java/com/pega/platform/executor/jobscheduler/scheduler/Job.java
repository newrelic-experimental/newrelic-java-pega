package com.pega.platform.executor.jobscheduler.scheduler;

import java.util.HashMap;
import java.util.Optional;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform86.Utils;

@Weave(type=MatchType.Interface)
public abstract class Job {

	public abstract String getId();

	@Trace(dispatcher = true)
	public void execute() {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Job",getClass().getSimpleName(),"execute");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "ID", getId());
		Optional<String> requestor = getRequestorId();
		Utils.addOptional(attributes, "RequestorID", requestor);
		Weaver.callOriginal();
	}

	public abstract Optional<String> getRequestorId();

}
