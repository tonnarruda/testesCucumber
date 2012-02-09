package com.fortes.rh.business.geral;

import java.io.File;

import org.apache.commons.httpclient.methods.PostMethod;


public interface MorroManager
{
	File getPrintScreen() throws Exception;
	File getErrorFile(String mensagem, String url, String versao, String clienteCnpj, String clienteNome, String usuario, String browser) throws Exception;
	String enviar(PostMethod filePost, File zip, String clienteCnpj, String clienteNome, String usuario) throws Exception;
}