package com.pega.platform.executionengine.vtable.internal;

import java.util.List;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.pub.util.StringMap;
import com.pega.platform.executionengine.runtime.Aspect;
import com.pega.platform.executionengine.runtime.RuleDispatcher.StackBehavior;
import com.pega.platform.executionengine.runtime.RuleHandle;
import com.pega.platform.executionengine.runtime.Runtime;

@SuppressWarnings("deprecation")
@Weave
public abstract class RuleDispatcherImpl {

	@Trace
	public void executeRuleInSandbox(RuleHandle ruleHandle, ClipboardPage stepPage, ParameterPage params, Aspect aspect,
			List<String> pagesToBeMadeAvailable) {
		Weaver.callOriginal();
	}
	
	@Trace
	public <T> T invoke(Runtime runtime, RuleHandle handle, ClipboardPage stepPage, ParameterPage params, Aspect aspect,
			StackBehavior stack, String forcedAppliesTo, boolean ignoreCircumstancing) {
		
		return Weaver.callOriginal();
	}
	
	@Trace
	public void invokeExpression(Runtime runtime, RuleHandle handle, ClipboardPage stepPage, ParameterPage params,
			Aspect aspect, StackBehavior stack, String appliesToClass, Object scalar) {
		Weaver.callOriginal();
	}
	
	@Trace
	public void invokeModel(Runtime runtime, RuleHandle handle, StringMap aKeys, ClipboardPage stepPage,
			ParameterPage params, Aspect aspect, StackBehavior stack) {
		Weaver.callOriginal();
	}
}
