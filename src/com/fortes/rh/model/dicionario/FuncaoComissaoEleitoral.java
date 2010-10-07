package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class FuncaoComissaoEleitoral extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;
	public static final String PRESIDENTE = "P";
	public static final String VICE_PRESIDENTE = "V";
	public static final String SECRETARIO = "S";

	@SuppressWarnings("unchecked")
	public FuncaoComissaoEleitoral()
	{
		put(SECRETARIO, "Secretário da Mesa Eleitoral");
		put(VICE_PRESIDENTE, "Vice-presidente da Mesa Eleitoral");
		put(PRESIDENTE, "Presidente da Mesa Eleitoral");
	}
}
