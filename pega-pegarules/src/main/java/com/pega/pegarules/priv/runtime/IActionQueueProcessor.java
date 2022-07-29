package com.pega.pegarules.priv.runtime;

import java.util.HashMap;
import java.util.Iterator;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.monitor.external.database.IAction;

@Weave(type = MatchType.Interface)
public abstract class IActionQueueProcessor {

	@Trace(dispatcher = true)
	public void process(IDataSyncContext var1, Iterator<IAction> var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","IActionQueueProcessor",getClass().getSimpleName(),"process");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIDataSyncContext(attributes, var1);
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
