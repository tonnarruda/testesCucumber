package com.fortes.rh.business.geral;

import java.io.File;

import org.apache.commons.httpclient.methods.PostMethod;


public interface MorroManager
{
	File getPrintScreen() throws Exception;
	File getErrorFile(String mensagem, String url, String versao, String clienteCnpj, String clienteNome) throws Exception;
	int enviar(PostMethod filePost, File zip, String clienteNome) throws Exception;
}