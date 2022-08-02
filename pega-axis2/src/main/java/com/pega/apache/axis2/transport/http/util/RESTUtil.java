package com.pega.apache.axis2.transport.http.util;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis2.AxisFault;
import com.pega.apache.axis2.context.MessageContext;
import com.pega.apache.axis2.description.AxisOperation;
import com.pega.apache.axis2.description.AxisService;
import com.pega.apache.axis2.engine.Handler;
import com.pega.apache.axis2.engine.Handler.InvocationResponse;

import java.io.InputStream;
import java.io.OutputStream;

@Weave
public abstract class RESTUtil {

    @Trace
    public static InvocationResponse processURLRequest(MessageContext msgContext, OutputStream out, String contentType) throws AxisFault {
        Handler.InvocationResponse return_response = Weaver.callOriginal();

        String[] transactionNames = null;

        String serviceName = null;
        AxisService axisService = msgContext.getAxisService();
        if (axisService != null) {
            serviceName = axisService.getName();
            NewRelic.addCustomParameter("Service Name", serviceName);
        }
        String operationName = null;
        AxisOperation axisOperation = msgContext.getAxisOperation();
        if (axisOperation != null) {
            if (axisOperation.getName() != null) {
                operationName = axisOperation.getName().getLocalPart();
                NewRelic.addCustomParameter("Operation Name", operationName);
            }
            if (serviceName == null) {
                axisService = axisOperation.getAxisService();
                if (axisService != null) {
                    serviceName = axisService.getName();
                }
            }
        }

        if (serviceName != null && operationName != null) {
            transactionNames = new String[]{"Axis", serviceName, operationName};
        }

        NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "Axis REST URL", transactionNames);

        return return_response;
    }

    @Trace
    public static InvocationResponse processXMLRequest(MessageContext msgContext, InputStream in, OutputStream out, String contentType) throws AxisFault {
        Handler.InvocationResponse return_response = Weaver.callOriginal();

        String[] transactionNames = null;

        String serviceName = null;
        AxisService axisService = msgContext.getAxisService();
        if (axisService != null) {
            serviceName = axisService.getName();
            NewRelic.addCustomParameter("Service Name", serviceName);
        }
        String operationName = null;
        AxisOperation axisOperation = msgContext.getAxisOperation();
        if (axisOperation != null) {
            if (axisOperation.getName() != null) {
                operationName = axisOperation.getName().getLocalPart();
                NewRelic.addCustomParameter("Operation Name", operationName);
            }
            if (serviceName == null) {
                axisService = axisOperation.getAxisService();
                if (axisService != null) {
                    serviceName = axisService.getName();
                }
            }
        }

        if (serviceName != null && operationName != null) {
            transactionNames = new String[]{"Axis", serviceName, operationName};
        }

        NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "Axis REST XML", transactionNames);

        return return_response;
    }

}
