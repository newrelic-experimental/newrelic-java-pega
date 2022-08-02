package com.pega.apache.axis.client;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.apache.axis.description.ServiceDesc;
import com.pega.apache.axis.handlers.soap.SOAPService;

@Weave
public abstract class Call {

	public abstract Service getService();
	private SOAPService myService = Weaver.callOriginal();

	@Trace
	public void invoke() {
		String serviceName = null;
		Service service  = getService();
		if(service != null) {
			QName serviceQName = service.getServiceName();
			if(serviceQName != null) {
				serviceName = serviceQName.getLocalPart();
			}
		}
		if(serviceName == null) {
			if(myService != null) {
				ServiceDesc serviceDesc = myService.getServiceDescription();
				if(serviceDesc != null) {
					serviceName = serviceDesc.getName();
					if(serviceName == null) {
						serviceName = serviceDesc.getEndpointURL();
					}
				}
			}
			serviceName = "UnknownService";
		}
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "PegaAxis", new String[] {serviceName});
		Weaver.callOriginal();
	}
}
