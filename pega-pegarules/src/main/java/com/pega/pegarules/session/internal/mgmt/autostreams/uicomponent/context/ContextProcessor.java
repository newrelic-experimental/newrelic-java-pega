package com.pega.pegarules.session.internal.mgmt.autostreams.uicomponent.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.pub.clipboard.ClipboardPage;

@Weave
public abstract class ContextProcessor {

	@Trace
	private void executeSection(String sectionName, Map<String, Object> secIncInfo, int readOnlyStatus, int orgReadOnlyStatus, String usingPage, ClipboardPage usingClipPage) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "SectionName", sectionName);
		Utils.addAttribute(attributes, "UsingPage", usingPage);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	private void processSection(String contextHash, String sectionName, Map<String, Object> secIncInfo,
			boolean bMetadataCreation, boolean bAtomicUpates, boolean doNotSkipCache, boolean skipIncludeInfo) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addAttribute(attributes, "SectionName", sectionName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public void processDatapage(String pageName, Set<String> properties, Map<String, Object> propertiesWithFieldValues,
			String uniqueId, Map<String, String> params, JsonObject stepObject) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Pega-Page", pageName);
		traced.addCustomAttribute("Pega-UniqueID", uniqueId);
		Weaver.callOriginal();		
	}
	
	@Trace
	public void processClipboardPage(String pageName, Set<String> properties,
			Map<String, Object> propertiesWithFieldValues, String uniqueId, Map<String, Object> pageInfo,
			JsonObject stepObject) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Pega-Page", pageName);
		traced.addCustomAttribute("Pega-UniqueID", uniqueId);
		Weaver.callOriginal();				
	}
}
