package com.nr.instrumentation.pega.pegarules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.pega.pegarules.exec.external.async.AsyncServiceInputData;
//import com.pega.pegarules.exec.external.async.AsyncServiceInputData;
import com.pega.pegarules.priv.runtime.IDataSyncContext;
import com.pega.pegarules.priv.services.jsr94.ServiceInfo;
import com.pega.pegarules.priv.tracer.ActivityTraceInfo;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.PRNode;
import com.pega.pegarules.pub.context.PRRequestor;
import com.pega.pegarules.pub.context.PRThread;
import com.pega.pegarules.pub.context.ProcessingStatus;
import com.pega.pegarules.pub.database.DatabaseTable;
import com.pega.pegarules.pub.runtime.ParameterPage;
import com.pega.pegarules.pub.util.StringMap;
import com.pega.pegarules.session.external.async.IBatchRequestorTask;
import com.pega.pegarules.session.external.mgmt.IPRNode;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;
import com.pega.pegarules.session.external.mgmt.IPRThread;
import com.pega.pegarules.session.internal.async.BatchRequestorTask;
import com.pega.platform.executor.jobscheduler.internal.config.ActivityExecutionConfig;
import com.pega.platform.executor.jobscheduler.internal.config.JobScheduleConfig;

public class Utils {

	private static final String THREAD_ATTRIBUTE = "Pega-JavaThread";
	private static final String THREADGROUP_ATTRIBUTE = "Pega-JavaThreadGroup";

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
	

	public static void addAsyncInputData(Map<String, Object> attributes, AsyncServiceInputData data) {
		if(data != null) {
			addAttribute(attributes, "CacheKey", data.getCacheKey());
			addAttribute(attributes, "NodeId", data.getNodeId());
			addAttribute(attributes, "PoolId", data.getPoolID());
			addAttribute(attributes, "RequestorID", data.getRequestorID());
			addAttribute(attributes, "ServiceName", data.getServiceName());
			addAttribute(attributes, "TenantId", data.getTenantId());
			addAttribute(attributes, "ThreadName", data.getThreadName());
		}
	}

	
	public static void addProcessingStatus(Map<String, Object> attributes,String prefix, ProcessingStatus status) {
		if(status != null) {
			String name = prefix != null && !prefix.isEmpty() ? prefix + "-LatestServerityText" : "LatestServerityText";
			addAttribute(attributes, name, status.getLatestSeverityText());
			name = prefix != null && !prefix.isEmpty() ? prefix + "-LatestMessage" : "LatestMessage";
			addAttribute(attributes, name, status.getLatestMessage());
		}
	}
	
	public static void addIDataSyncContext(Map<String, Object> attributes, IDataSyncContext ctx) {
		if(ctx != null) {
			addAttribute(attributes, "IDataSyncContext-ChannelID", ctx.getChannelId());
			addAttribute(attributes, "IDataSyncContext-NodeID", ctx.getNodeId());
		}
	}
	
	public static void addBatchRequestorTask(Map<String,Object> attributes, BatchRequestorTask task) {
		if (task != null) {
			addAttribute(attributes, "BatchRequestor-ID", task.getId());
		}
 	}
	
	public static void addIBatchRequestorTask(Map<String,Object> attributes, IBatchRequestorTask task) {
		if (task != null) {
			addAttribute(attributes, "IBatchRequestor-ID", task.getId());
		}
 	}
	
	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			if(!key.toLowerCase().startsWith("pega-")) {
				key = "Pega-"+key;
			}
			attributes.put(key, value);
		}
		if(!attributes.containsKey(THREAD_ATTRIBUTE)) {
			Thread current = Thread.currentThread();
			String threadName = current.getName();
			attributes.put(THREAD_ATTRIBUTE, threadName);
			ThreadGroup threadGroup = current.getThreadGroup();
			if(threadGroup != null) {
				String threadGroupName = threadGroup.getName();
				if(threadGroupName != null && !threadGroupName.isEmpty()) {
					attributes.put(THREADGROUP_ATTRIBUTE, threadGroupName);
				}
			}
		}
	}
	
	public static void addServiceInfo(Map<String, Object> attributes, ServiceInfo info) {
		if(info != null) {
			addAttribute(attributes, "Service-ClassName", info.getClassName());
			addAttribute(attributes, "Service-MethodName", info.getMethodName());
			addAttribute(attributes, "Service-PackageName", info.getPackageName());
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
	
	public static void addPRThread(Map<String, Object> attributes, PRThread thread) {
		if (thread != null) {
			addAttribute(attributes, "PRThread", thread.getName());
			ClipboardPage nodePage = thread.getNodePage();
			if (nodePage != null) {
				addAttribute(attributes, "PRThread-NodePage", nodePage.getName());
			}
			ClipboardPage reqPage = thread.getRequestorPage();
			if (reqPage != null) {
				addAttribute(attributes, "PRThread-RequestorPage", reqPage.getName());
			}
			ClipboardPage threadPage = thread.getThreadPage();
			if (threadPage != null) {
				addAttribute(attributes, "PRThread-ThreadPage", threadPage.getName());
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

	public static void addPRRequestor(Map<String, Object> attributes,PRRequestor requestor) {
		if (requestor != null) {
			addAttribute(attributes, "Requestor-CorrelationID", requestor.getCorrelationId());
			addAttribute(attributes, "Requestor-ID", requestor.getId());
			addPRNode(attributes, requestor.getNode());
			addAttribute(attributes, "Requestor-TypeName", requestor.getTypeName());
		}
	}

	public static void addIPRNode(Map<String,Object> attributes, IPRNode node) {
		if (node != null) {
			addAttribute(attributes, "IPRNode-Name", node.getName());
			addAttribute(attributes, "IPRNode-NodeUniqueID", node.getNodeUniqueID());
//			addAttribute(attributes, "IPRNode-URL", node.getNodeUrl());
//			addAttribute(attributes, "IPRNode-SystemName", node.getSystemName());
//			addAttribute(attributes, "IPRNode-SystemType", node.getSystemType());
//			addAttribute(attributes, "IPRNode-SystemType", node.getSystemType());
			addPRNode(attributes, node);
			addPRThread(attributes, node.getThread());
		}

	}
	
	public static void addActivityTraceInfo(Map<String, Object> attributes, ActivityTraceInfo info) {
		if(info != null) {
			addAttribute(attributes, "ActivityTraceInfo-ActivityType", info.getActivityType());
			addAttribute(attributes, "ActivityTraceInfo-KeyName", info.getKeyName());
			addAttribute(attributes, "ActivityTraceInfo-RuleSetName", info.getRuleSetName());
			addAttribute(attributes, "ActivityTraceInfo-RuleType", info.getRuleType());
		}
	}
	
	public static void addDatabaseTable(Map<String,Object> attributes, DatabaseTable dbTable) {
		if(dbTable != null) {
			addAttribute(attributes, "DatabaseTable-DBName", dbTable.getDBName());
			addAttribute(attributes, "DatabaseTable-TableName", dbTable.getTableName());
			addAttribute(attributes, "DatabaseTable-TableType", dbTable.getTableType());
			addAttribute(attributes, "DatabaseTable-FullyQualifiedTableName", dbTable.getFullyQualifiedTableName());
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
	
	@SuppressWarnings("unchecked")
	public static void addStringMap(Map<String, Object> attributes, StringMap map) {
		if(map != null) {
			Set<String> keys = map.keySet();
			for(String key : keys) {
				addAttribute(attributes, "Keys-Key-"+key, map.get(key));
			}
		}
	}

}
