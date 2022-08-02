package com.pega.apache.axis2.context;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.pega.apache.axis2.addressing.EndpointReference;
import com.pega.apache.axis2.description.AxisMessage;
import com.pega.apache.axis2.description.AxisOperation;
import com.pega.apache.axis2.description.AxisService;

@Weave
public abstract class MessageContext {

	@NewField
	public Token token = null;
	
	public MessageContext() {
		
	}
	
	MessageContext(ConfigurationContext configContext) {
		
	}
	
	public abstract AxisService getAxisService();
	
	public abstract AxisOperation getAxisOperation();
	
	public abstract EndpointReference getTo();
	
	public abstract AxisMessage getAxisMessage();
	
	public abstract String getSoapAction();
}
