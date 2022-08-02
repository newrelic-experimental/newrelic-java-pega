package com.pega.apache.commons.httpclient;

import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.httpclient.PegaHttpHeaders;

@Weave
public abstract class HttpClient {

	@Trace(leaf = true)
	public int executeMethod(HostConfiguration hostconfig, HttpMethod method, HttpState state) {
		PegaHttpHeaders headers = new PegaHttpHeaders(method);
		Transaction transaction = NewRelic.getAgent().getTransaction();
		transaction.insertDistributedTraceHeaders(headers);
			String protocol = hostconfig.getProtocol().getScheme();
			String host = hostconfig.getHost();
			int port = hostconfig.getPort();
			String path = method.getPath();
			String uriString;
			if(port > 0) {
				uriString = protocol + "://"+host+":"+port+"/"+path;
			} else {
				uriString = protocol + "://"+host+"/"+path;
			}
			java.net.URI uri = java.net.URI.create(uriString);
			HttpParameters params = HttpParameters.library("Pega-HttpCommons").uri(uri).procedure(method.getName()).extendedInboundHeaders(null).build();
			NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		
		int result =  Weaver.callOriginal();
		transaction.acceptDistributedTraceHeaders(TransportType.HTTP, headers);
		return result;
	}

}
