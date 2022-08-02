package com.pega.apache.axis2.engine;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis2.AxisFault;
import com.pega.apache.axis2.context.MessageContext;
import com.pega.apache.axis2.engine.Handler.InvocationResponse;


@Weave(type = MatchType.ExactClass)
public abstract class AxisEngine {

    @Trace
    public static void send(MessageContext msgContext) throws AxisFault {
        Weaver.callOriginal();
    }

    @Trace
    public static InvocationResponse receive(MessageContext msgContext) {
    	
    	return Weaver.callOriginal();
    }
}
