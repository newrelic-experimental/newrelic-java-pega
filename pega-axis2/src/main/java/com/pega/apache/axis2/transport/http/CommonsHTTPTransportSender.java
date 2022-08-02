package com.pega.apache.axis2.transport.http;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis2.context.MessageContext;
import com.pega.apache.axis2.engine.Handler;

@Weave
public abstract class CommonsHTTPTransportSender {

    public abstract String getName();

    @Trace
    public Handler.InvocationResponse invoke(MessageContext msgContext) {
        NewRelic.getAgent().getTracedMethod().setMetricName(new String[]{"Custom", getName(), msgContext.getSoapAction()});
        return Weaver.callOriginal();
    }

}
