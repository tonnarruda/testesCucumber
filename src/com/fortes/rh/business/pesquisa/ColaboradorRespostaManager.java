package com.fortes.rh.business.pesquisa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioResultadoPerguntaObjetiva;

@SuppressWarnings("unchecked")
public interface ColaboradorRespostaManager extends GenericManager<ColaboradorResposta>
{
	List countRespostas(Long perguntaId, Long[] estabelecimentosIds, Long[] areasIds, Date periodoIni, Date periodoFim, Long turmaId);
	Collection<ColaboradorResposta> findInPerguntaIds(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Date periodoIni, Date periodoFim, Long turmaId, Questionario questionario);
	void salvaQuestionarioRespondido(String respostas, Questionario questionario, Long colaboradorId, Long turmaId, char vinculo, Date respondidaEm) throws Exception;
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Date periodoIni, Date periodoFim, Long turmaId);
	Collection<ColaboradorResposta> findRespostasColaborador(Long questionarioId, Boolean aplicarPorAspecto);
	Collection<ColaboradorResposta> findByQuestionarioColaborador(Long questionarioId, Long colaboradorId, Long turmaId);
	Collection<ColaboradorResposta> findByQuestionarioCandidato(Long questionarioId, Long candidatoId);
	void removeFicha(Long colaboradorQuestionarioId) throws Exception;
	Collection<ColaboradorResposta> findByColaboradorQuestionario(ColaboradorQuestionario colaboradorQuestionario, Long questionarioId);
	Collection<ColaboradorResposta> findByColaboradorQuestionario(Long id);
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultipla(Long[] perguntasIds, Long[] estabelecimentosIds, Long[] areaIds, Date periodoIni, Date periodoFim, Long turmaId, Integer totalColaboradores);
	void save(Collection<ColaboradorResposta> colaboradorRespostas, ColaboradorQuestionario colaboradorQuestionario);
	void update(Collection<ColaboradorResposta> colaboradorRespostas, ColaboradorQuestionario colaboradorQuestionario);
	Collection<ColaboradorResposta> findByAvaliadoAndAvaliacaoDesempenho(Long avaliadoId, Long avaliacaoDesempenhoId);
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostas(Long avaliadoId, Long avaliacaoDesempenhoId);
	Collection<QuestionarioResultadoPerguntaObjetiva> calculaPercentualRespostasMultipla(Long avaliadoId, Long avaliacaoDesempenhoId);
}