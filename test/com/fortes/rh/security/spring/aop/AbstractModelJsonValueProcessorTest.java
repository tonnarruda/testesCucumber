package com.fortes.rh.security.spring.aop;


import junit.framework.TestCase;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.factory.sesmt.EventoFactory;

public class AbstractModelJsonValueProcessorTest extends TestCase {

	AbstractModelJsonValueProcessor processor;
	private JsonConfig jsonConfig;
	private Evento entity;
	
	public void setUp() {
		processor = new AbstractModelJsonValueProcessor();
	}
	
	public void testProcessArrayValue() {
		dadoUmaEntidadeQualquerDoSistema();
		
		JSONObject json = (JSONObject) processor.processArrayValue(entity, jsonConfig);
		
		assertQueIdDaEntidadeFoiProcessado(json);
		assertQueChaveParaAuditoriaFoiProcessada(json);
	}

	public void testProcessObjectValue() {
		dadoUmaEntidadeQualquerDoSistema();
		
		JSONObject json = (JSONObject) processor.processObjectValue("estado", entity, jsonConfig);
		
		assertQueIdDaEntidadeFoiProcessado(json);
		assertQueChaveParaAuditoriaFoiProcessada(json);
	}

	private void dadoUmaEntidadeQualquerDoSistema() {
		entity = EventoFactory.getEntity(007L);
		entity.setNome("Festa na Casa das Primas");
	}
	
	private void assertQueChaveParaAuditoriaFoiProcessada(JSONObject json) {
		assertEquals("chaveParaAuditoria", entity.getNome(), json.get("chaveParaAuditoria"));
	}

	private void assertQueIdDaEntidadeFoiProcessado(JSONObject json) {
		assertEquals("id", new Integer(entity.getId().toString()), (Integer) json.get("id"));
	}

	
}
