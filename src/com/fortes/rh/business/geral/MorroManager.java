package com.fortes.rh.business.geral;




public interface MorroManager
{
	void enviar(String mensagem, String classeExcecao, String stackTrace, String url, String browser, String appVersao, String idCliente, String nomeCliente, String usuario) throws Exception;
}