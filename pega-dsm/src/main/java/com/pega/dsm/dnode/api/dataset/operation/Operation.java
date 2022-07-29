package com.pega.dsm.dnode.api.dataset.operation;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.pega.dsm.Utils;
import com.pega.dsm.dnode.api.stream.DataObservable;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.PublicAPI;

@Weave(type=MatchType.BaseClass)
public abstract class Operation<T> {

	public abstract String getName();
	
	@Trace(dispatcher = true)
	public DataObservable<T> execute(PublicAPI var1, ClipboardPage var2, ClipboardPage var3) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","DSM","Operation",getClass().getSimpleName(),getName(),"execute");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, var2);
		return Weaver.callOriginal();
	}
}
