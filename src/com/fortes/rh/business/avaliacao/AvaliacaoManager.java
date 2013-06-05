package com.fortes.rh.business.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.ColaboradorResposta;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;

public interface AvaliacaoManager extends GenericManager<Avaliacao>
{
	Collection<Avaliacao> findAllSelect(Integer page, Integer pagingSize, Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo);
	Integer getCount(Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo);
	Collection<QuestionarioRelatorio> getQuestionarioRelatorio(Avaliacao avaliacao);
	void enviaLembrete();
	Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId);
	Collection<ResultadoQuestionario> montaResultado(Collection<Pergunta> perguntas, Long[] perguntasIds, Long[] areaIds, Date periodoIni, Date periodoFim, Avaliacao avaliacao, Long empresaId) throws Exception;
	String montaObsAvaliadores(Collection<ColaboradorResposta> colaboradorRespostas);
	void clonar(Long id, Long... empresasIds);
	Collection<Avaliacao> findPeriodoExperienciaIsNull(char acompanhamentoExperiencia, Long empresaId);
	Collection<Avaliacao> findAllSelectComAvaliacaoDesempenho(Long empresaId, boolean ativa);
	void remove(Long avaliacaoId);
}
