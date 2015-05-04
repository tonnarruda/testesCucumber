package com.fortes.portalcolaborador.business.operacao;

import com.fortes.portalcolaborador.business.ColaboradorPCManager;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class AtualizarColaboradorComHistorico extends Operacao {

	public URLTransacaoPC getUrlTransacaoPC()
	{
		return URLTransacaoPC.ATUALIZAR_COLABORADOR;
	}
	
	@Override
	@SuppressWarnings({ "deprecation"})
	public void gerarTransacao(String parametros) throws Exception
	{
		JSONObject jsonColaborador = new JSONObject(parametros);	
			
		if(jsonColaborador.get("id") != null)
		{
			ColaboradorPCManager colaboradorPCManager = (ColaboradorPCManager) SpringUtil.getBeanOld("colaboradorPCManager");

			Long colaboradorId = Long.parseLong(((Integer) jsonColaborador.get("id")).toString());
			colaboradorPCManager.enfileirarComHistoricos(getUrlTransacaoPC(), null, colaboradorId);
		}
	}
}
