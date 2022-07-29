package com.pega.pegarules.integration.engine.internal.services.jms;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.JMSUtils;

import javax.jms.Message;

@Weave
public abstract class JMSListener {

	@Trace(dispatcher = true)
	private void handleRequest(Message aMessage) {
		if (aMessage != null) {
			JMSUtils.processConsume(aMessage, NewRelic.getAgent().getTracedMethod());
			JMSUtils.nameTransaction(aMessage);
		}
		Weaver.callOriginal();
	}
}
