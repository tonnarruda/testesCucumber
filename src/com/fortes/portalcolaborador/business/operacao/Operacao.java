package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.business.TransacaoPCManager;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.dispatcher.json.JSONException;

public abstract class Operacao {

	TransacaoPCManager transacaoPCManager;
	
	public void gerarTransacao(String parametros) throws Exception{
		transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
		
		transacaoPCManager.enfileirar(getUrlTransacaoPC(), parametros);
	}
	
	public abstract URLTransacaoPC getUrlTransacaoPC();
	
}
