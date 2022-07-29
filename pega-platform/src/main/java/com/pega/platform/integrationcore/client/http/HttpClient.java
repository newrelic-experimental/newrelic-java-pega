package com.pega.platform.integrationcore.client.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;

import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.PegaInboundHttp;
import com.nr.instrumentation.pega.platform.PegaOutboundHttp;

@Weave(type = MatchType.Interface)
public abstract class HttpClient {

	@Trace(leaf = true)
	public HttpResponse invokeHttpClient(HttpRequest request) {
		
		URL url = request.getRequestURL();
		
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Request URL", url.toString());
		PegaOutboundHttp outbound = new PegaOutboundHttp(request);
		traced.addOutboundRequestHeaders(outbound);
		HttpResponse response = Weaver.callOriginal();
		try {
			PegaInboundHttp wrapper = new PegaInboundHttp(response);
			URI uri = url.toURI();
			HttpParameters params = HttpParameters.library("Pega").uri(uri).procedure("invokeHttpClient").inboundHeaders(wrapper).build();
			traced.reportAsExternal(params);
		} catch (URISyntaxException e) {
			NewRelic.getAgent().getLogger().log(Level.FINER, e, "Failed to get URI");
		}
		return response;
	}
	
}
