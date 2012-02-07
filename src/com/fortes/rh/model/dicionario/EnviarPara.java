package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class EnviarPara extends LinkedHashMap<Integer, String>
{

	public static final Integer USUARIO = 1;
	public static final Integer GESTOR_AREA = 2;
	public static final Integer AVULSO = 3;
	public EnviarPara()
	{
		put(USUARIO, "Usuário");
		put(GESTOR_AREA, "Gestor da Área");
		put(AVULSO, "Avulso");
	}
	
	public static final String getDescricao(int enviarPara)
	{
		switch (enviarPara) {
		case 1:
			return "Usuário";
		case 2:
			return "Gestor da Área";
		case 3:
			return "Avulso";
		default:
			return "";
		}
	}
}