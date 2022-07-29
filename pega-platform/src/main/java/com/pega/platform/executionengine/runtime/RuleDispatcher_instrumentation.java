package com.pega.platform.executionengine.runtime;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.pub.util.StringMap;
import com.pega.platform.executionengine.runtime.RuleDispatcher.StackBehavior;

@SuppressWarnings("deprecation")
@Weave(type = MatchType.Interface,originalName = "com.pega.platform.executionengine.runtime.RuleDispatcher")
public abstract class RuleDispatcher_instrumentation {

	@Trace
	public <T> T invoke(Runtime var1, RuleHandle var2, ClipboardPage var3, ParameterPage var4, Aspect var5, StackBehavior var6) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","RuleDispatcher",getClass().getSimpleName(),"invoke");
		traced.addCustomAttribute("Pega-RuleDispatcher-Class", getClass().getName());
		return Weaver.callOriginal();
	}

	@Trace
	public void invokeExpression(Runtime var1, RuleHandle var2, ClipboardPage var3, ParameterPage var4, Aspect var5, StackBehavior var6, String var7, Object var8) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","RuleDispatcher",getClass().getSimpleName(),"invokeExpression");
		traced.addCustomAttribute("Pega-RuleDispatcher-Class", getClass().getName());
		Weaver.callOriginal();
	}

	@Trace
	public void invokeModel(Runtime var1, RuleHandle var2, StringMap var3, ClipboardPage var4, ParameterPage var5, Aspect var6, StackBehavior var7) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","RuleDispatcher",getClass().getSimpleName(),"invokeModel");
		traced.addCustomAttribute("Pega-RuleDispatcher-Class", getClass().getName());
		Weaver.callOriginal();
		
	}

	@Trace
	public <T> T invoke(Runtime var1, RuleHandle var2, ClipboardPage var3, ParameterPage var4, Aspect var5, StackBehavior var6, boolean var7) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","RuleDispatcher",getClass().getSimpleName(),"invokeExpression");
		traced.addCustomAttribute("Pega-RuleDispatcher-Class", getClass().getName());
		return Weaver.callOriginal();
	}

}
