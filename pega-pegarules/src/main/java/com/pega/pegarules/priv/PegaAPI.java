package com.pega.pegarules.priv;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.PegaConfiguration;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.priv.tracer.ActivityTraceInfo;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.pub.util.StringMap;

@SuppressWarnings("rawtypes")
@Weave(type=MatchType.Interface)
public class PegaAPI {

	@Trace
	public boolean dispatchToGeneratedMethodInRule(Object var1, Map var2, Map var3) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","PegaAPI",getClass().getSimpleName(),"dispatchToGeneratedMethodInRule");
		return Weaver.callOriginal();
	}
	
	public void doAction(StringMap aKeys, ClipboardPage aNewStepPage, ParameterPage aNewParam, boolean ignoreCircumstance) {
		String activityName = aKeys.getString("pyActivityName");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Segment segment = null;
		
		if(activityName != null && !activityName.isEmpty()) {
			segment = NewRelic.getAgent().getTransaction().startSegment("PegaAPI/"+getClass().getSimpleName()+"/doAction/"+activityName);
		} else {
			segment = NewRelic.getAgent().getTransaction().startSegment("PegaAPI/"+getClass().getSimpleName()+"/doAction");
		}
		Utils.addClipboardPage(attributes, aNewStepPage);
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD || (activityName != null && PegaConfiguration.includeActivity(activityName))) {
			Utils.addStringMap(attributes, aKeys);
			Utils.addParameterPage(attributes, aNewParam);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
	}
	
	public boolean invokeWhen(String aBlockName, String aAssemblyTimeClass, String aAssemblyTimeWhenClass)  {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Custom/PegaAPI"+getClass().getSimpleName()+"/invokeWhen");
		long start = System.currentTimeMillis();
		boolean result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes, "BlockName", aBlockName);
			Utils.addAttribute(attributes, "AssemblyTimeClass", aAssemblyTimeClass);
			Utils.addAttribute(attributes, "AssemblyTimeWhenClass", aAssemblyTimeWhenClass);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
		return result;
	}

	
	public void invokeActivity(ClipboardPage aStepPage, ParameterPage aParamPage, String aName, String aAssemblyTimeClass, String aExplicitClass) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("PegaAPI/"+getClass().getSimpleName()+"/invokeActivity/"+aName);
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD || PegaConfiguration.includeActivity(aName)) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addClipboardPage(attributes, aStepPage);
			Utils.addAttribute(attributes, "pyActivityName", aName);
			String clazzName = aExplicitClass == null ? "" : aExplicitClass;
			if(clazzName.length() == 0 && aStepPage != null) {
				clazzName = aStepPage.getClassName();
			}
			if(clazzName == null || clazzName.isEmpty()) {
				clazzName = aAssemblyTimeClass;
			}
			Utils.addAttribute(attributes, "pyClassName", clazzName);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}
	}

/**
 * Moved to separate extension (pega-rules-steps) in order to disable tracing steps if desired
 */

	public void activityStepProlog(StringMap aLocalVars, ActivityTraceInfo aRuleInUse, String aStep, String aStepMethod) {

		if (PegaConfiguration.PEGA_STEP_ENABLED) {
			if(aRuleInUse.segment == null) {
				Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/Step");
				HashMap<String, Object> attributes = new HashMap<String, Object>();
				Utils.addAttribute(attributes, "Step", aStep);
				Utils.addAttribute(attributes, "StepMethod", aStepMethod);
				Utils.addActivityTraceInfo(attributes, aRuleInUse);
				segment.addCustomAttributes(attributes);
				aRuleInUse.segment = segment;
				aRuleInUse.startTime = System.currentTimeMillis();
			}
		}
		Weaver.callOriginal();
	}

	public void activityStepEpilog(StringMap aLocalVars, ActivityTraceInfo aRuleInUse, String aStep, String aStepMethod, boolean attempted) {
		if (PegaConfiguration.PEGA_STEP_ENABLED) {
			Segment segment = aRuleInUse.segment;
			if (segment != null) {
				if (!attempted) {
					segment.ignore();
				} else {
					Long start = aRuleInUse.startTime;
					if (start != null) {
						long end = System.currentTimeMillis();
						if (end - start >= PegaConfiguration.PEGA_STEP_THRESHOLD
								|| PegaConfiguration.includeStep(aStepMethod)) {
							segment.end();
						} else {
							segment.ignore();
						}
					} else {
						segment.ignore();
					}
				}
			} 
		}
		Weaver.callOriginal();
	}

}
