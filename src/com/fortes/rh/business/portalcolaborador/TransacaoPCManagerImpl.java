package com.fortes.rh.business.portalcolaborador;

import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.portalcolaborador.TransacaoPCDao;
import com.fortes.rh.model.dicionario.URLTransacaoPC;
import com.fortes.rh.model.portalcolaborador.TransacaoPC;
import com.fortes.rh.util.CryptUtil;
import com.google.gson.Gson;

public class TransacaoPCManagerImpl extends GenericManagerImpl<TransacaoPC, TransacaoPCDao> implements TransacaoPCManager 
{
	public void enfileirar(Object objeto, Class<?> classe, URLTransacaoPC urlTransacaoPC) 
	{
		try {
			TransacaoPC transacaoPC = new TransacaoPC();
			transacaoPC.setCodigoUrl(urlTransacaoPC.getId());
			transacaoPC.setData(new Date());
			
			Gson gson = new Gson();
			String json = gson.toJson(classe.cast(objeto));
			
			transacaoPC.setJson(CryptUtil.encrypt(json, "0123456789012345"));
			save(transacaoPC);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
