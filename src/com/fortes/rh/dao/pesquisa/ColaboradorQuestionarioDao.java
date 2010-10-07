package com.fortes.rh.dao.pesquisa;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public interface ColaboradorQuestionarioDao extends GenericDao<ColaboradorQuestionario>
{
	Collection<ColaboradorQuestionario> findByQuestionario(Long questionarioOrAvaliacaoId);

	ColaboradorQuestionario findByQuestionario(Long questionarioId, Long colaboradorId, Long turmaId);

	ColaboradorQuestionario findColaboradorComEntrevistaDeDesligamento(Long colaboradorId);

	void removeByColaboradorETurma(Long colaboradorId, Long turmaId);

	Collection<ColaboradorQuestionario> findRespondidasByColaboradorETurma(Long colaboradorId, Long turmaId);

	Collection<ColaboradorQuestionario> findFichasMedicas(Character vinculo, Date dataIni, Date dataFim, String nomeBusca, String cpfBusca, String matriculaBusca);

	ColaboradorQuestionario findByQuestionarioCandidato(Long id, Long candidatoId);

	Collection<ColaboradorQuestionario> findFichasMedicas();

	ColaboradorQuestionario findByIdProjection(Long id);

	ColaboradorQuestionario findByIdColaboradorCandidato(Long id);

	Collection<ColaboradorQuestionario> findAvaliacaoByColaborador(Long colaboradorId);

	Collection<ColaboradorQuestionario> findColaboradorHistoricoByQuestionario(Long questionarioId);

	Collection<ColaboradorQuestionario> findByColaboradorAndAvaliacaoDesempenho(Long colaboradorParticipanteId, Long avaliacaoDesempenhoId, boolean isAvaliado);

	Integer getCountParticipantesAssociados(Long avaliacaoDesempenhoId);

	Collection<ColaboradorQuestionario> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Boolean respondida);
	
	Collection<ColaboradorQuestionario> findAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId, Boolean respondida);

	Collection<ColaboradorQuestionario> findRespondidasByAvaliacaoDesempenho(Long[] participanteIds, Long avaliacaoDesempenhoId, boolean isAvaliado);

	void removeAssociadosSemResposta(Long avaliacaoDesempenhoId);
	
	void removeParticipantesSemAssociacao(Long avaliacaoDesempenhoId);

	void removeByParticipante(Long avaliacaoDesempenhoId, Long[] participanteIds, boolean isAvaliado);

	Collection<ColaboradorQuestionario> getPerformance(Collection<Long> avaliados, Long avaliacaoDesempenhoId);
}