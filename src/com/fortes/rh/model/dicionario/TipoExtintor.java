package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoExtintor extends LinkedHashMap<String, String>
{
	private static final long serialVersionUID = 8868252917200606555L;
	
	public static final String AGUA_GAS = "1";
	public static final String AGUA_PRESS = "2";
	public static final String PO_QUIMICO_SECO = "3";
	public static final String PO_QUIMICO_SECO_ABC = "4";
	public static final String CO2 = "5";

	public TipoExtintor()
	{
		put(AGUA_GAS, "AG - Água Gás (Classe A)");
		put(AGUA_PRESS, "AP - Água Pressurizada (Classe A)");
		put(PO_QUIMICO_SECO, "PQS - Pó Químico Seco (Classes B e C)");
		put(PO_QUIMICO_SECO_ABC, "ABC/PQS - Pó Químico Seco (Classes A,B e C)");
		put(CO2, "CO2 - Dióxido de Carbono (Classes B e C)");
	}
}