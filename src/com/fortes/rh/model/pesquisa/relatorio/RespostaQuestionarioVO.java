package com.fortes.rh.model.pesquisa.relatorio;

import java.util.Collection;

import com.fortes.rh.model.captacao.MatrizCompetenciaNivelConfiguracao;

public class RespostaQuestionarioVO {
	
	private Collection<RespostaQuestionario> respostasQuestionario;
	private Collection<MatrizCompetenciaNivelConfiguracao> matrizCompetecias;
	private String colaboradorQuestionarioPerformance;
	private String colaboradorQuestionarioPerformanceNivelCompetencia;
	private boolean somenteCompetencias;
	
	public Collection<RespostaQuestionario> getRespostasQuestionario() {
		return respostasQuestionario;
	}
	
	public void setRespostasQuestionario(Collection<RespostaQuestionario> respostasQuestionario) {
		this.respostasQuestionario = respostasQuestionario;
	}
	
	public Collection<MatrizCompetenciaNivelConfiguracao> getMatrizCompetecias() {
		return matrizCompetecias;
	}
	
	public void setMatrizCompetecias(Collection<MatrizCompetenciaNivelConfiguracao> matrizCompetecias) {
		this.matrizCompetecias = matrizCompetecias;
	}

	public String getColaboradorQuestionarioPerformance() {
		return colaboradorQuestionarioPerformance;
	}

	public void setColaboradorQuestionarioPerformance(String colaboradorQuestionarioPerformance) {
		this.colaboradorQuestionarioPerformance = colaboradorQuestionarioPerformance;
	}

	public String getColaboradorQuestionarioPerformanceNivelCompetencia() {
		return colaboradorQuestionarioPerformanceNivelCompetencia;
	}

	public void setColaboradorQuestionarioPerformanceNivelCompetencia(String colaboradorQuestionarioPerformanceNivelCompetencia) {
		this.colaboradorQuestionarioPerformanceNivelCompetencia = colaboradorQuestionarioPerformanceNivelCompetencia;
	}

	public boolean isSomenteCompetencias() {
		return somenteCompetencias;
	}

	public void setSomenteCompetencias(boolean somenteCompetencias) {
		this.somenteCompetencias = somenteCompetencias;
	}
}
