package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class Operacao extends LinkedHashMap<Integer, String>
{

	public static final Integer ENCERRAMENTO_SOLICITACAO = 1;

	public Operacao()
	{
		put(ENCERRAMENTO_SOLICITACAO, "Encerramento da solicitação de pessoal");
	}
	
	public static final String getDescricao(int meioComunicacao)
	{
		switch (meioComunicacao) {
		case 1:
			return "Encerramento da solicitação de pessoal";
		default:
			return "";
	
		}
	}
	
}