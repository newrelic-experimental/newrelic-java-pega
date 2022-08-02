package com.pega.apache.axis2.transport.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis2.AxisFault;
import com.pega.apache.axis2.context.ConfigurationContext;
import com.pega.apache.axis2.context.MessageContext;
import com.pega.apache.axis2.description.AxisOperation;
import com.pega.apache.axis2.description.AxisService;
import com.pega.apache.axis2.engine.Handler;
import com.pega.apache.axis2.engine.Handler.InvocationResponse;

@Weave(type = MatchType.ExactClass)
public abstract class HTTPTransportUtils {

    @SuppressWarnings("rawtypes")
	@Trace
    public static boolean processHTTPGetRequest(MessageContext msgContext, OutputStream out, String soapAction, String requestURI, ConfigurationContext configurationContext, Map requestParameters) throws AxisFault {
        boolean value = Weaver.callOriginal();
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
        }

        if (serviceName != null && operationName != null) {
            NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "Axis HTTP Get", new String[]{"Axis", serviceName, operationName});
        } else {
            if (soapAction != null) {
                NewRelic.addCustomParameter("SOAP Action", soapAction);
                String transactionName;
                if (!soapAction.startsWith("urn:")) {
                    transactionName = soapAction;
                } else {
                    transactionName = soapAction.substring(4);
                }
                NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "Axis HTTP Get", new String[]{"Axis", transactionName});
            } else {
                NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "Axis HTTP Get", new String[]{"Axis", requestURI});
            }

        }

        return value;
    }

    @Trace
    public static InvocationResponse processHTTPPostRequest(MessageContext msgContext, InputStream in, OutputStream out, String contentType, String soapActionHeader, String requestURI) throws AxisFault {
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
        } else {
            if (soapActionHeader != null) {
                NewRelic.addCustomParameter("SOAP Action Header", soapActionHeader);
                if (!soapActionHeader.startsWith("urn:")) {
                    transactionNames = new String[]{soapActionHeader};
                } else {
                    transactionNames = new String[]{soapActionHeader.substring(4)};
                }
            } else {
                transactionNames = new String[]{requestURI};
            }

        }

        NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "Axis HTTP Post", transactionNames);

        return return_response;
    }

}
