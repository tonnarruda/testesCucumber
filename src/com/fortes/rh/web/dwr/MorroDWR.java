package com.fortes.rh.web.dwr;

import java.io.File;
import java.util.Date;
import java.util.zip.ZipOutputStream;

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

	public String enviar(String mensagem, String classeExcecao, String stackTrace, String url, String usuario, String browser) 
	{
		File logErro = null, zip = null;
		
		try {
			ParametrosDoSistema params = parametrosDoSistemaManager.findById(1L);
			String path = ArquivoUtil.getRhHome() + File.separatorChar;
			
			// monta nome do arquivo (remprot)
			RPClient client = Autenticador.getRemprot(params.getServidorRemprot());
			
			String data = DateUtil.formataDate(new Date(), "yyyyMMdd_HHmm");
			String nomeArquivo = path + "ERRO_RH_RH_" + data + "_" + StringUtil.retiraAcento(client.getCustomerName()).replace(" ", "_");
			
			// geracao do arquivo texto	
			logErro = morroManager.getErrorFile(mensagem, classeExcecao, stackTrace, url, params.getAppVersao(), client.getCustomerId(), client.getCustomerName(), usuario, browser);
			
			// cria o zip
			ZipOutputStream zipOS = new Zip().compress(new File[] { logErro }, nomeArquivo, ".zip", false);
			zipOS.close();
			zip = new File(nomeArquivo + ".zip");
			
			// envia
			morroManager.enviar(zip, client.getCustomerId(), client.getCustomerName(), usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			if (logErro != null && logErro.exists())
				logErro.delete();
			
			if (zip != null && zip.exists())
				zip.delete();
		}
		
		return "Enviado com sucesso";
	}
	
	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setMorroManager(MorroManager morroManager) {
		this.morroManager = morroManager;
	}
}
