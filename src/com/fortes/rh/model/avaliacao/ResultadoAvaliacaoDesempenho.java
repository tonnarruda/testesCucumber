package com.fortes.rh.model.avaliacao;

import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;

public class ResultadoAvaliacaoDesempenho extends ResultadoQuestionario
{
	private static final long serialVersionUID = 1L;
	
	private Long avaliadoId;
	private String avaliadoNome;
	private String obsAvaliadores;
	private Double performance;
	
	public ResultadoAvaliacaoDesempenho(Long avaliadoId, String avaliadoNome, Double performance)
	{
		this.avaliadoId = avaliadoId;
		this.avaliadoNome = avaliadoNome;
		this.performance = performance;
	}

	public Long getAvaliadoId() {
		return avaliadoId;
	}

	public String getAvaliadoNome() {
		return avaliadoNome;
	}

	public Double getPerformance() {
		return performance;
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public String getObsAvaliadores() {
		return obsAvaliadores;
	}

	public void setObsAvaliadores(String obsAvaliadores) {
		this.obsAvaliadores = obsAvaliadores;
	}
}
