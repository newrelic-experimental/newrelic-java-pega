package com.pega.pegarules.session.internal;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.session.Utils;
import com.pega.pegarules.exec.external.util.TenantIdentifier;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.util.StringMap;
import com.pega.pegarules.session.external.mgmt.EngineRunnable;
import com.pega.pegarules.session.external.mgmt.IPRNode;

@Weave
public abstract class PRSessionProviderImplForModules {

	@SuppressWarnings("rawtypes")
	@Trace
	public Object doWithRequestorLocked(Object var1, int var2, long var3, Object var5, Object var6, Object[] var7, Map var8,
			EngineRunnable var9, boolean var10, boolean var11, TenantIdentifier tenantId) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(tenantId != null) {
			String tenantName = tenantId.getTenantName();
			if(tenantName != null && !tenantName.isEmpty()) {
				traced.addCustomAttribute("Pega-Tenant", tenantName);
			}
		}
		traced.setMetricName("Custom","PRSessionProviderImplForModules",getClass().getSimpleName(),"doWithRequestorLocked");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRNode(attributes, getNodeSingleton());
		
		return Weaver.callOriginal();
	}
	
	@Trace
	public String sendFile(ClipboardPage var1, String var2, boolean var3, String var4, String var5, boolean var6,
			StringMap var7, boolean var8) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","PRSessionProviderImplForModules",getClass().getSimpleName(),"sendFile");
		return Weaver.callOriginal();
		
	}
	
	public abstract IPRNode getNodeSingleton();
}
