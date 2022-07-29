package com.pega.pegarules.session.internal.serverpush;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.session.external.serverpush.Subscriber;
import com.pega.pegarules.session.external.serverpush.SubscriptionData;
import com.pega.platform.message.broadcast.MessageInbound;

@Weave(type = MatchType.BaseClass)
public abstract class AbstractPushEngine {

	@Trace
	public void handleRequest(HttpServletRequest aRequest, HttpServletResponse aResponse) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractPushEngine",getClass().getSimpleName(),"handleRequest");
		Weaver.callOriginal();
	}

	@Trace
	public void handlePostRequest(HttpServletRequest aRequest, String aMessage) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractPushEngine",getClass().getSimpleName(),"handlePostRequest");
		Weaver.callOriginal();
	}

	@Trace
	public void handleRequestHook(HttpServletRequest p0, HttpServletResponse p1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractPushEngine",getClass().getSimpleName(),"handleRequestHook");
		Weaver.callOriginal();
	}

	@Trace
	public final void handleResponse(MessageInbound aMessage, SubscriptionData aSubscription) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractPushEngine",getClass().getSimpleName(),"handleResponse");
		Weaver.callOriginal();
	}
	
	@Trace
	protected void handleResponse(String p0, Subscriber p1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractPushEngine",getClass().getSimpleName(),"handleResponse");
		Weaver.callOriginal();
	}
	
	@Trace
	protected void sendMessage(PushClientIdentifier p0, String p1) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","AbstractPushEngine",getClass().getSimpleName(),"sendMessage");
		Weaver.callOriginal();
	}
}
