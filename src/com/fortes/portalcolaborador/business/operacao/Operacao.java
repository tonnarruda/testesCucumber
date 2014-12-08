package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.business.TransacaoPCManager;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.util.SpringUtil;

public abstract class Operacao {

	TransacaoPCManager transacaoPCManager;
	
	public void gerarTransacao(String parametros){
		transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
		
		transacaoPCManager.enfileirar(getUrlTransacaoPC(), parametros);
	}
	
	public abstract URLTransacaoPC getUrlTransacaoPC();
	
}
