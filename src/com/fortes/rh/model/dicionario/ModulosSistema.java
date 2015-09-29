package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class ModulosSistema extends LinkedHashMap<String, String>
{
	//1 - R&S, 2 - C&S, 4 - Pesquisas, 8 - T&D, 16 - Aval. Desempenho, 32 - SESMT
	public static final String RES = "1";
	public static final String CES = "2";
	public static final String PESQUISA = "4";
	public static final String TED = "8";
	public static final String AVALDESEMPENHO = "16";
	public static final String SESMT = "32";

	public ModulosSistema()
	{
		put(RES, "R&S");
		put(CES, "C&S");
		put(PESQUISA, "Pesquisa");
		put(TED, "T&D");
		put(AVALDESEMPENHO, "Avaliação de Desempenho");
		put(SESMT, "SESMT");
	}
	
	public static String getDescricao(char tipo)
	{
		return new StatusAprovacaoSolicitacao().get(tipo);
	}
}
