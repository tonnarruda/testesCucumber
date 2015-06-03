package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

public class CompetenciaDWR
{
	private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;
	private SolicitacaoManager solicitacaoManager;

	@SuppressWarnings("rawtypes")
	public Map getByFaixa(Long faixaId, String data)
	{
		return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciaByFaixaSalarial(faixaId, DateUtil.criarDataDiaMesAno(data)), "getId", "getCompetenciaDescricaoNivel", ConfiguracaoNivelCompetencia.class);
	}
	
	@SuppressWarnings("rawtypes")
	public Map getCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, String dataIni, String dataFim)
	{
		if(!"  /  /    ".equals(dataIni) && !"".equals(dataIni) && !"  /  /    ".equals(dataIni) && !"".equals(dataFim) && faixaId != null && faixaId != 0)
			return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciasColaboradorByFaixaSalarialAndPeriodo(faixaId, DateUtil.criarDataDiaMesAno(dataIni), DateUtil.criarDataDiaMesAno(dataFim)), "getId", "getNome", Competencia.class);

		return null;
	}

	public Collection<ConfiguracaoNivelCompetencia> getSugestoesBySolicitacao(Long solicitacaoId) 
	{
		Solicitacao solicitacao = solicitacaoManager.findByIdProjectionAreaFaixaSalarial(solicitacaoId);
		
		Collection<ConfiguracaoNivelCompetencia> configuracoes =  configuracaoNivelCompetenciaManager.findByFaixa(solicitacao.getFaixaSalarial().getId(), new Date());

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
