package com.fortes.rh.security.spring.aop;

import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.time.DateUtils;

public class DateFormatJsonValueProcessorTest extends TestCase {

	private static final String DATA_ESPERADA = "07/03/1984 07:14:21";
	
	DateFormatJsonValueProcessor processor;
	private JsonConfig jsonConfig;
	
	public void setUp() {
		processor = new DateFormatJsonValueProcessor();
	}
	
	public void testProcessObjectValue() {
		
		Date aniversarioDoPonte = criaDataEHora();
		
		String dataProcessada = (String) processor.processObjectValue("aniversarioDoPonte", aniversarioDoPonte, jsonConfig);
		
		assertEquals("data como string", DATA_ESPERADA, dataProcessada);
	}
	
	public void testProcessArrayValue() {
		
		Date aniversarioDoPonte = criaDataEHora();
		
		String dataProcessada = (String) processor.processArrayValue(aniversarioDoPonte, jsonConfig);
		
		assertEquals("data como string", DATA_ESPERADA, dataProcessada);
		
	}

	private Date criaDataEHora() {
		try {
			return DateUtils.parseDate(DATA_ESPERADA, new String[] {"dd/MM/yyyy HH:mm:ss"} );
		} catch (ParseException e) {
			throw new RuntimeException("Erro ao converter data.", e);
		}
	}
	
}
