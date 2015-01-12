package com.fortes.portalcolaborador.business.operacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fortes.portalcolaborador.business.TransacaoPCManager;
import com.fortes.portalcolaborador.model.ColaboradorPC;
import com.fortes.portalcolaborador.model.HistoricoColaboradorPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.util.SpringUtil;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class InserirColaborador extends Operacao {

	private HistoricoColaboradorManager historicoColaboradorManager;
	
	public URLTransacaoPC getUrlTransacaoPC()
	{
		return URLTransacaoPC.COLABORADOR_ATUALIZAR;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	@Override
	public void gerarTransacao(String parametros) throws Exception
	{
		JSONObject j = new JSONObject(parametros);	
			
		if(j.get("id") != null)
		{
			Long colaboradorId = Long.parseLong(((Integer) j.get("id")).toString());
			
			transacaoPCManager = (TransacaoPCManager) SpringUtil.getBeanOld("transacaoPCManager");
			historicoColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBeanOld("historicoColaboradorManager");
			
			List<HistoricoColaborador> historicos = new ArrayList(historicoColaboradorManager.findByColaboradorIdWithProjectionPC(colaboradorId));
		
			Collection<HistoricoColaborador> historicosMontados = historicoColaboradorManager.montaSituacaoHistoricoColaborador(historicos);
			
			HistoricoColaborador historico = historicosMontados.iterator().next();
			ColaboradorPC colaboradorPC =  new ColaboradorPC(historico.getColaborador());;
			colaboradorPC.setHistoricosPc(new ArrayList<HistoricoColaboradorPC>());
			colaboradorPC.getHistoricosPc().add(new HistoricoColaboradorPC(historico));
			
			transacaoPCManager.enfileirar(URLTransacaoPC.COLABORADOR_ATUALIZAR, colaboradorPC.toJson());
		}
	}
}
