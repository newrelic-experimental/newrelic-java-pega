package com.pega.apache.axis2.client;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis2.client.async.AxisCallback;
import com.pega.apache.axis2.context.OperationContext;


@Weave(type = MatchType.BaseClass)
public abstract class OperationClient {

    public abstract OperationContext getOperationContext();
    
    protected AxisCallback axisCallback = Weaver.callOriginal();
    
    @Trace
    public void executeImpl(boolean b) {
        OperationContext oc1 = getOperationContext();
        if (oc1 != null) {
        	String serviceName = oc1.getServiceName();
        	String operationName = oc1.getOperationName();
        	if(serviceName != null && !serviceName.isEmpty()) {
        		if(operationName != null && !operationName.isEmpty()) {
        			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Pega","Apache","OperationClient",serviceName,operationName});
        		} else {
        			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Pega","Apache","OperationClient",serviceName,"UnknownOperation"});
        		}
        	} else {
        		if(operationName != null && !operationName.isEmpty()) {
        			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Pega","Apache","OperationClient","UnknownService",operationName});
        		} else {
        			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Pega","Apache","OperationClient","UnknownService","UnknownOperation"});
        		}
        	}
        }
        if(axisCallback != null && axisCallback.token == null) {
        	axisCallback.token = NewRelic.getAgent().getTransaction().getToken();
        }
        Weaver.callOriginal();
    }

}
