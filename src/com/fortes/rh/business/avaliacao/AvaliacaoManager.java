package com.fortes.rh.business.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;

public interface AvaliacaoManager extends GenericManager<Avaliacao>
{
	Collection<Avaliacao> findAllSelect(Long id, Boolean ativo, char modeloAvaliacao);
	Collection<QuestionarioRelatorio> getQuestionarioRelatorio(Avaliacao avaliacao);
	void enviaLembrete();
	Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId);
	Collection<ResultadoQuestionario> montaResultado(Collection<Pergunta> perguntas, Long[] perguntasIds, Long[] areaIds, Date periodoIni, Date periodoFim, Avaliacao avaliacao) throws Exception;
	void clonar(Long id);
}
