package com.fortes.rh.security.spring.aop;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.fortes.rh.util.DateUtil;

public class DateFormatJsonValueProcessor implements JsonValueProcessor {

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return this.process(value, jsonConfig);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return this.process(value, jsonConfig);
	}

	/**
	 * Gera uma data no formato "dd/MM/yyyy HH:mm:ss".
	 */
	private Object process(Object value, JsonConfig config) {
		Date data = (Date) value;
		return DateUtil.formataDate(data, "dd/MM/yyyy HH:mm:ss");
	}

}
