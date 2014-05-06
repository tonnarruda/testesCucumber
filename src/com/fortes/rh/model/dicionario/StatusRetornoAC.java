package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings("unchecked")
public class StatusRetornoAC extends LinkedHashMap
{
	private static final long serialVersionUID = -5315907152781743025L;
	
	public static final int CONFIRMADO = 1;
	public static final int AGUARDANDO = 2;
	public static final int CANCELADO = 3;
	public static final int PENDENTE = 4;

	public StatusRetornoAC()
	{
		put(CONFIRMADO, "Confirmado");
		put(AGUARDANDO, "Aguardando Confirmação");
		put(CANCELADO, "Cancelado");
	}

	public static String getDescricao(Integer tipo)
	{
		String retorno = "";
		switch (tipo)
		{
			case CONFIRMADO:
				retorno = "Confirmado";
				break;
			case AGUARDANDO:
				retorno = "Aguardando Confirmação";
				break;
			case CANCELADO :
				retorno = "Cancelado";
				break;
		}

		return retorno;
	}

	public static String getImg(Integer tipo)
	{
		String retorno = "";
		switch (tipo)
		{
			case CONFIRMADO:
				retorno = "flag_green.gif";
				break;
			case AGUARDANDO:
				retorno = "iconWarning.gif";
				break;
			case CANCELADO :
				retorno = "flag_red.gif";
				break;
		}

		return retorno;
	}

	public static int getAguardando()
	{
		return AGUARDANDO;
	}

	public static int getCancelado()
	{
		return CANCELADO;
	}

	public static int getConfirmado()
	{
		return CONFIRMADO;
	}

}