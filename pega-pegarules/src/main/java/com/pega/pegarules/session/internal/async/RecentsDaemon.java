package com.pega.pegarules.session.internal.async;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.pega.pegarules.Utils;
import com.pega.pegarules.data.external.access.TableInformation;
import com.pega.pegarules.pub.clipboard.ClipboardPage;
import com.pega.pegarules.pub.database.DatabaseTable;
import com.pega.pegarules.session.external.mgmt.IPRRequestor;
import com.pega.pegarules.session.external.mgmt.IPRThread;

@SuppressWarnings("rawtypes")
@Weave
public class RecentsDaemon {

	@Trace
	public void deleteRecentRecord(IPRThread theThread, ClipboardPage aPage) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "RecentsDaemon", "RecentsDaemon","DeleteRecentRecord");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRThread(attributes, theThread);
		Utils.addClipboardPage(attributes, aPage);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public void persistRecents(IPRRequestor aRequestor, Object aBase1, Object aBase2, Object[] aInputs, Map aInfo) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "RecentsDaemon", "RecentsDaemon","PersistRecents");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRRequestor(attributes, aRequestor);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public void persistRecentsForOperator(Map recents, String operatorId, int maxRecents, IPRThread theThread, DatabaseTable table, TableInformation tableInfo) {
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "RecentsDaemon", "RecentsDaemon","PersistRecents");
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addIPRThread(attributes, theThread);
		Utils.addDatabaseTable(attributes, table);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		
		Weaver.callOriginal();
	}
}
