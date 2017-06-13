package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class RegistrosDeSaude extends LinkedHashMap<String, String>
{
	public static final String CRO = "CRO";
	public static final String CRM = "CRM";
	public static final String RMS = "RMS";
	public static final String CFO = "CFO";

	public RegistrosDeSaude()
	{
		put(CRO, "CRO");
		put(CRM, "CRM");
		put(RMS, "RMS");
		put(CFO, "CFO");
	}
	
	public static String getDescricao(char tipo)
	{
		return new RegistrosDeSaude().get(tipo);
	}
}
