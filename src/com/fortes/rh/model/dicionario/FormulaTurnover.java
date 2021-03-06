package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings("serial")
public class FormulaTurnover extends LinkedHashMap<Integer, String>
{
	public static final int ATIVOS_INICIO_MES = 1;
	public static final int MEDIA_ATIVOS_MES = 2;

	public FormulaTurnover()
	{
		put(ATIVOS_INICIO_MES, "[(Admissões + Demissões / 2) / Ativos no final do mês anterior] * 100");
		put(MEDIA_ATIVOS_MES, "{[(Admissões + Demissões) / 2] / [(Ativos no final do mês anterior + Ativos no final do mês atual) / 2]} * 100");
	}
	
	public static String getDescricao(Integer tipo)
	{
		return new FormulaTurnover().get(tipo);
	}
}