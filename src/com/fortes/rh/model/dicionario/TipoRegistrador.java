package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings({ "serial", "rawtypes" })
public class TipoRegistrador extends LinkedHashMap{
	
	public static final Long EMPREGADOR = 1L;
	public static final Long COOPERATIVA = 2L;
	public static final Long SINDICATO = 3L;
	public static final Long MAODEOBRA = 4L;
	public static final Long EMPREGADO = 5L;
	public static final Long DEPENDENTE = 6L;
	public static final Long SINDICAL = 7L;
	public static final Long MEDICO = 8L;
	public static final Long AUTORIDADE = 9L;

	@SuppressWarnings("unchecked")
	public TipoRegistrador()
	{
		put(EMPREGADOR,"Empregador");
		put(COOPERATIVA,"Cooperativa");
		put(SINDICATO,"Sindicato de trabalhadores avulsos não portuários");
		put(MAODEOBRA,"Órgão Gestor de Mão de Obra");
		put(EMPREGADO,"Empregado");
		put(DEPENDENTE,"Dependente do empregado");
		put(SINDICAL,"Entidade Sindical competente");
		put(MEDICO,"Médico assistente");
		put(AUTORIDADE,"Autoridade Pública");
	}
	
}
