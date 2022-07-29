package com.pega.platform.pegasearch.searcher;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.PRThread;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.platform.search.searcher.Searcher.QueryExecutionObserver;

@Weave(type=MatchType.Interface)
public abstract class PegaSearcher {

	@Trace(dispatcher = true)
	public ClipboardPage executeResolved(PegaSearcherRequest var1, QueryExecutionObserver var2, ParameterPage var3,PRThread var4) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PegaSearcher",getClass().getSimpleName(),"executeResolved");
		return Weaver.callOriginal();
	}
}
