package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class StatusSolicitacao extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 984000509902692769L;

	public static final char TODAS = 'T';
	public static final char ABERTA = 'A';
	public static final char ENCERRADA = 'E';

	public StatusSolicitacao()
	{
		put(TODAS, "Todas as Solicitações");
		put(ABERTA, "Solicitações abertas");
		put(ENCERRADA, "Solicitações encerradas");
	}
	
	public static String getDescricao(Character statusSolicitacao)
	{
		StatusSolicitacao staSolicitacao = new StatusSolicitacao();
		return staSolicitacao.get(statusSolicitacao);
	}
}
