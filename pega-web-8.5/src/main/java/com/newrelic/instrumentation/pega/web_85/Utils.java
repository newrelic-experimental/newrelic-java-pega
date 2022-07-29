package com.newrelic.instrumentation.pega.web_85;

import java.util.Map;

public class Utils {

	
	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			if(!key.startsWith("Pega-")) key = "Pega-" + key;
			attributes.put(key, value);
		}
	}
}
