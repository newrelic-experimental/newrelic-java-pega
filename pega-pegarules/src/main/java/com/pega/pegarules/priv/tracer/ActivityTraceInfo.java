package com.pega.pegarules.priv.tracer;

import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;

@SuppressWarnings("serial")
@Weave
public abstract class ActivityTraceInfo extends RuleTraceInfo {
	
	@NewField
	public Segment segment = null;
	
	@NewField
	public Long startTime;

	
	public ActivityTraceInfo(String aInsKey, String aKeyName, String aRuleSetName, String aRuleSetVersion,
			boolean aMayStart, boolean aAuthenticatedOnly, String aRolesAndPrivileges, String aActivityType,
			String aUpdateDateTime) {
		this(aInsKey, aKeyName, aRuleSetName, aRuleSetVersion, aMayStart, aAuthenticatedOnly, aRolesAndPrivileges,
				aActivityType, aUpdateDateTime, (String) null);
	}
	
	public ActivityTraceInfo(String aInsKey, String aKeyName, String aRuleSetName, String aRuleSetVersion,
			boolean aMayStart, boolean aAuthenticatedOnly, String aRolesAndPrivileges, String aActivityType,
			String aUpdateDateTime, String aRuleType) {
		super(aInsKey, aKeyName, aRuleSetName, aRuleSetVersion, aUpdateDateTime);
	}

	public abstract String getActivityType();
	public abstract String getRuleType();
}
