package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class MeioComunicacao extends LinkedHashMap<Integer, String>
{

	public static final Integer CAIXA_MENSAGEM = 1;
	public static final Integer EMAIL = 2;

	public MeioComunicacao()
	{
		put(CAIXA_MENSAGEM, "Caixa de Mensagem");
		put(EMAIL, "Email");
	}
	
	public static final String getDescricao(int meioComunicacao)
	{
		switch (meioComunicacao) {
		case 1:
			return "Caixa de Mensagem";
		case 2:
			return "Email";
		default:
			return "";
		}
	}
}