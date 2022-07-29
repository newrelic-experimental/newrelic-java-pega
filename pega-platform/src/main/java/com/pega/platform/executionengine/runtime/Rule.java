package com.pega.platform.executionengine.runtime;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.platform.PegaConfiguration;
import com.nr.instrumentation.pega.platform.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.ParameterPage;

@Weave(type = MatchType.Interface)
public abstract class Rule<T> {
	
	public T main(Runtime var1, ClipboardPage var2, ParameterPage var3) {
		String pegaName = Utils.getPegaName(getClass());
		Segment segment = null;
		if(PegaConfiguration.PEGA_RULE_ENABLED) {
			segment = NewRelic.getAgent().getTransaction().startSegment("Rule/"+pegaName);
		}
		long start = System.currentTimeMillis();
		T result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(segment != null) {
			if(end - start >= PegaConfiguration.PEGA_RULE_THRESHOLD || PegaConfiguration.includeRule(pegaName)) {
				HashMap<String, Object> attributes = new HashMap<String, Object>();
				Utils.addClipboardPage(attributes, var2);
				segment.addCustomAttributes(attributes);
				segment.end();
			} else {
				segment.ignore();
			}
		}
		return result;
	}


}
