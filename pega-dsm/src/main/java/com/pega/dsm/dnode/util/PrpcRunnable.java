package com.pega.dsm.dnode.util;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.runtime.PublicAPI;

@Weave(type = MatchType.BaseClass)
public abstract class PrpcRunnable<T> {
	
	@NewField
	public Token token = null;
	
	protected PrpcRunnable(String userId, String accessGroup) {
	}

	protected PrpcRunnable() {
	}
	
	@Trace(async = true)
	public T run(PublicAPI var1) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		return Weaver.callOriginal();
	}
}
