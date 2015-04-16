package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.business.ColaboradorPCManager;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class InserirColaborador extends Operacao {

	public URLTransacaoPC getUrlTransacaoPC()
	{
		return URLTransacaoPC.COLABORADOR_ATUALIZAR;
	}
	
	@Override
	@SuppressWarnings({ "deprecation"})
	public void gerarTransacao(String parametros) throws Exception
	{
		JSONObject j = new JSONObject(parametros);	
			
		if(j.get("id") != null)
		{
			Long colaboradorId = Long.parseLong(((Integer) j.get("id")).toString());
			
			ColaboradorPCManager colaboradorPCManager = (ColaboradorPCManager) SpringUtil.getBeanOld("colaboradorPCManager");
			colaboradorPCManager.enfileirarColaboradorPC(colaboradorId);
		}
	}
}
