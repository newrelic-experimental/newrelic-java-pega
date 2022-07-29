package com.pega.platform.executor.queueprocessor.internal;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.EnqueueOperationConfig;
import com.pega.pegarules.pub.runtime.PublicAPI;
import com.pega.pegarules.session.external.mgmt.IPRRequestorForModules;

@Weave(type = MatchType.BaseClass)
public abstract class AbstractQueueProcessor {
	
	String queueName = Weaver.callOriginal();
	
	public abstract IPRRequestorForModules getRequestor();

	@Trace
	String enqueue(String activityName, ClipboardPage aMessagePage, EnqueueOperationConfig config, String partitionKey) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, aMessagePage);
		Utils.addAttribute(attributes, "Activity", activityName);
		Utils.addAttribute(attributes, "PartitionKey", partitionKey);
		Utils.addAttribute(attributes, "QueueName", queueName);
		Utils.addIPRRequestor(attributes, getRequestor());
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","AbstractQueueProcessor",getClass().getSimpleName(),"enqueue");
		return Weaver.callOriginal();
	}
	
	@Trace
	protected void enqueue(String queueName, String partitionKey, PublicAPI publicAPI, EnqueueOperationConfig config, ClipboardPage aMessagePage) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, aMessagePage);
		Utils.addAttribute(attributes, "QueueName", queueName);
		Utils.addAttribute(attributes, "PartitionKey", partitionKey);
		Utils.addAttribute(attributes, "QueueName", queueName);
		Utils.addIPRRequestor(attributes, getRequestor());
		traced.addCustomAttributes(attributes);
		traced.setMetricName("Custom","AbstractQueueProcessor",getClass().getSimpleName(),"enqueue");
		Weaver.callOriginal();
	}
}
