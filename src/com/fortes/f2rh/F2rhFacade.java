package com.fortes.f2rh;

import java.util.Collection;

import org.apache.commons.httpclient.NameValuePair;

public interface F2rhFacade {

	public abstract Collection<Curriculo> obterCurriculos(ConfigF2RH config);

	public abstract String find_f2rh(ConfigF2RH config);
	
	public abstract NameValuePair[] prepareParams(String[] query);

}