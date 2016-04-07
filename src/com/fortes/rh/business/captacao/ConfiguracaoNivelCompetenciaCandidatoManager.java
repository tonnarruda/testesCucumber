package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;

public interface ConfiguracaoNivelCompetenciaCandidatoManager extends GenericManager<ConfiguracaoNivelCompetenciaCandidato>
{
	public ConfiguracaoNivelCompetenciaCandidato findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);

	public void removeByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);
}
