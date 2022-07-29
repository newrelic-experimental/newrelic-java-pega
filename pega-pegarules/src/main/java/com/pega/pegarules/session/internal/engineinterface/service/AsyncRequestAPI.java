package com.pega.pegarules.session.internal.engineinterface.service;

import java.util.Map;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class AsyncRequestAPI {

	@SuppressWarnings("rawtypes")
	@Trace
	public Object[] invoke(Object[] aInput, Map aInfo) {
		return Weaver.callOriginal();
	}
	
	@SuppressWarnings("rawtypes")
	@Trace
	public Map processRequest() {
		return Weaver.callOriginal();
	}
}
