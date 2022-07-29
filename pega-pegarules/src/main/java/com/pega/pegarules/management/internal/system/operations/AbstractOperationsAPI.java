package com.pega.pegarules.management.internal.system.operations;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.management.internal.system.operations.handler.MultiNodeOperationsResultHandler;
import com.pega.pegarules.management.internal.system.operations.handler.OperationsResultHandler;
import com.pega.pegarules.management.internal.system.operations.jobinput.SystemOperationsJobInput;
import com.pega.pegarules.management.internal.system.operations.jobs.AbstractSystemOperationsJob;
import com.pega.pegarules.priv.management.PegaManagementResultBase;
import com.pega.platform.pegamanagement.systemoperation.SystemOperation;

@SuppressWarnings("rawtypes")
@Weave(type = MatchType.BaseClass)
public abstract class AbstractOperationsAPI extends AbstractManagementAPI {

	@Trace
	public <T extends SystemOperationsJobInput, R extends Serializable, W extends PegaManagementResultBase> W executeOnAllNodes(
			JobInformation<T, R> aJobInfo, MultiNodeOperationsResultHandler<R, W> aResultBinder) {
		SystemOperation sysOp = aJobInfo.getOperation();
		Class<AbstractSystemOperationsJob<T, R>> jobClazz = aJobInfo.getJobClass();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Job-Class", jobClazz != null ? jobClazz.getName() : null);
		if (sysOp != null) {
			Utils.addAttribute(attributes, "SystemOperation-PrivilegeName", sysOp.getPrivilegeName());
			Utils.addAttribute(attributes, "SystemOperation-ClusterPrivilegeName",
					sysOp.getClusterPrivilegeName());
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
		
	}
	
	@Trace
	public <T extends SystemOperationsJobInput, R extends Serializable, W extends PegaManagementResultBase> Map<String, W> executeOnAllNodes(
			JobInformation<T, R> aJobInfo, OperationsResultHandler<R, W> aResultBinder) {
		SystemOperation sysOp = aJobInfo.getOperation();
		Class<AbstractSystemOperationsJob<T, R>> jobClazz = aJobInfo.getJobClass();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Job-Class", jobClazz != null ? jobClazz.getName() : null);
		if (sysOp != null) {
			Utils.addAttribute(attributes, "SystemOperation-PrivilegeName", sysOp.getPrivilegeName());
			Utils.addAttribute(attributes, "SystemOperation-ClusterPrivilegeName",
					sysOp.getClusterPrivilegeName());
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace
	public <T extends SystemOperationsJobInput, R extends Serializable, W extends PegaManagementResultBase> W executeOnSelectedNodes(
			String aNodeType, JobInformation<T, R> aJobInfo, MultiNodeOperationsResultHandler<R, W> aResultBinder) {
		SystemOperation sysOp = aJobInfo.getOperation();
		Class<AbstractSystemOperationsJob<T, R>> jobClazz = aJobInfo.getJobClass();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Job-Class", jobClazz != null ? jobClazz.getName() : null);
		if (sysOp != null) {
			Utils.addAttribute(attributes, "SystemOperation-PrivilegeName", sysOp.getPrivilegeName());
			Utils.addAttribute(attributes, "SystemOperation-ClusterPrivilegeName",
					sysOp.getClusterPrivilegeName());
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
}
