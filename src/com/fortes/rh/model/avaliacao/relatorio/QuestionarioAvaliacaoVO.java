package com.fortes.rh.model.avaliacao.relatorio;

import java.io.Serializable;
import java.util.Collection;

import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;

public class QuestionarioAvaliacaoVO implements Serializable
{
	private static final long serialVersionUID = 5732056883883181559L;
	
	private QuestionarioRelatorio questionarioRelatorio;
	private Collection<MatrizCompetenciaNivelConfiguracao> matrizes;
	private boolean somenteCompetencias = false;
	
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
	public boolean isSomenteCompetencias() {
		return somenteCompetencias;
	}
	public void setSomenteCompetencias(boolean somenteCompetencias) {
		this.somenteCompetencias = somenteCompetencias;
	}
}