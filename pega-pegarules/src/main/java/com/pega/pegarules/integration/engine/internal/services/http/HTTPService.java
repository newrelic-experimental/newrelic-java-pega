package com.pega.pegarules.integration.engine.internal.services.http;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.HttpAPIHeaders;
import com.nr.instrumentation.pega.pegarules.HttpUtils;
import com.pega.platform.web.request.internal.HttpRequestMapper;

@Weave
public abstract class HTTPService {


	private static final String REMOTE_ADDRESS = Weaver.callOriginal();
	private static final String METHOD = Weaver.callOriginal();
	private static final String QUERY_STRING = Weaver.callOriginal();
	
	private HttpRequestMapper mRequestMapper = Weaver.callOriginal();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Trace
	public Object[] invoke(Object[] aInput, Map aInfo) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","HTTPService","invoke");
		Transaction transaction = NewRelic.getAgent().getTransaction();
		Map requestMap = (Map) aInput[0];
		if(requestMap !=  null && !requestMap.isEmpty()) {
			HttpUtils.processInbound(requestMap,mRequestMapper,traced,transaction,REMOTE_ADDRESS,QUERY_STRING,METHOD);
			Map<String, Object> headerMap = (Map) requestMap.get("headers");
			HttpAPIHeaders apiHeaders = new HttpAPIHeaders(headerMap);
			NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.HTTP, apiHeaders);
		}

		return Weaver.callOriginal();
	}

}
