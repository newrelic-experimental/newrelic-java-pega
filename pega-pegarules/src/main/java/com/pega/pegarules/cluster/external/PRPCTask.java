package com.pega.pegarules.cluster.external;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@Weave
public abstract class PRPCTask<P extends Serializable, R extends Serializable> {

	
	private final String userId = Weaver.callOriginal();
	private final String accessGroup = Weaver.callOriginal();
	private final String mTenantName = Weaver.callOriginal();

	public abstract String getClassName();
	
	@Trace
	public R call() {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "UserID", userId);
		Utils.addAttribute(attributes, "AccessGroup", accessGroup);
		Utils.addAttribute(attributes, "TenantName", mTenantName);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	
	@SuppressWarnings("rawtypes")
	@Trace
	public Object run(final IPRRequestor aRequestor, final Object aBase1, final Object aBase2, final Object[] aInputs,
			final Map aInfo) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "UserID", userId);
		Utils.addAttribute(attributes, "AccessGroup", accessGroup);
		Utils.addAttribute(attributes, "TenantName", mTenantName);
		traced.addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
}
