package com.pega.fnx.stream.spi.impl.processor;

import java.util.concurrent.CompletableFuture;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.pege.requestprocessor.GetCompletionBiConsumer;
import com.pega.fnx.stream.spi.request.GetMessageRequest;
import com.pega.fnx.stream.spi.response.GetMessageResponse;

@Weave
public abstract class GetMessageRequestProcessor {

	public CompletableFuture<GetMessageResponse> execute(GetMessageRequest request) {
		CompletableFuture<GetMessageResponse> result = Weaver.callOriginal();
		
		return result.whenComplete(new GetCompletionBiConsumer("GetMessageRequestProcessor/execute"));
	}
}
