package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class MotivoExtintorManutencao extends LinkedHashMap
{

	private static final long serialVersionUID = 5172729939417701468L;

	public static final String PRAZO_RECARGA = "1";
	public static final String PRAZO_HIDROSTATICO = "2";
	public static final String USADO_TREINAMENTO = "3";
	public static final String USADO_INCENDIO = "4";
	public static final String OUTRO = "0";

	@SuppressWarnings("unchecked")
	public MotivoExtintorManutencao()
	{
		put(PRAZO_RECARGA, "Prazo de recarga vencido");
		put(PRAZO_HIDROSTATICO, "Prazo do teste hidrostático vencido");
		put(USADO_TREINAMENTO, "Usado em treinamento");
		put(USADO_INCENDIO, "Usado em incêndio");
		put(OUTRO, "Outros (especifique abaixo)");
	}
}