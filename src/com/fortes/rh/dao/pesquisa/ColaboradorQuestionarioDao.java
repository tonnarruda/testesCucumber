package com.fortes.rh.dao.pesquisa;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;

public interface ColaboradorQuestionarioDao extends GenericDao<ColaboradorQuestionario>
{
	Collection<ColaboradorQuestionario> findByQuestionario(Long questionarioOrAvaliacaoId);

	ColaboradorQuestionario findByQuestionario(Long questionarioId, Long colaboradorId, Long turmaId);

	ColaboradorQuestionario findColaboradorComEntrevistaDeDesligamento(Long colaboradorId);

	void removeByColaboradorETurma(Long colaboradorId, Long turmaId);

	Collection<ColaboradorQuestionario> findRespondidasByColaboradorETurma(Long colaboradorId, Long turmaId, Long empresaId);

	Collection<ColaboradorQuestionario> findFichasMedicas(Character vinculo, Date dataIni, Date dataFim, String nomeBusca, String cpfBusca, String matriculaBusca);

	ColaboradorQuestionario findByQuestionarioCandidato(Long id, Long candidatoId);

	Collection<ColaboradorQuestionario> findFichasMedicas();

	ColaboradorQuestionario findByIdProjection(Long id);

	ColaboradorQuestionario findByIdColaboradorCandidato(Long id);

	Collection<ColaboradorQuestionario> findAvaliacaoByColaborador(Long colaboradorId, boolean somenteAvaliacaoDesempenho);

	Collection<ColaboradorQuestionario> findColaboradorHistoricoByQuestionario(Long questionarioId, Boolean respondida, Long empresaId);

	Collection<ColaboradorQuestionario> findByColaboradorAndAvaliacaoDesempenho(Long colaboradorParticipanteId, Long avaliacaoDesempenhoId, boolean isAvaliado);

	Integer getCountParticipantesAssociados(Long avaliacaoDesempenhoId);

	Collection<ColaboradorQuestionario> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Boolean respondida);
	
	Collection<ColaboradorQuestionario> findAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId, Boolean respondida, boolean considerarPeriodoAvalDesempenho);

	Collection<ColaboradorQuestionario> findRespondidasByAvaliacaoDesempenho(Long[] participanteIds, Long avaliacaoDesempenhoId, boolean isAvaliado);

	void removeAssociadosSemResposta(Long avaliacaoDesempenhoId);
	
	void removeParticipantesSemAssociacao(Long avaliacaoDesempenhoId);

	void removeByParticipante(Long avaliacaoDesempenhoId, Long[] participanteIds, boolean isAvaliado);

	Collection<ColaboradorQuestionario> getPerformance(Collection<Long> avaliados, Long avaliacaoDesempenhoId);

	Collection<ColaboradorQuestionario> findBySolicitacaoRespondidas(Long solicitacaoId);

	Collection<Colaborador> findRespondidasBySolicitacao(Long solicitacaoid, Long avaliacaoId);
	
	public Collection<ColaboradorQuestionario> findByQuestionarioEmpresaRespondida(Long questionarioOrAvaliacaoId, Boolean respondida, Collection<Long> estabelecimentoIds, Long empresaId);

	Integer countByQuestionarioRespondido(Long questionarioId);

	void excluirColaboradorQuestionarioByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);

	Collection<ColaboradorQuestionario> findByColaborador(Long colaboradorId);

	Double getMediaPeformance(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao);

	Integer getQtdAvaliadores(Long avaliacaoDesempenhoId, Long avaliadoId, boolean desconsiderarAutoAvaliacao);

	ColaboradorQuestionario findByColaboradorAvaliacao(Long colaboradorId, Long avaliacaoId);

	Collection<ColaboradorQuestionario> findQuestionarioByTurmaLiberadaPorUsuario(Long usuarioId);

	void removeByCandidato(Long candidatoId);
	
	public ColaboradorQuestionario findColaborador(Long colaboradorId, Long questionarioId, Long turmaId);

	void deleteRespostaAvaliacaoDesempenho(Long colaboradorQuestionarioId);

	Collection<ColaboradorQuestionario> findTodos();

	void updatePerformance(Long colaboradorQuestionarioId, double performance);
	
	ColaboradorQuestionario findByColaboradorAvaliacaoCurso(Long colaboradorId, Long avaliacaoCursoId, Long turmaId);

	Collection<ColaboradorQuestionario> findForRankingPerformanceAvaliacaoCurso(Long[] cursosIds, Long[] turmasIds, Long[] avaliacaoCursosIds);
}