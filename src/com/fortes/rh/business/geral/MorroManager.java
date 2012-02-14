package com.fortes.rh.business.geral;

import java.io.File;



public interface MorroManager
{
	File getPrintScreen() throws Exception;
	File getErrorFile(String mensagem, String classeExcecao, String stackTrace, String url, String versao, String clienteCnpj, String clienteNome, String usuario, String browser) throws Exception;
	String enviar(File zip, String clienteCnpj, String clienteNome, String usuario) throws Exception;
}