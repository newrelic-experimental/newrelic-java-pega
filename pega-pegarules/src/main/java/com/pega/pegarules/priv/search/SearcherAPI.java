package com.pega.pegarules.priv.search;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;

@Weave(type = MatchType.Interface)
public abstract class SearcherAPI {

	@Trace
	public void search(ClipboardPage var1) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","SearcherAPI",getClass().getSimpleName(),"search");
		traced.addCustomAttribute("Pega-SearcherAPI-Class", getClass().getName());
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addClipboardPage(attributes, var1);
		traced.addCustomAttributes(attributes);
		Weaver.callOriginal();

	}
}
