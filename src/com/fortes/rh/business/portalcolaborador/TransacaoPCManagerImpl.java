package com.fortes.rh.business.portalcolaborador;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.portalcolaborador.TransacaoPCDao;
import com.fortes.rh.model.dicionario.URLTransacaoPC;
import com.fortes.rh.model.portalcolaborador.TransacaoPC;
import com.fortes.rh.util.CryptUtil;
import com.fortes.rh.util.SpringUtil;
import com.google.gson.JsonObject;

public class TransacaoPCManagerImpl extends GenericManagerImpl<TransacaoPC, TransacaoPCDao> implements TransacaoPCManager 
{
	private final String URL_PORTAL_COLABORADOR = "http://0.0.0.0:3000";
	
	private TransacaoPCManager transacaoPCManager;
	
	public void enfileirar(Object objeto, Class<?> classe, URLTransacaoPC urlTransacaoPC) 
	{
		try {
			TransacaoPC transacaoPC = new TransacaoPC();
			transacaoPC.setCodigoUrl(urlTransacaoPC.getId());
			transacaoPC.setData(new Date());
			
			String json = classe.cast(objeto).toString();
			
			transacaoPC.setJson(CryptUtil.encrypt(json, "0123456789012345"));
			save(transacaoPC);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void processarFila()
	{
		transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
		
		try {
			Collection<TransacaoPC> transacoes = getDao().findAll(new String[] { "data" });
			
			for (TransacaoPC transacaoPC : transacoes) 
				enviar(transacaoPC);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void enviar(TransacaoPC transacaoPC) throws HttpException, IOException 
	{
		URLTransacaoPC urlTransacaoPC = URLTransacaoPC.getById(transacaoPC.getCodigoUrl());
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("json", transacaoPC.getJson());
		
        StringRequestEntity requestEntity = new StringRequestEntity(
        		jsonObj.toString(),
        	    "application/json",
        	    "UTF-8");

    	PostMethod postMethod = new PostMethod(URL_PORTAL_COLABORADOR + urlTransacaoPC.getUrl());
    	postMethod.setRequestEntity(requestEntity);
    	postMethod.addRequestHeader("Authorization", "Token token=c880f108ecc40eb1148fb1c6495986e267aaecd157f138a0039dedcfdb0c2baf");

    	final HttpClient httpClient = new HttpClient();
    	
    	int statusCode = httpClient.executeMethod(postMethod);
    	
    	if (statusCode == 200)
    		transacaoPCManager.remove(transacaoPC.getId());
	}
}
