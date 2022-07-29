package com.newrelic.instrumentation.pege.requestprocessor;

import java.util.List;
import java.util.function.BiConsumer;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.pega.fnx.stream.spi.model.MessageWithMetadata;
import com.pega.fnx.stream.spi.response.GetMessageResponse;

public class GetCompletionBiConsumer implements BiConsumer<GetMessageResponse, Throwable> {

	private Segment segment = null;
	private long start;

	public GetCompletionBiConsumer(String segmentName) {
		segment = NewRelic.getAgent().getTransaction().startSegment(segmentName);
		start = System.currentTimeMillis();
	}

	@Override
	public void accept(GetMessageResponse t, Throwable u) {
		if(segment != null) {
			List<MessageWithMetadata> msgs =  t.getMessages();
			if(!msgs.isEmpty()) {
				long end = System.currentTimeMillis();
				if(end - start >= 100) {
					segment.end();
				} else {
					segment.ignore();
				}
			} else {
				segment.ignore();
			}
			segment = null;
		}
		if(u != null) {
			NewRelic.noticeError(u);
		}
	}

}
