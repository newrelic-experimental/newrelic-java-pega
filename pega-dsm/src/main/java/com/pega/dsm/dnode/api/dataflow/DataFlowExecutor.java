package com.pega.dsm.dnode.api.dataflow;

import java.util.List;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.dsm.dnode.api.stream.DataSubscriber;

@Weave(type = MatchType.Interface)
abstract class DataFlowExecutor {

	public void doOnInit(DataSubscriber<?> var1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","DataFlowExecutor",getClass().getSimpleName(),"doOnInit");
		Weaver.callOriginal();
	}

	public <T> void doOnNext(List<DataSubscriber<T>> var1, T var2) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","DataFlowExecutor",getClass().getSimpleName(),"doOnNext");
		Weaver.callOriginal();
	}

	public void doOnFlush() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","DataFlowExecutor",getClass().getSimpleName(),"doOnFlust");
		Weaver.callOriginal();
	}

	public void doOnError(DataSubscriber<?> var1, Throwable var2) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","DataFlowExecutor",getClass().getSimpleName(),"doOnError");
		Weaver.callOriginal();
	}

	public void doOnCompleted(DataSubscriber<?> var1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","DataFlowExecutor",getClass().getSimpleName(),"doOnCompleted");
		Weaver.callOriginal();
	}

}
