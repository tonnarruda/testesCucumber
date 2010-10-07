package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings({"unchecked"})
public class Deficiencia extends LinkedHashMap
{
	private static final long serialVersionUID = -4297978464057851828L;
	
	public static final char SEM_DEFICIENCIA = '0';//usado no importador
	public static final char FISICA 			= '1';
	public static final char AUDITIVA 		= '2';
	public static final char VISUAL 			= '3';
	public static final char MENTAL 			= '4';
	public static final char MULTIPLA 		= '5';
	public static final char REABILITADO 	= '6';

	public Deficiencia()
	{
		put(SEM_DEFICIENCIA, "Sem Deficiência");
		put(FISICA, "Física");
		put(AUDITIVA, "Auditiva");
		put(VISUAL, "Visual");
		put(MENTAL, "Mental");
		put(MULTIPLA, "Múltipla");
		put(REABILITADO, "Reabilitado");
	}
}