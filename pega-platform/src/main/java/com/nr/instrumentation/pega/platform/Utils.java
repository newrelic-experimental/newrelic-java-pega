package com.nr.instrumentation.pega.platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.PRNode;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;
import com.pega.pegarules.session.external.mgmt.IPRRequestorForModules;
import com.pega.pegarules.session.external.mgmt.IPRThread;
import com.pega.platform.executor.jobscheduler.internal.config.ActivityExecutionConfig;
import com.pega.platform.executor.jobscheduler.internal.config.JobScheduleConfig;
import com.pega.platform.remoteexecution.ResponseMessage;
import com.pega.platform.remoteexecution.internal.RequestMessage;

public class Utils {

	private static final String prefix = "com.pegarules.generated";
	
	private static final List<String> prefixes = new ArrayList<String>();
	
	static {
		prefixes.add("ra");
		prefixes.add("action");
		prefixes.add("model");
		prefixes.add("when");
		prefixes.add("baseclass");
		prefixes.add("stream");
		prefixes.add("model");
		prefixes.add("pegaaesremote");
		prefixes.add("channel");
		prefixes.add("triage");
		prefixes.add("email");
	}
	
	public static String getPegaName(Class<?> clazz) {
		Package pack = clazz.getPackage();
		if(pack != null) {
			String packName = pack.getName();
			
			if(!packName.startsWith(prefix)) return clazz.getName();
		}
		
		String simpleName = clazz.getSimpleName();
		
		StringTokenizer st = new StringTokenizer(simpleName, "_");
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			
			if(!prefixes.contains(token.toLowerCase())) {
				return token;
			}
		}
		return clazz.getName();
	}
	
	public static <S extends Serializable> void addResponseMessage(Map<String, Object> attributes, ResponseMessage<S> response) {
		if(response != null) {
			addAttribute(attributes, "ResponseMessage-ExecutingNode", response.getExecutingNode());
			addAttribute(attributes, "ResponseMessage-ExecutionID", response.getExecutionID());
			addAttribute(attributes, "ResponseMessage-UniqueJobID", response.getUniqueJobID());
		}
	}
	
	public static <P extends Serializable, R extends Serializable> void addRequestMessage(Map<String, Object> attributes, RequestMessage<P, R> message) {
		if(message != null) {
			addAttribute(attributes, "Request-Message-JobClass", message.getJobClass());
			addAttribute(attributes, "Request-Message-RequestingNode", message.getRequestingNode());
			addAttribute(attributes, "Request-Message-ResponseQueueName", message.getResponseQueueName());
			addAttribute(attributes, "Request-Message-UniqueJobID", message.getUniqueJobID());
			
		}
	}
	
	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			if(!key.startsWith("Pega-")) key = "Pega-" + key;
			attributes.put(key, value);
		}
	}
	
	public static void addClipboardPage(Map<String, Object> attributes, ClipboardPage page) {
		if (page != null) {
			addAttribute(attributes, "Page-Classname", page.getClassName());
			addAttribute(attributes, "Page-Name", page.getName());
			ClipboardPage parent = page.getParentPage();
			if (parent != null) {
				addAttribute(attributes, "Page-Parent-Classname", parent.getClassName());
				addAttribute(attributes, "Page-Parent-Name", parent.getName());
			} 
		}
	}
	
	public static void addClipboardPage(Map<String, Object> attributes, String prefix,ClipboardPage page) {
		if (page != null) {
			if (prefix != null && !prefix.isEmpty()) {
				addAttribute(attributes, prefix + "-Classname", page.getClassName());
				addAttribute(attributes, prefix + "-Name", page.getName());
				ClipboardPage parent = page.getParentPage();
				if (parent != null) {
					addAttribute(attributes, prefix + "-Parent-Classname", parent.getClassName());
					addAttribute(attributes, prefix + "-Parent-Name", parent.getName());
				}
			} else {
				addClipboardPage(attributes, page);
			} 
		}
	}
	
	public static void addIPRThread(Map<String, Object> attributes, IPRThread thread) {
		if (thread != null) {
			addAttribute(attributes, "IPRThread-Name", thread.getName());
			Thread javaThread = thread.getJavaThread();
			if (javaThread != null) {
				addAttribute(attributes, "IPRThread-JavaThread", javaThread.getName());
			}
			addClipboardPage(attributes, "NodePage", thread.getNodePage());
			addIPRRequestor(attributes, thread.getRequestor());
		}
		
	}
	
	public static void addJobScheduleConfig(Map<String, Object> attributes, JobScheduleConfig config) {
		if (config != null) {
			addAttribute(attributes, "JobSchedule-Activity", config.getActivityName());
			addAttribute(attributes, "JobSchedule-AssociatedClass", config.getAssociatedClass());
			addAttribute(attributes, "JobSchedule-Name", config.getName());
			addAttribute(attributes, "JobSchedule-RuleSet", config.getRuleSet());
			addAttribute(attributes, "JobSchedule-ScheduleName", config.getScheduleName());
		}
	}
	
	public static void addPRNode(Map<String, Object> attributes, PRNode node) {
		if(node != null) {
			addAttribute(attributes, "Node-Tier", node.getNodeTier());
			addAttribute(attributes, "Node-UniqueID", node.getNodeUniqueID());
			addAttribute(attributes, "Node-SystemName", node.getSystemName());
		}
	}
	
	public static void addActivityExecutionConfig(Map<String, Object> attributes, ActivityExecutionConfig config) {
		if(config != null) {
			addAttribute(attributes, "ActivityExecutionConfig-AccessGroup", config.getAccessGroup());
			addAttribute(attributes, "ActivityExecutionConfig-ActivityClass", config.getActivityClass());
			addAttribute(attributes, "ActivityExecutionConfig-Activity", config.getActivityName());
			addClipboardPage(attributes, "ActivityExecutionConfig", config.getPrimaryPage());
			addIPRThread(attributes, config.getThread());
		}
	}
	
	public static void addIPRRequestor(Map<String, Object> attributes,IPRRequestor requestor) {
		if (requestor != null) {
			addAttribute(attributes, "Requestor-CorrelationID", requestor.getCorrelationId());
			addAttribute(attributes, "Requestor-ClientIP", requestor.getClientIPAddress());
			addAttribute(attributes, "Requestor-ID", requestor.getId());
			addPRNode(attributes, requestor.getNode());
			addAttribute(attributes, "Requestor-TenantIdentifier", requestor.getTenantIdentifier());
			addAttribute(attributes, "Requestor-TypeName", requestor.getTypeName());
		}
	}

	public static void addIPRRequestor(Map<String, Object> attributes,IPRRequestorForModules requestor) {
		if (requestor != null) {
			addAttribute(attributes, "IPRRequestorForModules-ID", requestor.getId());
			addPRNode(attributes, requestor.getNode());
			addAttribute(attributes, "IPRRequestorForModules-TenantIdentifier", requestor.getTenantIdentifier());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void addParameterPage(Map<String, Object> attributes, ParameterPage page) {
		if(page != null) {
			Set<String> keys = page.keySet();
			for(String key : keys) {
				addAttribute(attributes, "ParameterPage-Key-"+key, page.get(key));
			}
		}
	}
}
