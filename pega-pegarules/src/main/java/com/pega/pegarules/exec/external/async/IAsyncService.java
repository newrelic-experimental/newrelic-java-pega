package com.pega.pegarules.exec.external.async;

import com.newrelic.api.agent.NewRelic;
//import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.external.async.IBatchRequestorTask;
//import com.pega.pegarules.session.internal.async.BatchRequestorTask;

@Weave(type = MatchType.Interface)
public abstract class IAsyncService {

	@Trace
	public IBatchRequestorTask onServiceAddedToQueue(ExecutionState aExecState, AsyncServiceInputData aInputData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aInputData != null) {
			serviceName = aInputData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncService",getClass().getSimpleName(),"onServiceAddedToQueue",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncService",getClass().getSimpleName(),"onServiceAddedToQueue","UnknownService");
		}

		IBatchRequestorTask task = Weaver.callOriginal();
//		if(task instanceof BatchRequestorTask)  {
//			BatchRequestorTask batchTask = (BatchRequestorTask)task;
//			if(batchTask.token == null) {
//				Token t = NewRelic.getAgent().getTransaction().getToken();
//				if(t != null && t.isActive()) {
//					batchTask.token = t;
//				} else if(t != null) {
//					t.expire();
//					t = null;
//				}
//			}
//		}
		
		return task;
	}
	
	@Trace
	public boolean onServiceExecutionFinish(ExecutionState aExecState, AsyncServiceInputData aInputData, AsyncServiceResultData aResultData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aInputData != null) {
			serviceName = aInputData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncService",getClass().getSimpleName(),"onServiceExecutionFinish",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncService",getClass().getSimpleName(),"onServiceExecutionFinish","UnknownService");
		}
		return Weaver.callOriginal();
	}
	
	@Trace
	public void onServiceExecutionStart(ExecutionState aExecState, AsyncServiceInputData aInputData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aInputData != null) {
			serviceName = aInputData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncService",getClass().getSimpleName(),"onServiceExecutionStart",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncService",getClass().getSimpleName(),"onServiceExecutionStart","UnknownService");
		}
		Weaver.callOriginal();
	}
}
