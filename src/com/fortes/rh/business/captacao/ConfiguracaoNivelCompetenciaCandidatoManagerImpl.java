package com.fortes.rh.business.captacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaCandidatoDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;

@Component
public class ConfiguracaoNivelCompetenciaCandidatoManagerImpl extends GenericManagerImpl<ConfiguracaoNivelCompetenciaCandidato, ConfiguracaoNivelCompetenciaCandidatoDao> implements ConfiguracaoNivelCompetenciaCandidatoManager
{
	@Autowired
	public ConfiguracaoNivelCompetenciaCandidatoManagerImpl(ConfiguracaoNivelCompetenciaCandidatoDao configuracaoNivelCompetenciaCandidatoDao) {
		setDao(configuracaoNivelCompetenciaCandidatoDao);
	}
	
	@TesteAutomatico
	public ConfiguracaoNivelCompetenciaCandidato findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId) {
		return getDao().findByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}

	@TesteAutomatico
	public void removeByCandidatoAndSolicitacao(Long candidatoId,Long solicitacaoId) {
		getDao().removeByCandidatoAndSolicitacao(candidatoId, solicitacaoId);
	}
	
	@TesteAutomatico
	public void removeByCandidato(Long candidatoId) {
		getDao().removeByCandidato(candidatoId);
	}
}
