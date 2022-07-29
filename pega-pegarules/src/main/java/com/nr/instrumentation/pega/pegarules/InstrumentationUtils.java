package com.nr.instrumentation.pega.pegarules;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.newrelic.agent.bridge.AgentBridge;
import com.pega.pegarules.pub.runtime.AbstractActivity;

public class InstrumentationUtils {

	
	private static List<String> instrumentation = new ArrayList<String>();
	
	public static void instrumentActivity(AbstractActivity activity) {
		Class<?> clazz = activity.getClass();
		if(!instrumentation.contains(clazz.getName())) {
			Method[] methods = clazz.getDeclaredMethods();
			for(Method method : methods) {
				String methodName = method.getName();
				if(methodName.startsWith("step") && methodName.contains("circum")) {
					AgentBridge.instrumentation.instrument(method, "ActivityStep");
				}
			}
			instrumentation.add(clazz.getName());
		}
	}
}