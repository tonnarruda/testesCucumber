package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class OrgaoDeClasse  extends LinkedHashMap<Long, String>{
	public static final Long CRM = 1L;
	public static final Long CRO = 2L;
	public static final Long RMS = 3L;
	
	public OrgaoDeClasse()
	{
		put(CRM, "Conselho Regional de Medicina (CRM)");
		put(CRO, "Conselho Regional de Odontologia (CRO)");
		put(RMS, "Registro do Ministério da Saúde (RMS)");
	}
}
