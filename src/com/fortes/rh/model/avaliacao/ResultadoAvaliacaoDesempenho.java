package com.fortes.rh.model.avaliacao;

import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;

public class ResultadoAvaliacaoDesempenho extends ResultadoQuestionario
{
	private static final long serialVersionUID = 1L;
	
	private Long avaliadoId;
	private String avaliadoNome;
	private Double performance;
	private Integer pontuacaoMaximaAspecto;
	private Integer pontuacaoPorAspecto;
	
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

	public Double getPercentualAspecto(){
		return ((double) pontuacaoPorAspecto / (double) pontuacaoMaximaAspecto)*performance;
	}

	public Integer getPontuacaoMaximaAspecto() {
		return pontuacaoMaximaAspecto;
	}

	public void setPontuacaoMaximaAspecto(Integer pontuacaoMaximaAspecto) {
		this.pontuacaoMaximaAspecto = pontuacaoMaximaAspecto;
	}

	public Integer getPontuacaoPorAspecto() {
		return pontuacaoPorAspecto;
	}

	public void setPontuacaoPorAspecto(Integer pontuacaoPorAspecto) {
		this.pontuacaoPorAspecto = pontuacaoPorAspecto;
	}
}
