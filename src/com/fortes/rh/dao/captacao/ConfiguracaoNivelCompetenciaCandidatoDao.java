package com.fortes.rh.dao.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;

public interface ConfiguracaoNivelCompetenciaCandidatoDao extends GenericDao<ConfiguracaoNivelCompetenciaCandidato> 
{
	public ConfiguracaoNivelCompetenciaCandidato findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);

	public void removeByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);
	
}
