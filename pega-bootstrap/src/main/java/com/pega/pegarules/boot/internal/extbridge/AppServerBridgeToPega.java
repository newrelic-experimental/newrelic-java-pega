package com.pega.pegarules.boot.internal.extbridge;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.pega.bootstrap.Utils;

@Weave
public class AppServerBridgeToPega {
	
	public static Object invokeMethodPropagatingThrowable(Object[][] aDescriptor, int aMethodIndex, Object aSourceInstance, Object[] aArgs)  {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("AppServerBridgeToPega/invokeMethodPropagatingThrowable");
		if(aSourceInstance != null) {
			segment.addCustomAttribute("Pega-SourceInstance", aSourceInstance.getClass().getName());
		}
		long startTime = System.currentTimeMillis();
		Object returnedObject = Weaver.callOriginal();
		long endTime= System.currentTimeMillis();
		if(endTime - startTime >= Utils.TRACE_THRESHOLD) {
			segment.end();
		} else {
			segment.ignore();
		}
		
		return returnedObject;
	}
}