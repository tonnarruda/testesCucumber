package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings("unchecked")
public class MovimentacaoAC extends LinkedHashMap
{
	private static final long serialVersionUID = -5315907152781743025L;
	
	public static final String AREA = "LOT_CODIGO";
	public static final String ESTABELECIMENTO = "EST_CODIGO";

	public MovimentacaoAC()
	{
		put(AREA, "areaOrganizacional");
		put(ESTABELECIMENTO, "estabelecimento");
	}

	public static String getArea()
	{
		return AREA;
	}

	public static String getEstabelecimento()
	{
		return ESTABELECIMENTO;
	}

}