package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCandidatoDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;

public class ConfiguracaoNivelCompetenciaCandidatoManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetenciaCandidato, ConfiguracaoNivelCompetenciaCandidatoDao> implements ConfiguracaoNivelCompetenciaCandidatoManager
{
	@TesteAutomatico
	public ConfiguracaoNivelCompetenciaCandidato findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) {
		return getDao().findByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}

	@TesteAutomatico
	public void removeByCandidatoAndSolicitacao(Long candidatoId,Long solicitacaoId) {
		getDao().removeByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}
}
