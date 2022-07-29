package com.nr.instrumentation.pega.platform;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageConsumeParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.pega.platform.messaging.messagebus.MessageHandler;

public class NRMessageHandler<E extends Serializable> implements MessageHandler<E> {
	
	private MessageHandler<E> delegate = null;
	private static boolean isTransformed = false;
	private String delegateName;
	private MessageUtils.TypeOfDestination typeOfDest = null;
	private String destName = null;
	private static final String RESPONSE = "NeoRemoteExecutionResponse";
	
	public NRMessageHandler(MessageHandler<E> d,MessageUtils.TypeOfDestination destType, String name) {
		delegate = d;
		delegateName = delegate != null ? delegate.getClass().getSimpleName() : "UnknownHandler";
		typeOfDest = destType;
		destName = name;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(dispatcher = true)
	public void onMessage(E message) {
		if(destName.contains(RESPONSE)) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "NRMessageHandler", "MessageHandler",RESPONSE);
		} else {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "NRMessageHandler", "MessageHandler",destName);
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(typeOfDest != null && typeOfDest != MessageUtils.TypeOfDestination.UNKNOWN && destName != null && !destName.isEmpty()) {
			DestinationType dType = null;
			if(typeOfDest == MessageUtils.TypeOfDestination.QUEUE) {
				dType = DestinationType.NAMED_QUEUE;
			} else if(MessageUtils.TypeOfDestination.TOPIC == typeOfDest) {
				dType = DestinationType.NAMED_TOPIC;
			}
			MessageConsumeParameters params = MessageConsumeParameters.library("Pega-MessageBus").destinationType(dType).destinationName(destName).inboundHeaders(null).build();
			traced.reportAsExternal(params);
		} else {
			traced.setMetricName("Custom","MessageHandler",getClass().getSimpleName(),"onMessage");
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "DestinationName", destName);
		Utils.addAttribute(attributes, "Pega-MessageHandlerClass", getClass().getName());
		Utils.addAttribute(attributes,"Pega-MessageClass", message.getClass().getName());
		Utils.addAttribute(attributes, "ActualMessageHandler", delegateName);
		MessageUtils.processInboundMessage(attributes, message);
		traced.addCustomAttributes(attributes);
		
		NewRelic.getAgent().getInsights().recordCustomEvent("Pega_OnMessage", attributes);
		if(delegate != null) {
			delegate.onMessage(message);
		}
	}

}
