package com.pega.pegarules.session.internal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.session.Utils;
import com.pega.pegarules.exec.external.util.TenantIdentifier;
import com.pega.pegarules.session.external.mgmt.EngineRunnable;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@SuppressWarnings("rawtypes")
@Weave
public abstract class PRSessionProviderImpl {

	public Object doUnsafeAccessToUnlockedRequestor(Object aRequestor, Object aBase1, Object aBase2, Object[] aInputs,Map aInfo, Object aRunnable, boolean aMayActivateRequestor, boolean aMayRecreateRequestor) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("PRSessionProviderImpl/doUnsafeAccessToUnlockedRequestor");

		if(Method.class.isAssignableFrom(aRunnable.getClass())) {
			Method method = (Method)aRunnable;
			if(method != null) {
				Utils.addAttribute(attributes, "Method-Class", method.getDeclaringClass().getName());
				Utils.addAttribute(attributes, "Method-Name", method.getName());
			}
		} else if(EngineRunnable.class.isAssignableFrom(aRunnable.getClass())) {
			EngineRunnable runnable = (EngineRunnable)aRunnable;
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "PRSessionProviderImpl","EngineRunnable",Utils.getEngineRunnableClass(runnable));
		}

		long start = System.currentTimeMillis();
		Object result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= Utils.TRACE_THRESHOLD) {
			String base1Class = aBase1 != null ? aBase1.getClass().getName() : null;
			Utils.addAttribute(attributes, "Base1", base1Class);

			String base2Class = aBase2 != null ? aBase2.getClass().getName() : null;
			Utils.addAttribute(attributes, "Base2", base2Class);
			String runnableClass = aRunnable != null ? aRunnable.getClass().getName() : null;
			Utils.addAttribute(attributes, "Runnable", runnableClass);
			segment.addCustomAttributes(attributes);
			segment.end();
		} else {
			segment.ignore();
		}

		return result;
	}

	Object doWithRequestorLocked(Object aRequestor, int aAttempts, long aInterval, Object aBase1, Object aBase2,
			Object[] aInputs, Map aInfo, Object aRunnable, boolean aMayActivateRequestor, boolean aMayRecreateRequestor,
			boolean aCreateLightWeightRequestorWithoutStandardThread, TenantIdentifier tenant) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		if(Method.class.isAssignableFrom(aRunnable.getClass())) {
			Method method = (Method)aRunnable;
			if(method != null) {
				Utils.addAttribute(attributes, "Method-Class", method.getDeclaringClass().getName());
				Utils.addAttribute(attributes, "Method-Name", method.getName());
			}
		} else if(EngineRunnable.class.isAssignableFrom(aRunnable.getClass())) {
			EngineRunnable runnable = (EngineRunnable)aRunnable;
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "PRSessionProviderImpl","EngineRunnable",Utils.getEngineRunnableClass(runnable));
		}
		if(aRunnable instanceof com.pega.dsm.dnode.stream.healthmonitor.StreamHealthCheckRunnable || aRunnable instanceof com.pega.platform.pegamanagement.healthmonitor.internal.PegaHTMLStreamRunnable) {
			NewRelic.getAgent().getTransaction().ignore();
		} else if(aBase1 instanceof com.pega.dsm.dnode.stream.healthmonitor.StreamHealthCheckRunnable || aBase1 instanceof com.pega.platform.pegamanagement.healthmonitor.internal.PegaHTMLStreamRunnable) {
			NewRelic.getAgent().getTransaction().ignore();
		}

		Object result = Weaver.callOriginal();
		return result;
	}

	protected void performPostProcessingAction(boolean gotLock, long lockInterval, IPRRequestor borrowedRequestor, IPRRequestor theRequestor, String requestorName, int originalLockCount, int readLocksHeldByCurrThread) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("PRSessionProviderImpl/performPostProcessingAction");
		long start = System.currentTimeMillis();
		Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= Utils.TRACE_THRESHOLD) {
			segment.end();
		} else {
			segment.ignore();
		}
	}

	protected Object performTargetActionWithLock(Object aBase1, Object aBase2, Object[] aInputs, Map aInfo, Object aRunnable, IPRRequestor theRequestor) {
		Segment segment = NewRelic.getAgent().getTransaction().startSegment("PRSessionProviderImpl/performPostProcessingAction");
		long start = System.currentTimeMillis();
		Object result = Weaver.callOriginal();
		long end = System.currentTimeMillis();
		if(end - start >= Utils.TRACE_THRESHOLD) {
			segment.end();
		} else {
			segment.ignore();
		}
		return result;
	}

	protected RequestorInfo obtainRequestor(Object aRequestor, boolean aMayActivateRequestor,
			boolean aMayRecreateRequestor, boolean aCreateLightWeightRequestorWithoutStandardThread,
			boolean aMayBorrowRequestor, TenantIdentifier tenant) {
		RequestorInfo info = Weaver.callOriginal();
		if(info != null) {
			HashMap<String, Object> attributes = new HashMap<String, Object>();
			Utils.addIPRRequestor(attributes, info.theRequestor);
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		}
		return info;
	}

	@Weave
	protected static class RequestorInfo {
		IPRRequestor theRequestor = Weaver.callOriginal();
	}
}
