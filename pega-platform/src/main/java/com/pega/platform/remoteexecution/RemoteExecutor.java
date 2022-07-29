package com.pega.platform.remoteexecution;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.google.common.util.concurrent.ListenableFuture;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class RemoteExecutor {

	@Trace(dispatcher = true)
	public <P extends Serializable, R extends Serializable> Map<UUID, ListenableFuture<R>> execute(Task<P, R> var1, Collection<UUID> var2) {
		String taskClass = var1 != null ? var1.getClassName() : null;
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(taskClass != null && !taskClass.isEmpty()) {
			traced.addCustomAttribute("Pega-Task-Classname", taskClass);
		}
		traced.addCustomAttribute("Pega-RemoteExecutor-Class", getClass().getName());
		traced.setMetricName("Custom","RemoteExecutor",getClass().getSimpleName(),"execute");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <P extends Serializable, R extends Serializable> ResponseBucket<R> execute(Task<P, R> var1, ApplicabilityPredicate var2) {
		String taskClass = var1 != null ? var1.getClassName() : null;
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(taskClass != null && !taskClass.isEmpty()) {
			traced.addCustomAttribute("Pega-Task-Classname", taskClass);
		}
		traced.addCustomAttribute("Pega-RemoteExecutor-Class", getClass().getName());
		traced.setMetricName("Custom","RemoteExecutor",getClass().getSimpleName(),"execute");
		return Weaver.callOriginal();
	}

}
