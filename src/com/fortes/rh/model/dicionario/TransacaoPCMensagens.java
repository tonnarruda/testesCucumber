package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TransacaoPCMensagens extends LinkedHashMap<Integer, String>
{
	public static final int OK = 200;
	public static final int NAOAUTORIZADO = 401;
	public static final int ERRO = 0;

	public TransacaoPCMensagens()
	{
		put(OK, "Conexão realizada com sucesso.");
		put(NAOAUTORIZADO, "Conexão não autorizada com Portal do Colaborador. Verifique se o token e chave estão corretos na configuração do sistema.");
		put(ERRO, "Ocorreu um erro de conexão. Não foi possível se conectar com o Portal do Colaborador.");
	}
	
	public static String getDescricao(int tipo)
	{
		String retorno = new TransacaoPCMensagens().get(tipo); 
		
		if(retorno == null)
			return new TransacaoPCMensagens().get(ERRO);
					
		return retorno;
	}
}
