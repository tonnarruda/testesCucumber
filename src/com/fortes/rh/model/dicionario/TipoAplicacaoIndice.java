package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


public class TipoAplicacaoIndice extends LinkedHashMap<Object, Object>
{
	private static final long serialVersionUID = 2794402836365495345L;
	
	public static final int CARGO = 1;
	public static final int INDICE = 2;
	public static final int VALOR = 3;

	public TipoAplicacaoIndice()
	{
		put(CARGO, "Por cargo");
		put(INDICE, "Por índice");
		put(VALOR, "Por valor");
	}
	
	public static String getDescricao(Integer tipo)
	{
		String retorno = "";
		switch (tipo)
		{
			case CARGO:
				retorno = "Cargo";
				break;
			case INDICE:
				retorno = "Índice";
				break;
			case VALOR:
				retorno = "Valor";
				break;
		}
		
		return retorno;
	}
	
	public static int getValor(String tipo)
	{
		if(tipo.equalsIgnoreCase("C"))
			return CARGO;
		else if(tipo.equalsIgnoreCase("I"))
			return INDICE;
		else
			return VALOR;
	}
	
	public static String getCodigoAC(Integer tipo)
	{
		String retorno = "";
		switch (tipo)
		{
			case CARGO:
				retorno = "C";
				break;
			case INDICE:
				retorno = "I";
				break;
			case VALOR:
				retorno = "V";
				break;
		}
		
		return retorno;
	}

	public static int getIndice()
	{
		return INDICE;
	}

	public static int getValor()
	{
		return VALOR;
	}

	public static int getCargo()
	{
		return CARGO;
	}
}