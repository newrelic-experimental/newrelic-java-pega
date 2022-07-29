package com.pega.dsm.dnode.impl.dataset.stream;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.pega.dsm.dnode.api.stream.DataSubscriber;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.runtime.PublicAPI;

@Weave
public abstract class StreamBrowseOperation {

	@Trace(dispatcher = true)
	private void processRecords(PublicAPI tools, DataSubscriber<ClipboardPage> subscriber) {
		
		Weaver.callOriginal();
	}
}
