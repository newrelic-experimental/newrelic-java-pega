package com.pega.apache.axis2.receivers;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis2.AxisFault;
import com.pega.apache.axis2.context.MessageContext;
import com.pega.apache.axis2.description.AxisMessage;

@Weave(type = MatchType.BaseClass)
public abstract class AbstractMessageReceiver {

    @Trace
    protected void invokeBusinessLogic(MessageContext msgCtx) throws AxisFault {
        AxisMessage axisMsg = msgCtx.getAxisMessage();
        String axisName = axisMsg.getName();
        String axisOpName = axisMsg.getAxisOperation().getName().getLocalPart();

        TracedMethod traced = NewRelic.getAgent().getTracedMethod();
        traced.setMetricName(new String[]{"Custom", "MessageReceiver", axisName, axisOpName});
        traced.addRollupMetricName(new String[]{"Custom", "MessageReceiver", "invokeBusinessLogic", axisName});
        Weaver.callOriginal();
    }

    @Trace
    public void receive(MessageContext msgCtx) throws AxisFault {
        AxisMessage axisMsg = msgCtx.getAxisMessage();
        String axisName = axisMsg.getName();
        String axisOpName = axisMsg.getAxisOperation().getName().getLocalPart();

        TracedMethod traced = NewRelic.getAgent().getTracedMethod();
        traced.setMetricName(new String[]{"Custom", "MessageReceiver", axisName, axisOpName});
        traced.addRollupMetricName(new String[]{"Custom", "MessageReceiver", axisName});
        Weaver.callOriginal();
    }

}
