package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings({ "serial", "rawtypes" })
public class TipoCertificacao  extends LinkedHashMap
{
	public static final Character VENCIDA_AVENCER = 'T';
	public static final Character VENCIDA = 'V';
	public static final Character AVENCER = 'A';
	
	@SuppressWarnings("unchecked")
	public TipoCertificacao() {
		put(VENCIDA_AVENCER,"Vencidas e a vencer");
		put(VENCIDA, "Vencida");
		put(AVENCER, "A vencer");
	}

	public static String getDescricao(Character tipoCertificacao)
	{
		if (tipoCertificacao == null)
			return "";
		
		return (String) new TipoCertificacao().get((tipoCertificacao));
	}
	
}