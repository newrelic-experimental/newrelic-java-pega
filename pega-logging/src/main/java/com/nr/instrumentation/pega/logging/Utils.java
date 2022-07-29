package com.nr.instrumentation.pega.logging;

import java.util.HashMap;

import org.apache.logging.log4j.core.LogEvent;

import com.pega.pegarules.priv.util.logging.SOAPAppenderPega;

public class Utils {

	public static void addLogEvent(HashMap<String, Object> attributes,LogEvent event, SOAPAppenderPega appender) {
		
		addAttribute(attributes, "Log-Level", event.getLevel());
		addAttribute(attributes, "Log-LoggerName", event.getLoggerName());
		addAttribute(attributes, "Log-Thread", event.getThreadName());
		addAttribute(attributes, "Log-Source", event.getSource());
		String message = event.getMessage().getFormat();
		addAttribute(attributes, "Log-Message", message);
		String exText = getThrowableAsString(event);
		addAttribute(attributes, "Log-ExceptionText", exText);
		addAttribute(attributes, "Appender-ServiceClass", appender.getServiceClass());
		addAttribute(attributes, "Appender-ServicePackage", appender.getServicePackage());
		addAttribute(attributes, "Appender-UserName", appender.getUserName());
	}
	
	public static void addAttribute(HashMap<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			if(!key.startsWith("Pega-")) key = "Pega-" + key;
			attributes.put(key, value);
		}
	}
	
	private static String getThrowableAsString(LogEvent aEvent) {
		if(aEvent == null || aEvent.getThrown()  == null) return null;
		String xDetails = aEvent.getThrown().toString();
		int xdLength = xDetails.length();
		StringBuilder sb = new StringBuilder(xdLength * 128);

		for (int i = 0; i < xdLength; ++i) {
			sb.append(xDetails);
			sb.append("\n");
		}

		return sb.toString();
	}

}
