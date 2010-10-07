package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoMembroComissao extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;
	public static final String ELEITO = "E";
	public static final String INDICADO_EMPRESA = "I";
	public static final String INDICADO_CIPA = "C";

	private static TipoMembroComissao tipoMembroComissao;

	public static TipoMembroComissao getInstance()
	{
		if (tipoMembroComissao == null)
			tipoMembroComissao = new TipoMembroComissao();

		return tipoMembroComissao;
	}

	@SuppressWarnings("unchecked")
	private TipoMembroComissao()
	{
		put(ELEITO, "Eleito");
		put(INDICADO_EMPRESA, "Indicado pela Empresa");
		put(INDICADO_CIPA, "Indicado pela CIPA");
	}
}
