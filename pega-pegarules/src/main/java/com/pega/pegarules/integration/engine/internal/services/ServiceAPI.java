package com.pega.pegarules.integration.engine.internal.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;

@Weave
public abstract class ServiceAPI {


	protected String mServiceType = Weaver.callOriginal();
	protected String mServicePackage = Weaver.callOriginal();
	protected String mServiceClass = Weaver.callOriginal();
	protected String mServiceMethod = Weaver.callOriginal();
	private String mUserName = Weaver.callOriginal();
	private String mPrimaryPageName = Weaver.callOriginal();
	private String mPrimaryPageClass = Weaver.callOriginal();
	private String mPrimaryPageModel = Weaver.callOriginal();
	private String mServiceActivity = Weaver.callOriginal();
	private String mExecutionMode = Weaver.callOriginal();
	private String mRequestProcessorName = Weaver.callOriginal();

	@Trace
	public void processRequest() {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		if (!transaction.isTransactionNameSet()) {
			List<String> list = new ArrayList<String>();
			if (mServiceType != null && !mServiceType.isEmpty()) {
				list.add(mServiceType);
			}
			if (mServicePackage != null && !mServicePackage.isEmpty()) {
				list.add(mServicePackage);
			}
			if (mServiceMethod != null && !mServiceMethod.isEmpty()) {
				list.add(mServiceMethod);
			}
			if (!list.isEmpty()) {
				String[] names = new String[list.size()];
				list.toArray(names);
				transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "ServiceAPI", names);
			} 
		}
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Pega-ServiceType", mServiceType);
		Utils.addAttribute(attributes, "Pega-ServicePackage", mServicePackage);
		Utils.addAttribute(attributes, "Pega-ServiceClass", mServiceClass);
		Utils.addAttribute(attributes, "Pega-Username", mUserName);
		Utils.addAttribute(attributes, "Pega-PrimaryPageName", mPrimaryPageName);
		Utils.addAttribute(attributes, "Pega-PrimaryPageClass", mPrimaryPageClass);
		Utils.addAttribute(attributes, "Pega-PrimaryPageModel", mPrimaryPageModel);
		Utils.addAttribute(attributes, "Pega-ServiceActivity", mServiceActivity);
		Utils.addAttribute(attributes, "Pega-ExecutionMode", mExecutionMode);
		Utils.addAttribute(attributes, "Pega-RequestProcessorName", mRequestProcessorName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);

		Weaver.callOriginal();
	}
}
