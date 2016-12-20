package com.fortes.rh.security.spring.aop;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;

public class AbstractModelJsonValueProcessor implements JsonValueProcessor {

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return this.process(value, jsonConfig);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return this.process(value, jsonConfig);
	}

	/**
	 * Gera um JSONObject com os atributos "id" e "chaveParaAuditoria" apenas.
	 */
	private Object process(Object value, JsonConfig config) {
		if (value != null) {
			AbstractModel entity = (AbstractModel) value;
			return new JSONObject()
				.element("id", entity.getId())
				.element("chaveParaAuditoria", this.getChaveDaAuditoria(entity));
		}
		return new JSONObject(true);
	}

	/**
	 * Retorna a chave da auditoria.
	 */
	private String getChaveDaAuditoria(AbstractModel entity) {
		String chave = new ChaveNaEntidade(entity).procura();
		return StringUtils.defaultIfEmpty(chave, "");
	}

}
