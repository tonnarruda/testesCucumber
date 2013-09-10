package com.fortes.rh.business.geral;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.log4j.Logger;

import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.Zip;


@SuppressWarnings("deprecation")
public class FileBoxManagerImpl implements FileBoxManager
{
	private static Logger logger = Logger.getLogger(FileBoxManagerImpl.class);
	private final String PATH = ArquivoUtil.getRhHome() + File.separatorChar;
	
	public void enviar(String fileName, String description, File file) throws Exception
	{
		Date hoje = new Date();
		String nomeZip = PATH + hoje.getTime();
		String msgStatus = "";
		
		File zip = null;
		
		try {
			HttpClient client = new HttpClient( );
	        
	        String weblintURL = "http://www.fortesinformatica.com.br/cgi-bin/filebox/send";
	        
	        MultipartPostMethod method = new MultipartPostMethod( weblintURL );
	        
			ZipOutputStream zipOS = new Zip().compress(new File[] { file }, nomeZip, ".zip", false);
			zipOS.close();
			zip = new File(nomeZip + ".zip");
	        
	        method.addParameter("FileName", fileName );
	        method.addParameter("Description", description );
	        method.addParameter("Att", "suporte.rh@grupofortes.com.br" );
	        method.addParameter("ReplyTo", "" );
	        method.addParameter("File", fileName, zip);
	        
	        int status = client.executeMethod( method );
	        String response = method.getResponseBodyAsString( );
	        method.releaseConnection();
	        msgStatus = (status == HttpStatus.SC_OK) ? response : "Falha no envio:\n" + status + " - " + HttpStatus.getStatusText(status);
		
			logger.info("Msg de retorno do morro: " + msgStatus);
		} finally {
			if (zip != null && zip.exists())
				zip.delete();
		}
	}

}