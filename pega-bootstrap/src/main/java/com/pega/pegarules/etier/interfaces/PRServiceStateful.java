package com.pega.pegarules.etier.interfaces;

import java.util.ArrayList;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@SuppressWarnings("rawtypes")
@Weave(type=MatchType.Interface)
public abstract class PRServiceStateful {

	@Trace(dispatcher = true)
	public ArrayList invokeService(String var1, ArrayList var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PRServiceStateful",getClass().getName(),"invokeService",var1);
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public ArrayList invokeServiceTxnMand(String var1, ArrayList var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PRServiceStateful",getClass().getName(),"invokeService",var1);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public ArrayList invokeServiceTxnNever(String var1, ArrayList var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PRServiceStateful",getClass().getName(),"invokeService",var1);
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public ArrayList invokeServiceTxnNotSupported(String var1, ArrayList var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PRServiceStateful",getClass().getName(),"invokeService",var1);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public ArrayList invokeServiceTxnReq(String var1, ArrayList var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PRServiceStateful",getClass().getName(),"invokeService",var1);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public ArrayList invokeServiceTxnReqNew(String var1, ArrayList var2) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PRServiceStateful",getClass().getName(),"invokeService",var1);
		return Weaver.callOriginal();
	}
}
