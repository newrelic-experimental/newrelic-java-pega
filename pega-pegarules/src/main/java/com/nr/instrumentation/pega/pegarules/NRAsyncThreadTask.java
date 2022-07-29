package com.nr.instrumentation.pega.pegarules;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.pega.pegarules.session.external.async.IAsyncThreadTask;

public class NRAsyncThreadTask implements IAsyncThreadTask {

	private IAsyncThreadTask delegate = null;
	private Token token = null;
	private String activityName = null;
	private static boolean isTransformed = false;

	public NRAsyncThreadTask(IAsyncThreadTask d,Token t,String aName) {
		delegate = d;
		token = t;
		activityName = aName;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(async = true)
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		if(activityName != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","IAsyncThreadTask","run",activityName);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","IAsyncThreadTask","run");
		}
		delegate.run();
	}

	@Override
	public void completeTask() {
		if(token != null) {
			token.expire();
			token = null;
		}
			delegate.completeTask();
	}

	@Override
	public String getId() {
		return delegate.getId();
	}

	@Override
	public String getStatus() {
		return delegate.getStatus();
	}

	@Override
	public boolean hasFinished() {
		return delegate.hasFinished();
	}

	@Override
	public boolean hasStarted() {
		return delegate.hasStarted();
	}

	@Override
	public boolean preventStart() {
		return delegate.preventStart();
	}

}
