package com.fortes.f2rh;

import java.util.Collection;

import org.apache.commons.httpclient.NameValuePair;

public interface F2rhFacade {

	public abstract Collection<Curriculo> obterCurriculos(ConfigF2RH config);

	public abstract String find_f2rh(ConfigF2RH config) throws Exception;
	
	public abstract NameValuePair[] prepareParams(String[] query);

	public abstract Collection<Curriculo> buscarCurriculos(String[] consulta) throws Exception;

	public abstract String[] montaIds(String[] curriculosId);
	
	public Collection<Curriculo> buscarCurriculosComFoto(String[] consulta) throws Exception;

}