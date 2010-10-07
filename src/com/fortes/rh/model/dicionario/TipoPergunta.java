package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("unchecked")
public class TipoPergunta extends LinkedHashMap
{
	private static final long serialVersionUID = -415001237145144693L;
	
	public static final int OBJETIVA = 1;
	public static final int SUBJETIVA = 3;
	public static final int NOTA = 4;
	public static final int MULTIPLA_ESCOLHA = 5;


	public TipoPergunta()
	{
		put(SUBJETIVA, "Subjetiva");
		put(NOTA, "Nota");
		put(OBJETIVA, "Objetiva");
		put(MULTIPLA_ESCOLHA, "Múltipla Escolha");
	}

	public int getObjetiva()
	{
		return OBJETIVA;
	}

	public int getSubjetiva()
	{
		return SUBJETIVA;
	}

	public int getNota()
	{
		return NOTA;
	}

	public int getMultiplaEscolha()
	{
		return MULTIPLA_ESCOLHA;
	}
}