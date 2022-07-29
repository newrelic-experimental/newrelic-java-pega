package com.pega.pegarules.pub.runtime;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.PegaConfiguration;

@Weave(type = MatchType.Interface)
public abstract class Function {

	//@Trace
	public Object invoke(Object[] var1) {

		Segment segment = null;
		if(PegaConfiguration.PEGA_FUNCTION_ENABLED) {
			segment = NewRelic.getAgent().getTransaction().startSegment("Pega/Function");
		}
		long start = System.currentTimeMillis();
		String[] metadata = pzGetMetaData();
		String name = null;
		if(metadata != null && metadata.length > 0) {
			name = metadata[0]; 
		}

		Object result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if (segment != null) {
			if (end - start >= PegaConfiguration.PEGA_FUNCTION_THRESHOLD || PegaConfiguration.includeFunction(name)) {
				if (name != null) {
					segment.addCustomAttribute("Pega-FunctionName", name);
				}
				segment.end();
			} else {
				segment.ignore();
			} 
		}
		return result;
	}

	public abstract String[] pzGetMetaData();
}
