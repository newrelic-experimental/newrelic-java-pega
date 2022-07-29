package com.pega.fnx.stream.spi.loader;

import java.util.concurrent.CompletableFuture;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.fnx.stream.spi.Request;
import com.pega.fnx.stream.spi.Response;
import com.pega.fnx.stream.spi.model.Operation;

@Weave
public abstract class SPIProxy {

	@Trace(dispatcher = true)
	public <T extends Response> CompletableFuture<T> execute(Request<T> request) {
		Operation operation = request.operation();
		if(operation != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","SPIProxy","execute",operation.name());
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "SPI", "Stream",operation.name());
		}
		return Weaver.callOriginal();
	}
}
