package com.pega.pegarules.session.internal.async;

import java.util.List;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.session.external.async.IAgentQueue;
import com.pega.pegarules.session.external.async.IAsyncThreadTask;
import com.pega.pegarules.session.external.async.IBatchRequestorTask;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;

@Weave
public abstract class Agent {

	public void addBatchRequestor(IBatchRequestorTask aTask) {
		if(aTask instanceof BatchRequestorTask) {
			BatchRequestorTask bTask = (BatchRequestorTask)aTask;
			if(bTask.token == null) {
				Token t = NewRelic.getAgent().getTransaction().getToken();
				if(t != null && t.isActive()) {
					bTask.token = t;
				} else if(t != null) {
					t.expire();
					t = null;
				}
			}

		}
		Weaver.callOriginal();
	}

	@SuppressWarnings("rawtypes")
	public BatchRequestorTask createBatchRequestorTask(IPRRequestor aRequestor, String aClassName, String aActivityName,
			ParameterPage aParamPage, ClipboardPage aPrimaryPage, List aOtherPages, IAgentQueue aAgentTask,
			String aRequestorType, boolean aIsDetachedTask, ClipboardPage aQueueItem, String aTenantUserId,
			String aTenantId) {
		BatchRequestorTask task = Weaver.callOriginal();
		if(task.token == null) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				task.token = t;
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		return task;
	}

	public boolean queueBatchAsyncLoad(IBatchRequestorTask aRunnable) {
		if(aRunnable instanceof BatchRequestorTask) {
			BatchRequestorTask batchTask = (BatchRequestorTask)aRunnable;
			if(batchTask.token == null) {
				Token t = NewRelic.getAgent().getTransaction().getToken();
				if(t != null && t.isActive()) {
					batchTask.token = t;
				} else if(t != null) {
					t.expire();
					t = null;
				}
			}
		}

		return Weaver.callOriginal();
	}

	public void addAsyncThreadTask(IAsyncThreadTask aTask) {
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void runAgentTask(Object aId) {
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void runBatchTask(Object aId) {
		Weaver.callOriginal();
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private IAsyncThreadTask createPRThreadTask(IPRRequestor aRequestor, String aClassName, String aActivityName,
			ParameterPage aParamPage, ClipboardPage aPrimaryPage, List aOtherPages) {
		IAsyncThreadTask task = Weaver.callOriginal();
		AsyncThreadTask aTask = (AsyncThreadTask)task;
		return task;
	}
}