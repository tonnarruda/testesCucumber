package com.fortes.rh.model.avaliacao;

import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;

public class ResultadoAvaliacaoDesempenho extends ResultadoQuestionario
{
	private static final long serialVersionUID = 1L;
	
	private Long avaliadoId;
	private String avaliadoNome;
	
	public ResultadoAvaliacaoDesempenho(Long avaliadoId, String avaliadoNome)
	{
		this.avaliadoId = avaliadoId;
		this.avaliadoNome = avaliadoNome;
	}

	public Long getAvaliadoId() {
		return avaliadoId;
	}

	public String getAvaliadoNome() {
		return avaliadoNome;
	}
}
