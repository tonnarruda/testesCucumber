package com.fortes.rh.business.pesquisa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;
import com.fortes.rh.model.pesquisa.relatorio.RespostaQuestionarioVO;
import com.fortes.rh.security.spring.aop.callback.ColaboradorRespostaAuditorCallbackImpl;
import com.fortes.security.auditoria.Audita;

public interface ColaboradorRespostaManager extends GenericManager<ColaboradorResposta>
{
	@SuppressWarnings("rawtypes")
	List countRespostas(Long perguntaId, Long[] estabelecimentosIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long turmaId);
	Collection<ColaboradorResposta> findInPerguntaIds(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Questionario questionario, Long empresaId);
	Collection<ColaboradorResposta> findInPerguntaIdsAvaliacao(Long[] perguntasIds, Long[] areasIds,  Date periodoIni, Date periodoFim, Long empresaId);
	void salvaQuestionarioRespondido(String respostas, Questionario questionario, Long colaboradorId, Long turmaId, char vinculo, Date respondidaEm, Long colaboradorQuestionarioId, boolean inserirFichaMedica) throws Exception;
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Long empresaId);
	Collection<ColaboradorResposta> findRespostasColaborador(Long questionarioId, Boolean aplicarPorAspecto);
	Collection<ColaboradorResposta> findByQuestionarioColaborador(Long questionarioId, Long colaboradorId, Long turmaId);
	Collection<ColaboradorResposta> findByQuestionarioCandidato(Long questionarioId, Long candidatoId);
	void removeFicha(Long colaboradorQuestionarioId) throws Exception;
	Collection<ColaboradorResposta> findByColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario, Long questionarioId);
	Collection<ColaboradorResposta> findByColaboradorQuestionario(Long id);
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultipla(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Long[] cargosIds, Date periodoIni, Date periodoFim, boolean desligamento, Long turmaId, Integer totalColaboradores, Long empresaId);
	@Audita(operacao="Inserção", auditor=ColaboradorRespostaAuditorCallbackImpl.class)
	void save(Collection<ColaboradorResposta> colaboradorRespostas, ColaboradorQuestionario colaboradorQuestionario, Long usuarioLogadoId, Long candidatoId);
	@Audita(operacao="Atualização", auditor=ColaboradorRespostaAuditorCallbackImpl.class)
	void update(Collection<ColaboradorResposta> colaboradorRespostas, ColaboradorQuestionario colaboradorQuestionario, Long usuarioLogadoId, Long empresaId, Collection<ConfiguracaoNivelCompetencia> niveisCompetenciaMarcados);
	Collection<ColaboradorResposta> findByAvaliadoAndAvaliacaoDesempenho(Long avaliadoId, Long avaliacaoDesempenhoId, boolean desconsiderarAutoAvaliacao);
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas(Long avaliadoId, Long avaliacaoDesempenhoId);
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultipla(Long avaliadoId, Long avaliacaoDesempenhoId);
	void removeFichas(Long[] colaboradorQuestionarioIds);
	Collection<RespostaQuestionarioVO> findRespostasAvaliacaoDesempenho(Long colaboradorQuestionarioId);
	Usuario findUsuarioParaAuditoria(Long usuarioId);
	Integer countColaboradorAvaliacaoRespondida(Long avaliacaoId);
	boolean existeRespostaSemCargo(Long[] perguntasIds);
	Collection<ColaboradorResposta> findPerguntasRespostasByColaboradorQuestionario(Long colaboradorQuestionarioId);
}