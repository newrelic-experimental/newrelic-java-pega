package com.pega.pegarules.integration.config.internal.connect;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;

@Weave(type=MatchType.Interface)
public abstract class Connector {

	@Trace
	public ClipboardPage invoke(ClipboardPage var1, Map<String, Object> var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, var1);
		traced.setMetricName("Custom","Connector",getClass().getSimpleName(),"invoke");
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
