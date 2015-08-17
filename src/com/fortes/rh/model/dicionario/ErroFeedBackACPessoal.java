package com.fortes.rh.model.dicionario;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ErroFeedBackACPessoal extends LinkedHashMap<Object, Object>
{
	private static final long serialVersionUID = -2212584961699812229L;

	public static final String AREAORGANIZACIONAL_NIVEL_EXCEDIDO = "000001";
	
	private static Map<String, String> mensagens = new HashMap<String, String>(); 

	static{
		mensagens.put(AREAORGANIZACIONAL_NIVEL_EXCEDIDO, "Esta área organizacional não poderá ser cadastrada contendo a área mãe selecionada, pois o nível de hierarquia será excedido de acordo com as configurações de mascara da lotação no Fortes Pessoal.");
	}
	
	public static String getMensagem(String tipo)
	{
		return mensagens.get(tipo).toString();
	}
}