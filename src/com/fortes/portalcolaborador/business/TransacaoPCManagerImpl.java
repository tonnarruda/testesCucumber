package com.fortes.portalcolaborador.business;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.fortes.business.GenericManagerImpl;
import com.fortes.portalcolaborador.dao.TransacaoPCDao;
import com.fortes.portalcolaborador.model.MovimentacaoOperacaoPC;
import com.fortes.portalcolaborador.model.TransacaoPC;
import com.fortes.portalcolaborador.model.dicionario.TransacaoPCMensagens;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.CryptUtil;
import com.fortes.rh.util.SpringUtil;
import com.google.gson.JsonObject;

public class TransacaoPCManagerImpl extends GenericManagerImpl<TransacaoPC, TransacaoPCDao> implements TransacaoPCManager 
{
	private TransacaoPCManager transacaoPCManager;
	private MovimentacaoOperacaoPCManager movimentacaoOperacaoPCManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private static String key = null;
	
	public void enfileirar(URLTransacaoPC urlTransacaoPC, String parametros) 
	{
		try {
			if(key == null)
				key = parametrosDoSistemaManager.findById(1L).getPcKey();
			
			TransacaoPC transacaoPC = new TransacaoPC();
			transacaoPC.setCodigoUrl(urlTransacaoPC.getId());
			transacaoPC.setData(new Date());
			transacaoPC.setJson(CryptUtil.encrypt(parametros, key));
			
			save(transacaoPC);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public String testarConexao() 
	{
		transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
		parametrosDoSistemaManager = (ParametrosDoSistemaManager) SpringUtil.getBeanOld("parametrosDoSistemaManager");
		
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		
		try {
			TransacaoPC transacaoPC = new TransacaoPC();
			transacaoPC.setCodigoUrl(URLTransacaoPC.TESTAR_CONEXAO_PORTAL.getId());
			transacaoPC.setJson(CryptUtil.encrypt("{\"echo\":\"ok\"}", parametrosDoSistema.getPcKey()));
			
			return TransacaoPCMensagens.getDescricao(enviar(transacaoPC, parametrosDoSistema.getPcToken()));
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return TransacaoPCMensagens.getDescricao(TransacaoPCMensagens.ERRO);		
		}
	}
	
	@SuppressWarnings("deprecation")
	public void processarFila()
	{
		try {
			transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
			parametrosDoSistemaManager = (ParametrosDoSistemaManager) SpringUtil.getBeanOld("parametrosDoSistemaManager");
			
			ParametrosDoSistema params = parametrosDoSistemaManager.findById(1L);
		
			Collection<TransacaoPC> transacoes = getDao().findAll(new String[] { "data" });
			
			for (TransacaoPC transacaoPC : transacoes) {
 				int statusCode = enviar(transacaoPC, params.getPcToken());
				
		    	if ( (statusCode >= 200 && statusCode <= 208) || statusCode == 226 )
		    		transacaoPCManager.remove(transacaoPC.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Integer enviar(TransacaoPC transacaoPC, String pcToken) throws HttpException, IOException 
	{
		URLTransacaoPC urlTransacaoPC = URLTransacaoPC.getById(transacaoPC.getCodigoUrl());
		
        HttpMethod method = getMethod(urlTransacaoPC.getMethod());
        method.setURI(new URI(urlTransacaoPC.getUrl(), false));
        method.addRequestHeader("Authorization", "Token token=" + pcToken);
        
        //TODO: Refatorar para o envio de parametros do GET
        if (!urlTransacaoPC.getMethod().equalsIgnoreCase("GET"))
        {
        	JsonObject jsonObj = new JsonObject();
    		jsonObj.addProperty("json", transacaoPC.getJson());
        	
	        StringRequestEntity requestEntity = new StringRequestEntity(
	        		jsonObj.toString(),
	        	    "application/json",
	        	    "UTF-8");
	        
	        ((EntityEnclosingMethod) method).setRequestEntity(requestEntity);
        }
        
    	final HttpClient httpClient = new HttpClient();
    	int statusCode = httpClient.executeMethod(method);
    	
    	return statusCode;
	}
	
	private HttpMethod getMethod(String method)
	{
		if (method.equalsIgnoreCase("PUT"))
			return new PutMethod();
		else if (method.equalsIgnoreCase("POST"))
			return new PostMethod();
		
		return new GetMethod();
	}

	public void processarOperacoes(Collection<MovimentacaoOperacaoPC> movimentacoesOperacaoPC) throws Exception
	{
		for (MovimentacaoOperacaoPC movimentacaoOperacaoPC : movimentacoesOperacaoPC)
		{
			movimentacaoOperacaoPC.getOperacao().gerarTransacao(movimentacaoOperacaoPC.getParametros());
			movimentacaoOperacaoPCManager.remove(movimentacaoOperacaoPC.getId());
		}
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setMovimentacaoOperacaoPCManager(MovimentacaoOperacaoPCManager movimentacaoOperacaoPCManager)
	{
		this.movimentacaoOperacaoPCManager = movimentacaoOperacaoPCManager;
	}
}