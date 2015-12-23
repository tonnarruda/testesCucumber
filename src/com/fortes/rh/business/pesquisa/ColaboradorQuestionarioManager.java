package com.fortes.rh.business.pesquisa;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.security.spring.aop.callback.ColaboradorQuestionarioAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface ColaboradorQuestionarioManager extends GenericManager<ColaboradorQuestionario>
{
	@Audita(operacao="Remoção de Resposta", auditor=ColaboradorQuestionarioAuditorCallbackImpl.class)
	void deleteRespostaAvaliacaoDesempenho(Long colaboradorQuestionarioId);
	Collection<ColaboradorQuestionario> findByQuestionario(Long questionarioId);
	ColaboradorQuestionario findByQuestionario(Long questionarioId, Long colaboradorId, Long turmaId);
	Collection<Colaborador> selecionaColaboradores(Collection<Colaborador> colaboradores, char qtdPercentual, double percentual, int quantidade);
	Collection<Colaborador> selecionaColaboradoresPorParte(Collection<Colaborador> colaboradores, char filtrarPor, Collection<Long> areasIds, Collection<Long> cargosIds, char qtdPercentual, double percentual, int quantidade);
	void save(Questionario questionario, Long[] colaboradoresId, Turma turma) throws Exception;
	ColaboradorQuestionario findColaboradorComEntrevistaDeDesligamento(Long colaboradorId);
	void removeByColaboradorETurma(Long colaboradorId, Long turmaId);
	Collection<ColaboradorQuestionario> findRespondidasByColaboradorETurma(Long colaboradorId, Long turmaId, Long empresaId);
	Collection<ColaboradorQuestionario> findFichasMedicas(Character vinculo, Date dataIni, Date dataFim, String nomeBusca, String cpfBusca, String matriculaBusca);
	ColaboradorQuestionario findByQuestionarioCandidato(Long id, Long candidatoId);
	ColaboradorQuestionario findByIdProjection(Long id);
	ColaboradorQuestionario findByIdColaboradorCandidato(Long colaboradorQuestionarioId);
	Collection<ColaboradorResposta> populaQuestionario(Avaliacao avaliacao);
	Collection<ColaboradorQuestionario> findAvaliacaoByColaborador(Long colaboradorId);
	public Collection<ColaboradorQuestionario> findAvaliacaoDesempenhoByColaborador(Long colaboradorId);
	Collection<ColaboradorQuestionario> findColaboradorHistoricoByQuestionario(Long questionarioId, Boolean respondida, Long empresaId);
	void save(AvaliacaoDesempenho avaliacaoDesempenho, Long[] colaboradorIds, boolean isAvaliado);
	void remove(Long[] colaboradorParticipanteId, Long avaliacaoDesempenhoId, boolean isAvaliado) throws Exception;
	void removeNotIn(Collection<ColaboradorQuestionario> colaboradorQuestionarios, Long avaliacaoDesempenhoId) throws Exception;
	boolean verifyTemParticipantesAssociados(Long avaliacaoDesempenhoId);
	Collection<ColaboradorQuestionario> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId, Boolean respondida);
	Collection<ColaboradorQuestionario> findByColaboradorAndAvaliacaoDesempenho(Long colaboradorId, Long avaliacaoDesempenhoId, boolean isAvaliado, boolean desconsiderarAutoAvaliacao);
	Collection<ColaboradorQuestionario> associarParticipantes(AvaliacaoDesempenho avaliacaoDesempenho, Collection<Colaborador> avaliados, Collection<Colaborador> avaliadores) throws Exception;
	Collection<ColaboradorQuestionario> desassociarParticipantes(AvaliacaoDesempenho avaliacaoDesempenho) throws Exception;
	void clonarParticipantes(AvaliacaoDesempenho avaliacaoDesempenho, AvaliacaoDesempenho avaliacaoDesempenhoClone) throws Exception;
	Collection<ColaboradorQuestionario> findAvaliadosByAvaliador(Long avaliacaoDesempenhoId, Long avaliadorId, char respondida, boolean considerarPeriodoAvalDesempenho, boolean considerarLiberada, Boolean considerarRespostasParciais);
	Collection<ColaboradorQuestionario> getPerformance(Collection<Long> avaliados, Long avaliacaoDesempenhoId);
	Collection<ColaboradorQuestionario> findBySolicitacaoRespondidas(Long solicitacaoId);
	Collection<Colaborador> findRespondidasBySolicitacao(Long solicitacaoid, Long avaliacaoId);
	public Collection<ColaboradorQuestionario> findByQuestionarioEmpresaRespondida(Long questionarioId, Boolean respondida, Collection<Long> estabelecimentoIds, Long empresaId );
	Integer countByQuestionarioRespondido(Long questionarioId);
	void excluirColaboradorQuestionarioByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	Collection<ColaboradorQuestionario> findByColaborador(Long colaboradorId);
	Double getMediaPeformance(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao);
	Integer getQtdAvaliadores(Long avaliacaoDesempenhoId, Long avaliadoId, boolean desconsiderarAutoAvaliacao);
	ColaboradorQuestionario findByColaboradorAvaliacao(Colaborador colaborador, Avaliacao avaliacao);
	ColaboradorQuestionario findByColaboradorAvaliacaoCurso(Long colaboradorId, Long avaliacaoCursoId, Long turmaId);
	Collection<ColaboradorQuestionario> findQuestionarioByTurmaLiberadaPorUsuario(Long usuarioId);
	void removeByCandidato(Long candidatoId);
	ColaboradorQuestionario findColaborador(Long colaboradorId, Long questionarioId, Long turmaId);
	Collection<ColaboradorQuestionario> findForRankingPerformanceAvaliacaoCurso(Long[] cursosIds, Long[] turmasIds, Long[] avaliacaoCursosIds);
	void removeBySolicitacaoId(Long solicitacaoId);
	Collection<ColaboradorQuestionario> findAutoAvaliacao(Long colaboradorId);
	public void validaAssociacao(Collection<Colaborador> avaliados, Collection<Colaborador> avaliadores, boolean permiteAutoAvaliacao) throws Exception;
	Collection<ColaboradorQuestionario> findRespondidasByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	boolean existeMesmoModeloAvaliacaoEmDesempenhoEPeriodoExperiencia(Long avaliacaoId);
	public void updateAvaliacaoFromColaboradorQuestionarioByAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho);
}