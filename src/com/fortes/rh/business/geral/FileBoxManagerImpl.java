package com.fortes.rh.business.geral;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("deprecation")
public class FileBoxManagerImpl implements FileBoxManager
{
	private static final String FILEBOX_URL = "http://www.fortesinformatica.com.br/cgi-bin/filebox/send";
	private static Logger logger = Logger.getLogger(FileBoxManagerImpl.class);
	
	public void enviar(String fileName, String description, File file) throws Exception
	{
		HttpClient client = new HttpClient( );
        
        MultipartPostMethod method = new MultipartPostMethod( FILEBOX_URL );
        
        method.addParameter("FileName", fileName );
        method.addParameter("Description", description );
        method.addParameter("Att", "suporte.rh@grupofortes.com.br" );
        method.addParameter("ReplyTo", "" );
        method.addParameter("File", fileName, file);
        
        int status = client.executeMethod( method );
        String response = method.getResponseBodyAsString( );
        method.releaseConnection();
        
        String msgStatus = (status == HttpStatus.SC_OK) ? response : "Falha no envio:\n" + status + " - " + HttpStatus.getStatusText(status);
	
		logger.info("Msg de retorno do morro: " + msgStatus);
	}
}