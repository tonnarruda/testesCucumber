package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class FuncaoComissao extends LinkedHashMap<String,String>
{
	private static final long serialVersionUID = 1L;
	public static final String PRESIDENTE = "P";
	public static final String VICE_PRESIDENTE = "V";
	public static final String SECRETARIO = "S";
	public static final String MEMBRO_SUPLENTE = "L";
	public static final String MEMBRO_TITULAR = "T";

	private static FuncaoComissao funcaoComissao;

	public static FuncaoComissao getInstance()
	{
		if (funcaoComissao == null)
			funcaoComissao = new FuncaoComissao();

		return funcaoComissao;
	}

	private FuncaoComissao()
	{
		put(SECRETARIO, "Secretário");
		put(MEMBRO_SUPLENTE, "Membro Suplente");
		put(MEMBRO_TITULAR, "Membro Titular");
		put(VICE_PRESIDENTE, "Vice-presidente");
		put(PRESIDENTE, "Presidente");
	}
}
