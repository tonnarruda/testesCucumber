package com.fortes.rh.model.avaliacao.relatorio;

import java.io.Serializable;
import java.util.Collection;

import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;

public class QuestionarioAvaliacaoDesempenhoVO implements Serializable
{
	private static final long serialVersionUID = 5732056883883181559L;
	
	private QuestionarioRelatorio questionarioRelatorio;
	private Collection<MatrizCompetenciaNivelConfiguracao> matrizes;
	
	public QuestionarioRelatorio getQuestionarioRelatorio() {
		return questionarioRelatorio;
	}
	public void setQuestionarioRelatorio(QuestionarioRelatorio questionarioRelatorio) {
		this.questionarioRelatorio = questionarioRelatorio;
	}
	public Collection<MatrizCompetenciaNivelConfiguracao> getMatrizes() {
		return matrizes;
	}
	public void setMatrizes(Collection<MatrizCompetenciaNivelConfiguracao> matrizes) {
		this.matrizes = matrizes;
	}
}