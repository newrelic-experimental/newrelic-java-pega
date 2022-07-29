package com.pega.pegarules.management.internal.system.operations;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.management.internal.system.operations.jobinput.SystemOperationsJobInput;
import com.pega.pegarules.management.internal.system.operations.jobs.AbstractSystemOperationsJob;
import com.pega.platform.remoteexecution.ApplicabilityPredicate;

@Weave
class SystemOperationsDistributedExecutorImpl {

	@Trace
	public <T extends SystemOperationsJobInput, R extends Serializable> Map<String, ResultFromOneNode<R>> executeOnAllNodesInCluster(
			Class<? extends AbstractSystemOperationsJob<T, R>> aJobClass, T aJobInput) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public <T extends SystemOperationsJobInput, R extends Serializable> ResultFromOneNode<R> executeOnOneNodeBasedOnRequestorId(
			Class<? extends AbstractSystemOperationsJob<T, R>> aJobClass, T aJobInput, ApplicabilityPredicate tester) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public <T extends SystemOperationsJobInput, R extends Serializable> ResultFromOneNode<R> executeOnOneNodeInCluster(
			Class<? extends AbstractSystemOperationsJob<T, R>> aJobClass, T aJobInput, String aNodeId) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public <T extends SystemOperationsJobInput, R extends Serializable> Map<String, ResultFromOneNode<R>> executeOnSelectedNodesInCluster(
			Class<? extends AbstractSystemOperationsJob<T, R>> aJobClass, T aJobInput, Set<String> nodeIds) {
		return Weaver.callOriginal();
	}
}
