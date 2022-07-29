package com.pega.pegarules.internal.bootstrap;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.pega.bootstrap.Utils;
import com.pega.pegarules.boot.internal.extbridge.IAppServerBridge;

@Weave
public abstract class PRBootstrap implements IAppServerBridge {
	
	private static final synchronized boolean checkForStartup(Object[][] aDescriptor, Object[] aArgs) {
		return Weaver.callOriginal();
	}
	
	private static final Method getMethod(Object[][] aDescriptor, int aMethodIndex) {
		return Weaver.callOriginal();
	}

	public Object invokeMethodPropagatingThrowable(Object[][] var1, int var2, Object var3, Object[] var4) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("PRBootstrap/invokeMethodPropagatingThrowable");
		if(checkForStartup(var1, var4)) {
			Method method = getMethod(var1, var2);
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addAttribute(attributes,"ClassName", method.getDeclaringClass().getName());
			Utils.addAttribute(attributes,"Method", method.getName());
			segment.addCustomAttributes(attributes);
		}
		long startTime = System.currentTimeMillis();
		Object result = Weaver.callOriginal();
		long endTime = System.currentTimeMillis();
		if(endTime - startTime >= Utils.TRACE_THRESHOLD) {
			segment.end();
		} else {
			segment.ignore();
		}
		return result;
	}
}