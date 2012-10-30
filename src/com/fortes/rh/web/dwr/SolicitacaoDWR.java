package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.util.CollectionUtil;

public class SolicitacaoDWR {

	private SolicitacaoManager solicitacaoManager;

	public Map getSolicitacoes(Long empresaId) 
	{
		Collection<Solicitacao> solicitacaos = solicitacaoManager.findSolicitacaoList(empresaId, false, StatusAprovacaoSolicitacao.APROVADO, false);
		return new CollectionUtil<Solicitacao>().convertCollectionToMap(solicitacaos, "getId", "getDescricaoFormatada");
	}

	public Map<String, String> getObsSolicitacao(Long solicitacaoId) 
	{
		Solicitacao solicitacao = solicitacaoManager.findById(solicitacaoId);
		
		HashMap<String, String> solicitacaoMap = new HashMap<String, String>();
		solicitacaoMap.put("status", String.valueOf(solicitacao.getStatus()));
		solicitacaoMap.put("obs", solicitacao.getObservacaoLiberador());
		
		return solicitacaoMap;  
	}
	
	public Map<Long, String> getByEmpresaEstabelecimentosAreas(Long empresaId, Long[] estabelecimentosIds, Long[] areasIds) 
	{
		Collection<Solicitacao> solicitacoes = solicitacaoManager.findByEmpresaEstabelecimentosAreas(empresaId, estabelecimentosIds, areasIds);
		
		return new CollectionUtil<Solicitacao>().convertCollectionToMap(solicitacoes, "getId", "getDescricaoFormatada");
	}
	
	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
}
