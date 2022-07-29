package com.newrelic.instrumentation.pega.aws;

import java.util.logging.Level;

import com.newrelic.agent.service.AbstractService;
import com.newrelic.api.agent.NewRelic;

public class AWSInstanceNaming extends AbstractService {

	public AWSInstanceNaming() {
		super("AWSInstanceNaming");
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected void doStart() throws Exception {
		String awsName = System.getProperty("identification.nodeid");
		if(awsName != null && !awsName.isEmpty()) {
			NewRelic.setInstanceName(awsName);
			NewRelic.getAgent().getLogger().log(Level.INFO, "Set instance name to {0}",awsName);
		} else {
			NewRelic.getAgent().getLogger().log(Level.INFO, "Unable to set instance name because AWS property not found",awsName);
		}
	}

	@Override
	protected void doStop() throws Exception {
		
	}
}
