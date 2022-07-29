package com.nr.instrumentation.pega.pegarules;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.pega.pegarules.integration.engine.internal.services.http.HTTPUtils;
import com.pega.pegarules.pub.PRException;
import com.pega.pegarules.pub.util.StringUtils;
import com.pega.platform.web.request.internal.HttpRequestMapper;

public class HttpUtils {
	
	@SuppressWarnings("rawtypes")
	public static  void processInbound(Map requestMap, HttpRequestMapper mRequestMapper, TracedMethod traced, Transaction transaction, String REMOTE_ADDRESS, String QUERY_STRING, String METHOD) {
		
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "RemoteAddress", requestMap.get(REMOTE_ADDRESS));
		Utils.addAttribute(attributes, "QueryString", requestMap.get(QUERY_STRING));
		Utils.addAttribute(attributes, "Method", requestMap.get(METHOD));
		String requestURI =  (String) requestMap.get("requestURI");
		Utils.addAttribute(attributes, "RequestURI", requestURI);
		String requestData = (String) requestMap.get("serviceRequestData");
		Utils.addAttribute(attributes, "ServiceRequestData",requestData);
		
		traced.addCustomAttributes(attributes);
		try {
			Map<String, String> serviceInfo = HttpUtils.getServiceInfo(requestMap, mRequestMapper);
			if(serviceInfo != null && !serviceInfo.isEmpty()) {
				String theServicePackage = serviceInfo.get("ServicePackage");
				String theServiceClass = serviceInfo.get("ServiceClass");
				String theServiceMethod = serviceInfo.get("ServiceMethod");
				List<String> list = new ArrayList<String>();
				if(theServicePackage != null) {
					list.add(theServicePackage);
					Utils.addAttribute(attributes, "ServicePackage",theServicePackage);
				}
				if(theServiceClass != null) {
					list.add(theServiceClass);
					Utils.addAttribute(attributes, "ServiceClass",theServiceClass);
				}
				if(theServiceMethod != null) {
					list.add(theServiceMethod);
					Utils.addAttribute(attributes, "ServiceMethod",theServiceMethod);
				}
				if(!list.isEmpty()) {
					String[] names = new String[list.size()];
					list.toArray(names);
					transaction.setTransactionName(TransactionNamePriority.CUSTOM_LOW, true, "HttpService", names);
				}
			}
		} catch (UnsupportedEncodingException e) {
		} catch (PRException e) {
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> getServiceInfo(Map requestMap, HttpRequestMapper mapper) throws PRException, UnsupportedEncodingException {
		String encoding = (String) requestMap.get("characterEncoding");
		HashMap initParams = (HashMap) requestMap.get("com.pega.web.request.request_servlet_initparam");
		if(encoding == null && initParams != null) {
			encoding = (String) initParams.get("CharacterEncoding");
		}
		
		if(encoding != null) {
			encoding = StringUtils.stripQuotes(encoding);
		} else {
			encoding = "UTF-8";
		}
		String originalPathInfo = (String) requestMap.get("pathInfo");		
		final HashMap httpMapInfo = new HashMap();
		httpMapInfo.putAll(mapper.buildRequestorDataFromHttpRequest(requestMap));
		if (originalPathInfo == null) {
			originalPathInfo = (String) httpMapInfo.get("pxReqPathInfoReal");
		}
		String servletPath = (String) requestMap.get("servletPath");
		boolean hasRestServlet = StringUtils.isNotBlank(servletPath) && servletPath.endsWith("PRRestService");
		
		String appAliasName = "";
		if (!hasRestServlet) {
			appAliasName = HTTPUtils.processAppURL(originalPathInfo);
			if (StringUtils.isNotBlank(appAliasName)) {
				originalPathInfo = HTTPUtils.sanitizeAppURL(originalPathInfo, appAliasName);
			}
		}
		String pathInfo = HTTPUtils.adjustPathInfo(originalPathInfo, encoding);
		Map<String, String> keys = (Map<String, String>) HTTPUtils.extractKeys(pathInfo, encoding);
		
		return keys;
	}
	
}
