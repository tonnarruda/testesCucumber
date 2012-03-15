package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SituacaoSolicitacaoEpi extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 8232268516382621533L;

	public static final char TODAS = 'T'; 
	public static final char ABERTA = 'A'; 
	public static final char ENTREGUE = 'E'; 
	public static final char ENTREGUE_PARCIALMENTE = 'P';
	
	SituacaoSolicitacaoEpi()
	{
		put('T', "Todas");
		put('A', "Aberta");
		put('E', "Entregue");
		put('P', "Entregue Parcialmente");
	}
	
	public static final String getSituacao(int qtdEntregue, int qtdSolicitado)
	{
		if (qtdEntregue >= qtdSolicitado)
			return getDescricao(ENTREGUE);
		else if (qtdEntregue > 0)
			return getDescricao(ENTREGUE_PARCIALMENTE);
		else
			return getDescricao(ABERTA);
	}

	public static String getDescricao(char situacaoSolicitacaoEpi) 
	{
		SituacaoSolicitacaoEpi situacao = new SituacaoSolicitacaoEpi();
		return situacao.get(situacaoSolicitacaoEpi);
	}
}
