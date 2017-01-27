package com.fortes.rh.web.dwr;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import remprot.RPClient;

import com.fortes.rh.business.geral.MorroManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.Autenticador;

@Component
@RemoteProxy(name="MorroDWR")
public class MorroDWR
{
	@Autowired
	private MorroManager morroManager;
	@Autowired
	private ParametrosDoSistemaManager parametrosDoSistemaManager;

	@RemoteMethod
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
}
