package com.fortes.rh.business.geral;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.log4j.Logger;

import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.Zip;


@SuppressWarnings("deprecation")
public class MorroManagerImpl implements MorroManager
{
	private static Logger logger = Logger.getLogger(MorroManagerImpl.class);
	private final String PATH = ArquivoUtil.getRhHome() + File.separatorChar;
	
	public void enviar(String mensagem, String classeExcecao, String stackTrace, String url, String browser, String versao, String clienteCnpj, String clienteNome, String usuario) throws Exception
	{
		Date hoje = new Date();
		String nomeZip = PATH + hoje.getTime();
		String msgStatus = "";
		
		File logErro = new File(PATH + "Erro.txt");
		File zip = null;
		
		try {
			FileOutputStream fos = new FileOutputStream(logErro);  
			String textoErro = montaFileErro(mensagem, classeExcecao, stackTrace, url, browser, versao, clienteCnpj, clienteNome, usuario);
			
			fos.write(textoErro.getBytes());  
			fos.close();
			HttpClient client = new HttpClient( );
	        
	        String weblintURL = "http://www.fortesinformatica.com.br/cgi-bin/filebox/send";
	        
	        MultipartPostMethod method = new MultipartPostMethod( weblintURL );
	        String dataFormatada = DateUtil.formataDate(hoje, "yyyyMMdd_HHmm");
	        String fileName = "ERRO_RH_RH_" + dataFormatada + "_" + StringUtil.retiraAcento(clienteNome).replace(" ", "_") + ".zip";
	        
			ZipOutputStream zipOS = new Zip().compress(new File[] { logErro }, nomeZip, ".zip", false);
			zipOS.close();
			zip = new File(nomeZip + ".zip");
	        
	        method.addParameter("FileName", fileName );
	        method.addParameter("Description", clienteCnpj + " " + clienteNome + " - " + usuario );
	        method.addParameter("Att", "suporte.rh@grupofortes.com.br" );
	        method.addParameter("ReplyTo", "" );
	        method.addPart( new FilePart( "File", zip, "multipart/form-data", "ISO-8859-1" ) );
	        
	        int status = client.executeMethod( method );
	        String response = method.getResponseBodyAsString( );
	        method.releaseConnection();
	        msgStatus = (status == HttpStatus.SC_OK) ? response : "Falha no envio:\n" + status + " - " + HttpStatus.getStatusText(status);
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Msg de retorno do morro: " + msgStatus);
		} finally {
			if (logErro != null && logErro.exists())
				logErro.delete();
			if (zip != null && zip.exists())
				zip.delete();
		}
	}

	private String montaFileErro(String mensagem, String classeExcecao, String stackTrace, String url, String browser, String versao, String clienteCnpj, String clienteNome, String usuario) {
		StringBuffer texto = new StringBuffer();
		
		texto.append("Mensagem=" + mensagem + "\n");
		texto.append("URL=" + url + "\n");
		texto.append("Usuario=" + usuario + "\n");
		texto.append("Browser=" + browser + "\n");
		texto.append("Produto=RH" + "\n");
		texto.append("Versao=" + versao + "\n");
		texto.append("LicenciadoCNPJ=" + clienteCnpj + "\n");
		texto.append("LicenciadoNome=" + clienteNome + "\n");
		texto.append("---------------- Servidor ----------------\n");
		texto.append("VersaoJava=" + System.getProperty("java.version", "Não identificado") + "\n");
		texto.append("Especificacao=" + System.getProperty("java.vm.specification.vendor", "Não identificado") + "\n");
		texto.append("SistemaOperacional=" + System.getProperty("os.name", "Não identificado") + "\n");
		texto.append("MemoriaLivre=" + Runtime.getRuntime().freeMemory() + "\n");
		texto.append("MemoriaTotal=" + Runtime.getRuntime().totalMemory() + "\n");
		
		try {
			File systemConf = new File(PATH + "system.conf");
			texto.append("------------------ system.conf ------------------\n");
			texto.append("RH_HOME=" + PATH + "\n");
			texto.append(ArquivoUtil.getContents(systemConf));
			systemConf = null;
		} catch (Exception e) {}
		
		
		texto.append("------------------ Erro ------------------\n");
		texto.append("ClasseExcecao=" + classeExcecao + "\n");
		texto.append("StackTrace=" + stackTrace + "\n");
		
		return texto.toString();
	}
	
}