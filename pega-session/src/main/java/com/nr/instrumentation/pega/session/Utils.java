package com.nr.instrumentation.pega.session;

import java.util.HashMap;

import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.context.PRNode;
import com.pega.pegarules.pub.context.PRRequestor;
import com.pega.pegarules.pub.context.PRThread;
import com.pega.pegarules.session.external.mgmt.EngineRunnable;
import com.pega.pegarules.session.external.mgmt.IPRNode;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;
import com.pega.pegarules.session.internal.async.BatchRequestorTask;

public class Utils {

	private static final String LAMBDA = "$$Lambda$";
	public static long TRACE_THRESHOLD = 100L;
	
	public static String getEngineRunnableClass(EngineRunnable runnable) {
		String classname = runnable.getClass().getSimpleName();
		int index = classname.indexOf(LAMBDA);
		if(index > -1) {
			return classname.substring(0, index);
		}
		
		return classname;
	}
	
	public static void addBatchRequestorTask(HashMap<String,Object> attributes, BatchRequestorTask task) {
		addAttribute(attributes, "BatchRequestor-ID", task.getId());
 	}
	
	public static void addAttribute(HashMap<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			if(!key.startsWith("Pega-")) key = "Pega-"+key;
			attributes.put(key, value);
		}
	}
	
	public static void addClipboardPage(HashMap<String, Object> attributes, ClipboardPage page) {
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
	
	public static void addClipboardPage(HashMap<String, Object> attributes, String prefix,ClipboardPage page) {
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
	
	public static void addPRThread(HashMap<String, Object> attributes, PRThread thread) {
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
	
	public static void addPRNode(HashMap<String, Object> attributes, PRNode node) {
		if(node != null) {
			addAttribute(attributes, "Node-Tier", node.getNodeTier());
			addAttribute(attributes, "Node-UniqueID", node.getNodeUniqueID());
			addAttribute(attributes, "Node-SystemName", node.getSystemName());
		}
	}
	
	public static void addIPRRequestor(HashMap<String, Object> attributes,IPRRequestor requestor) {
		if (requestor != null) {
			addAttribute(attributes, "Requestor-CorrelationID", requestor.getCorrelationId());
			addAttribute(attributes, "Requestor-ClientIP", requestor.getClientIPAddress());
			addAttribute(attributes, "Requestor-ID", requestor.getId());
			addPRNode(attributes, requestor.getNode());
			addAttribute(attributes, "Requestor-TenantIdentifier", requestor.getTenantIdentifier());
			addAttribute(attributes, "Requestor-TypeName", requestor.getTypeName());
		}
	}

	public static void addPRRequestor(HashMap<String, Object> attributes,PRRequestor requestor) {
		if (requestor != null) {
			addAttribute(attributes, "Requestor-CorrelationID", requestor.getCorrelationId());
			addAttribute(attributes, "Requestor-ID", requestor.getId());
			addPRNode(attributes, requestor.getNode());
			addAttribute(attributes, "Requestor-TypeName", requestor.getTypeName());
		}
	}

	public static void addIPRNode(HashMap<String,Object> attributes, IPRNode node) {
		if (node != null) {
			addAttribute(attributes, "IPRNode-Name", node.getName());
			addAttribute(attributes, "IPRNode-NodeUniqueID", node.getNodeUniqueID());
			addAttribute(attributes, "IPRNode-URL", node.getNodeUrl());
			addAttribute(attributes, "IPRNode-SystemName", node.getSystemName());
			addAttribute(attributes, "IPRNode-SystemType", node.getSystemType());
			addAttribute(attributes, "IPRNode-SystemType", node.getSystemType());
			addPRNode(attributes, node);
			addPRThread(attributes, node.getThread());
		}

	}
}
