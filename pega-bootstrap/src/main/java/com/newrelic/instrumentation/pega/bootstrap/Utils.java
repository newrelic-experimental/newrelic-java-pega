package com.newrelic.instrumentation.pega.bootstrap;

import java.util.Map;

public class Utils {
	
	public static long TRACE_THRESHOLD = 100L;

	public static String PINGSERVICE = "/prweb/PRRestService/monitor/pingService/ping";
	
	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null ) {
			if(!key.startsWith("Pega-")) key = "Pega-" + key;
			attributes.put(key, value);
		}
	}
}
