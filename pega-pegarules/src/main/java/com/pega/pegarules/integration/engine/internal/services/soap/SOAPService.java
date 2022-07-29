package com.pega.pegarules.integration.engine.internal.services.soap;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.HttpAPIHeaders;

@Weave(type = MatchType.BaseClass)
public abstract class SOAPService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public Object[] invoke(Object[] aInput, Map aInfo) {
		Map requestMap = (Map) aInput[0];
		if (requestMap != null) {
			Map<String, Object> headerMap = (Map) requestMap.get("headers");
			HttpAPIHeaders apiHeaders = new HttpAPIHeaders(headerMap);
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.HTTP, apiHeaders);
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","SOAPService",getClass().getSimpleName(),"invoke");
		return Weaver.callOriginal();
	}
}
