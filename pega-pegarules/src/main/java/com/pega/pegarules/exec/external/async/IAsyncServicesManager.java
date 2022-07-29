package com.pega.pegarules.exec.external.async;

import java.util.HashMap;
import java.util.Set;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.nonblockingui.ServiceRequestBundle;

@Weave(type = MatchType.Interface)
public abstract class IAsyncServicesManager {

	@Trace(dispatcher = true)
	public boolean onServiceAddedToQueue(AsyncServiceInputData aInputData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aInputData != null) {
			serviceName = aInputData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceAddedToQueue",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceAddedToQueue","UnknownService ");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAsyncInputData(attributes, aInputData);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public boolean onServiceExecutionFinish(AsyncServiceInputData aInputData, AsyncServiceResultData aResultData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aInputData != null) {
			serviceName = aInputData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceExecutionFinish",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceExecutionFinish","UnknownService ");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAsyncInputData(attributes, aInputData);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public ExecutionStateData onServiceExecutionStart(AsyncServiceInputData aInputData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aInputData != null) {
			serviceName = aInputData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceExecutionStart",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceExecutionStart","UnknownService ");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAsyncInputData(attributes, aInputData);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public boolean onServiceMessageFromCluster(AsyncServiceInputData aInputData, String aMessage) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aInputData != null) {
			serviceName = aInputData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceMessageFromCluster",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceMessageFromCluster","UnknownService ");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAsyncInputData(attributes, aInputData);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public ExecutionStateData registerAndWaitForResult(AsyncServiceInputData aServiceSpecificData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aServiceSpecificData != null) {
			serviceName = aServiceSpecificData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceMessageFromCluster",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncServicesManager","onServiceMessageFromCluster","UnknownService ");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAsyncInputData(attributes, aServiceSpecificData);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public String registerAndWaitForUpdateOnServices(Set<String> aCacheKeys, long timeout) {
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void registerForServicesAndWaitForUpdate(ServiceRequestBundle srBundle) {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public ExecutionStateData registerWithNotificationHandler(AsyncServiceInputData aServiceSpecificData,
			NotificationHandler aHandler) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aServiceSpecificData != null) {
			serviceName = aServiceSpecificData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncServicesManager","registerWithNotificationHandler",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncServicesManager","registerWithNotificationHandler","UnknownService ");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAsyncInputData(attributes, aServiceSpecificData);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public ExecutionStateData registerWithoutWait(AsyncServiceInputData aServiceSpecificData) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String serviceName = null;
		if(aServiceSpecificData != null) {
			serviceName = aServiceSpecificData.getServiceName();
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			traced.setMetricName("Custom","AsyncServicesManager","registerWithoutWait",serviceName);
		} else {
			traced.setMetricName("Custom","AsyncServicesManager","registerWithoutWait","UnknownService ");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAsyncInputData(attributes, aServiceSpecificData);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
