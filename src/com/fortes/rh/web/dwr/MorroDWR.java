package com.fortes.rh.web.dwr;

import org.apache.commons.lang.StringUtils;

import remprot.RPClient;
import uk.ltd.getahead.dwr.WebContextFactory;

import com.fortes.rh.business.geral.MorroManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.Autenticador;

public class MorroDWR
{
	private MorroManager morroManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	public String enviar(String mensagem, String classeExcecao, String stackTrace, String url, String browser) 
	{
		String usuarioLogado = "";
		String idCliente = "00000000000000";
		String nomeCliente = "Cliente sem remprot";
		ParametrosDoSistema params = null;

		try {
			usuarioLogado = SecurityUtil.getNomeUsuarioLogedByDWR(WebContextFactory.get().getHttpServletRequest().getSession());
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			params = parametrosDoSistemaManager.findById(1L);
			RPClient client = Autenticador.getRemprot();
			
			if(client != null && StringUtils.isNotEmpty(client.getCustomerId()))
			{
				idCliente = client.getCustomerId();
				nomeCliente = client.getCustomerName();
			}
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			morroManager.enviar(mensagem, classeExcecao, stackTrace, url, browser, params.getAppVersao(), idCliente, nomeCliente, usuarioLogado);
		} catch (Exception e) {e.printStackTrace();}
		
		return "Enviado com sucesso";
	}
	
	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setMorroManager(MorroManager morroManager) {
		this.morroManager = morroManager;
	}
}
