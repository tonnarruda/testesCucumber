package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class CodigoGFIP extends LinkedHashMap<String, String> 
{
	private static final long serialVersionUID = 3956506865867923881L;
	
	public static final String _00 = "00";
	public static final String _01 = "01";
	public static final String _02 = "02";
	public static final String _03 = "03";
	public static final String _04 = "04";
	public static final String _05 = "05";
	public static final String _06 = "06";
	public static final String _07 = "07";
	public static final String _08 = "08";
	
	private static CodigoGFIP instancia;
	
	public static CodigoGFIP getInstance()
	{
		if (instancia == null)
			instancia = new CodigoGFIP();

		return instancia;
	}

	private CodigoGFIP() {
		put(_00, "00 - Nunca foi exposto a agente nocivo");
		put(_01, "01 - Não exposição a agente nocivo");
		put(_02, "02 - Exposição a agente nocivo (aposentadoria especial aos 15 anos de serviço)");
		put(_03, "03 - Exposição a agente nocivo (aposentadoria especial aos 20 anos de serviço)");
		put(_04, "04 - Exposição a agente nocivo (aposentadoria especial aos 25 anos de serviço)");
		put(_05, "05 - Não exposição a agente nocivo - múltiplos vínculos");
		put(_06, "06 - Exposição a agente nocivo (aposentadoria especial aos 15 anos de serviço) - múltiplos vínculos");
		put(_07, "07 - Exposição a agente nocivo (aposentadoria especial aos 20 anos de serviço) - múltiplos vínculos");
		put(_08, "08 - Exposição a agente nocivo (aposentadoria especial aos 25 anos de serviço) - múltiplos vínculos");
	}
}