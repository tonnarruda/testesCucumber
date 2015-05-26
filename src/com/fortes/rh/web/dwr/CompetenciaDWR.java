package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.util.CollectionUtil;

public class CompetenciaDWR
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private SolicitacaoManager solicitacaoManager;

	@SuppressWarnings("unchecked")
	public Map getByFaixa(Long faixaId)
	{
		return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaId, null), "getId", "getCompetenciaDescricaoNivel", ConfiguracaoNivelCompetencia.class);
	}

	public Collection<ConfiguracaoNivelCompetencia> getSugestoesBySolicitacao(Long solicitacaoId) 
	{
		Solicitacao solicitacao = solicitacaoManager.findByIdProjectionAreaFaixaSalarial(solicitacaoId);
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes =  configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId());

		return configuracoes;
	}
	
	public void setConfiguracaoNivelCompetenciaManager(ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) 
	{
		this.solicitacaoManager = solicitacaoManager;
	}
}
