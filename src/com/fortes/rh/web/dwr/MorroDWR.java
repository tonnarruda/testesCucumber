package com.fortes.rh.web.dwr;

import java.io.File;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import remprot.RPClient;

import com.fortes.rh.business.geral.MorroManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.Autenticador;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.util.Zip;

public class MorroDWR
{
	private MorroManager morroManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	public String enviar(String mensagem, String url) 
	{
//		PostMethod filePost = new PostMethod("http://www.fortesinformatica.com.br/cgi-bin/filebox/send");
		PostMethod filePost = new PostMethod("http://localhost/morro.php");
		
		File printErro = null, logErro = null, zip = null;
		String retorno = "";
		
		try {
			ParametrosDoSistema params = parametrosDoSistemaManager.findById(1L);
			String path = ArquivoUtil.getRhHome() + File.separatorChar;
			
			// monta nome do arquivo (remprot)
			RPClient client = Autenticador.getRemprot(params.getServidorRemprot());
			
			String data = DateUtil.formataDate(new Date(), "yyyyMMdd_HHmm");
			String nomeArquivo = path + "ERRO_RH_RH_" + data + "_" + StringUtil.retiraAcento(client.getCustomerName()).replace(" ", "_");
			
			// geracao do print
			printErro = morroManager.getPrintScreen();
	
			// geracao do arquivo texto	
			logErro = morroManager.getErrorFile(mensagem, url, params.getAppVersao(), client.getCustomerId(), client.getCustomerName());
			
			// cria o zip
			ZipOutputStream zipOS = new Zip().compress(new File[] { printErro, logErro }, nomeArquivo, ".zip", false);
			zipOS.close();
			zip = new File(nomeArquivo + ".zip");
			
			// envia
			int status = morroManager.enviar(filePost, zip, client.getCustomerName());
	
			retorno = (status == HttpStatus.SC_OK) ? "Enviado com sucesso" : "Falha no envio";
			
		} catch (Exception e) {
			e.printStackTrace();
			retorno = "Falha no envio";
		
		} finally {
			filePost.releaseConnection();
			
			if (printErro != null && printErro.exists())
				printErro.delete();
			
			if (logErro != null && logErro.exists())
				logErro.delete();
			
			if (zip != null && zip.exists())
				zip.delete();
		}
		
		return retorno;
	}
	
	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setMorroManager(MorroManager morroManager) {
		this.morroManager = morroManager;
	}
}
