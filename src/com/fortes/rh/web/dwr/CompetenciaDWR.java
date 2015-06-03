package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
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
	public Map getCompetenciasColaboradorByFaixaSalarialAndData(Long faixaId, String data)
	{
		if(!"  /  /    ".equals(data) && !"".equals(data) && faixaId != null && faixaId != 0)
			return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciasColaboradorByFaixaSalarialAndPeriodo(faixaId, DateUtil.criarDataDiaMesAno(data), null), "getId", "getNome", Competencia.class);
		
		return new LinkedHashMap();
	}
	
	@SuppressWarnings("rawtypes")
	public Map getCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, String dataIni, String dataFim)
	{
		if(!"  /  /    ".equals(dataIni) && !"".equals(dataIni) && !"  /  /    ".equals(dataFim) && !"".equals(dataFim) && faixaId != null && faixaId != 0)
			return CollectionUtil.convertCollectionToMap(configuracaoNivelCompetenciaManager.findCompetenciasColaboradorByFaixaSalarialAndPeriodo(faixaId, DateUtil.criarDataDiaMesAno(dataIni), DateUtil.criarDataDiaMesAno(dataFim)), "getId", "getNome", Competencia.class);

		return new LinkedHashMap();
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
