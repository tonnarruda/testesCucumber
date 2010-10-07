package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoComissaoReuniao extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;
	public static final String EXTRAORDINARIA = "E";
	public static final String ORDINARIA = "O";

	private static TipoComissaoReuniao tipoMembroComissao;

	public static TipoComissaoReuniao getInstance()
	{
		if (tipoMembroComissao == null)
			tipoMembroComissao = new TipoComissaoReuniao();

		return tipoMembroComissao;
	}

	@SuppressWarnings("unchecked")
	private TipoComissaoReuniao()
	{
		put(EXTRAORDINARIA, "Extraordinária");
		put(ORDINARIA, "Ordinária");
	}
}
