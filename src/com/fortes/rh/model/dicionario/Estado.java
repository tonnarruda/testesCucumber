package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

/**
 * 
 * @author Moésio Medeiros
 * Data: 25 jul 2006
 * Utilizado para popular 'select' em formulario com displaytag 
 *
 */
public class Estado extends LinkedHashMap
{
	@SuppressWarnings("unchecked")
	public Estado()
	{
	    put("AC", "Acre");
	    put("AL", "Alagoas");
	    put("AP", "Amapá");
	    put("AM", "Amazonas");
	    put("BA", "Bahia");
	    put("CE", "Ceará");
	    put("DF", "Distrito Federal");
	    put("ES", "Espírito Santo");
	    put("GO", "Goiás");
	    put("MA", "Maranhão");
	    put("MT", "Mato Grosso");
	    put("MS", "Mato Grosso do Sul");
	    put("MG", "Minas Gerais");
	    put("PA", "Pará");
	    put("PB", "Paraíba");
	    put("PR", "Paraná");
	    put("PE", "Pernambuco");
	    put("PI", "Piauí");
	    put("RJ", "Rio de Janeiro");
	    put("RN", "Rio Grande do Norte");
	    put("RS", "Rio Grande do Sul");
	    put("RO", "Rondônia");
	    put("RR", "Roraima");
	    put("SP", "São Paulo");
	    put("SC", "Santa Catarina");
	    put("SE", "Sergipe");
	    put("TO", "Tocantins");
	}
}