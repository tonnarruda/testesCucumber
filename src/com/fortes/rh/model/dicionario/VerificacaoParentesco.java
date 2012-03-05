package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class VerificacaoParentesco extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = -2104282801289268755L;
	
	public static final char DESABILITADA 			= 'N';
	public static final char BUSCA_MESMA_EMPRESA	= 'E';
	public static final char BUSCA_TODAS_AS_EMPRESAS= 'T';

	public VerificacaoParentesco()
	{
		put(DESABILITADA, "Desabilitada");
		put(BUSCA_MESMA_EMPRESA, "Busca na mesma empresa");
		put(BUSCA_TODAS_AS_EMPRESAS, "Busca em todas as empresas");
	}
}