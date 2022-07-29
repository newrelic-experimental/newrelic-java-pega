package com.pega.pegarules.internal.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.pega.bootstrap.PegaWebHeaders;
import com.newrelic.instrumentation.pega.bootstrap.Utils;

@Weave
public abstract class WebStandardBoot {

	@Trace
	public void doPost(HttpServletRequest aReq, HttpServletResponse aResp) {
		String reqURI = aReq.getRequestURI();
		if(reqURI.equalsIgnoreCase(Utils.PINGSERVICE)) {
			NewRelic.getAgent().getTransaction().ignore();
		} else {
			PegaWebHeaders webHeaders = new PegaWebHeaders(aReq, aResp);
			Transaction transaction = NewRelic.getAgent().getTransaction();
			transaction.acceptDistributedTraceHeaders(TransportType.HTTP, webHeaders);
			transaction.insertDistributedTraceHeaders(webHeaders);
		}
		Weaver.callOriginal();
	}
}
