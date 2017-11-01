package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoLocalAcidente extends LinkedHashMap<Long, String>{
	public static final Long BRASIL = 1L;
	public static final Long EXTERIOR = 2L;
	public static final Long TERCEIROS = 3L;
	public static final Long PUBLICA = 4L;
	public static final Long RURAL = 5L;
	public static final Long EMBARCACAO = 6L;
	public static final Long OUTROS = 9L;
	
	public TipoLocalAcidente()
	{
		put(BRASIL, "Estabelecimento do empregador no Brasil");
		put(EXTERIOR, "Estabelecimento do empregador no Exterior");
		put(TERCEIROS, "Estabelecimento de terceiros onde o empregador presta serviços");
		put(PUBLICA, "Via pública");
		put(RURAL, "Área rural");
		put(EMBARCACAO, "Embarcação");
		put(OUTROS, "Outros");
	}
}
