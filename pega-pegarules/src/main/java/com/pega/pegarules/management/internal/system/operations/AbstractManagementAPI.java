package com.pega.pegarules.management.internal.system.operations;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.management.internal.system.operations.jobinput.SystemOperationsJobInput;
import com.pega.pegarules.management.internal.system.operations.jobs.AbstractSystemOperationsJob;
import com.pega.platform.pegamanagement.systemoperation.SystemOperation;
import com.pega.platform.remoteexecution.ApplicabilityPredicate;

@Weave(type = MatchType.BaseClass)
public abstract class AbstractManagementAPI {

	@Trace
	protected <T extends SystemOperationsJobInput, R extends Serializable> Map<String, ResultFromOneNode<R>> executeOnAllNodes(JobInformation<T, R> remoteJobInfo, boolean skipSecurityCheck) {
		SystemOperation sysOp = remoteJobInfo.getOperation();
		Class<AbstractSystemOperationsJob<T, R>> jobClazz = remoteJobInfo.getJobClass();
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
	protected <T extends SystemOperationsJobInput, R extends Serializable> ResultFromOneNode<R> executeOnOneNode(String aNodeId, JobInformation<T, R> remoteJobInfo) {
		SystemOperation sysOp = remoteJobInfo.getOperation();
		Class<AbstractSystemOperationsJob<T, R>> jobClazz = remoteJobInfo.getJobClass();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "NodeId", aNodeId);
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
	protected <T extends SystemOperationsJobInput, R extends Serializable> ResultFromOneNode<R> executeOnOneNodeBasedOnRequestorId(JobInformation<T, R> remoteJobInfo, ApplicabilityPredicate tester) {
		SystemOperation sysOp = remoteJobInfo.getOperation();
		Class<AbstractSystemOperationsJob<T, R>> jobClazz = remoteJobInfo.getJobClass();
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
	<T extends SystemOperationsJobInput, R extends Serializable> Map<String, ResultFromOneNode<R>> executeOnSelectedNodes(Set<String> nodeIds, JobInformation<T, R> remoteJobInfo, boolean skipSecurityCheck) {
		SystemOperation sysOp = remoteJobInfo.getOperation();
		Class<AbstractSystemOperationsJob<T, R>> jobClazz = remoteJobInfo.getJobClass();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "Job-Class", jobClazz != null ? jobClazz.getName() : null);
		if (sysOp != null) {
			Utils.addAttribute(attributes, "SystemOperation-PrivilegeName", sysOp.getPrivilegeName());
			Utils.addAttribute(attributes, "SystemOperation-ClusterPrivilegeName",
					sysOp.getClusterPrivilegeName());
		}
		StringBuffer sb = new StringBuffer();
		int size = nodeIds.size();
		int index = 0;
		for(String nodeId : nodeIds) {
			sb.append(nodeId);
			if(index < size - 1) {
				sb.append(',');
			}
			index++;
		}
		Utils.addAttribute(attributes, "NodeIds", sb.toString());
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
}
