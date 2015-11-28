package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SituacaoSolicitacaoEpi extends LinkedHashMap<String, String>
{
	private static final long serialVersionUID = 8232268516382621533L;

	public static final String TODAS = "T"; 
	public static final String ABERTA = "A"; 
	public static final String ENTREGUE = "E"; 
	public static final String ENTREGUE_PARCIALMENTE = "P";
	public static final String DEVOLVIDO = "D"; 
	public static final String DEVOLVIDO_PARCIALMENTE = "DP";
	public static final String SEM_DEVOLUCAO = "S";
	
	SituacaoSolicitacaoEpi()
	{
		put("T", "Todas");
		put("A", "Aberta");
		put("E", "Entregue");
		put("P", "Entregue Parcialmente");
		put("D", "Devolvido");
		put("DP", "Devolvido Parcialmente");
		put("S", "Sem Devolução");
	}
	
	public static final String getSituacaoDescricaoEntrega(int qtdEntregue, int qtdSolicitado)
	{
		if (qtdEntregue >= qtdSolicitado)
			return getDescricao(ENTREGUE);
		else if (qtdEntregue > 0)
			return getDescricao(ENTREGUE_PARCIALMENTE);
		else
			return getDescricao(ABERTA);
	}

	public static final String getSituacaoEntrega(int qtdEntregue, int qtdSolicitado)
	{
		if (qtdEntregue >= qtdSolicitado)
			return ENTREGUE;
		else if (qtdEntregue > 0)
			return ENTREGUE_PARCIALMENTE;
		else
			return ABERTA;
	}

	public static final String getSituacaoDescricaoDevolucao(int qtdDevolvido, int qtdEntregue)
	{
		if (qtdDevolvido == qtdEntregue)
			return getDescricao(DEVOLVIDO);
		else if(qtdDevolvido > 0)
			return getDescricao(DEVOLVIDO_PARCIALMENTE);
		else return getDescricao(SEM_DEVOLUCAO);
	}

	public static final String getSituacaoDevolucao(int qtdDevovildo, int qtdEntregue)
	{
		if (qtdDevovildo == qtdEntregue)
			return DEVOLVIDO;
		else if(qtdDevovildo > 0)
			return DEVOLVIDO_PARCIALMENTE;
		else return SEM_DEVOLUCAO;
	}
	
	public static String getDescricao(String situacaoSolicitacaoEpi) 
	{
		SituacaoSolicitacaoEpi situacao = new SituacaoSolicitacaoEpi();
		return situacao.get(situacaoSolicitacaoEpi);
	}
}