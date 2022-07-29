package com.pega.pegarules.pub.runtime;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.PegaConfiguration;
import com.nr.instrumentation.pega.pegarules.Utils;

@Weave(type=MatchType.Interface)
public abstract class Activity {

	//@Trace
	public void perform() {
		Class<?> clazz = getClass();
		String actionName = Utils.getPegaName(clazz);
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("Activity/"+actionName);
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "PegaCore", "Pega","Activity",actionName);
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= PegaConfiguration.PEGA_ACTIVITY_THRESHOLD) {
			segment.end();
		} else {
			segment.ignore();
		}
	}
}
