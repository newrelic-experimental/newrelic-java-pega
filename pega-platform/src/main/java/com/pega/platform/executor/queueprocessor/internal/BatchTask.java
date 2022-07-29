package com.pega.platform.executor.queueprocessor.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.runtime.PublicAPI;
import com.pega.pegarules.session.external.PRSessionProviderForModules;

@Weave(type = MatchType.BaseClass)
public abstract class BatchTask {

	@NewField
	protected Token token = null;
	
	public BatchTask(PRSessionProviderForModules sessionProvider) {
		
	}
	
	@Trace(async = true)
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BatchTask",getClass().getSimpleName(),"run");
		Weaver.callOriginal();
	}
	
	void after(Throwable exception) {
		if(exception != null) {
			NewRelic.noticeError(exception);
		}
		Weaver.callOriginal();
	}
	
	@Trace
	public void execute(PublicAPI var1)  {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BatchTask",getClass().getSimpleName(),"execute");
		Weaver.callOriginal();
	}
	
	@Trace
	public void executeInBorrowedRequestor() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BatchTask",getClass().getSimpleName(),"executeInBorrowedRequestor");
		Weaver.callOriginal();
	}
}
