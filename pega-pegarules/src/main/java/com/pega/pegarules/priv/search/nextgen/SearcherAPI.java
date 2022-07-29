package com.pega.pegarules.priv.search.nextgen;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.PRThread;
import com.pega.pegarules.pub.runtime.ParameterPage;

@Weave(type = MatchType.Interface)
public abstract class SearcherAPI {

	@Trace
	public ClipboardPage search(ClipboardPage var1, ParameterPage var2, PRThread var3) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","SearcherAPI",getClass().getSimpleName(),"search");
		traced.addCustomAttribute("Pega-SearcherAPI-Class", getClass().getName());
		return Weaver.callOriginal();
	}
}
