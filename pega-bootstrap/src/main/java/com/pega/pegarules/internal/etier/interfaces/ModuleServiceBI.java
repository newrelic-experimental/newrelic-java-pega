package com.pega.pegarules.internal.etier.interfaces;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ModuleServiceBI {

	@Trace
	public void doPost(ServletContext var1, HttpServletRequest var2, HttpServletResponse var3) {
		Weaver.callOriginal();
	}

}
