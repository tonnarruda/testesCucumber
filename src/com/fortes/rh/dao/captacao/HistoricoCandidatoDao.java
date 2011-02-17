package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;

public interface HistoricoCandidatoDao extends GenericDao<HistoricoCandidato>
{
	Collection<HistoricoCandidato> findByCandidato(Candidato candidato);

	Collection<HistoricoCandidato> findByCandidato(Collection<CandidatoSolicitacao> candidatos);

	Collection<HistoricoCandidato> findList(CandidatoSolicitacao candidatoSolicitacao);

	Collection<HistoricoCandidato> findByPeriodo(Map parametros);

	//public Collection<HistoricoCandidato> findHistoricoByEtapaSeletiva(Long idSolicitacao, Long idEtapaSeletiva, Date inicio, Date fim);

	HistoricoCandidato findByIdProjection(Long historicoId);

	Collection<HistoricoCandidato> findQtdParticipantes(String ano, Long cargoId, Long[] etapaIds);

	String[] findResponsaveis();
}