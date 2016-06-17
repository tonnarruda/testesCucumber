package com.fortes.rh.model.avaliacao;

import java.util.LinkedList;

public class ResultadoCompetenciaColaborador
{
	private Long competenciaId;
	private String competenciaNome;
	private LinkedList<ResultadoCompetencia> resultadoCompetencias = new LinkedList<ResultadoCompetencia>();
	
	public Long getCompetenciaId() {
		return competenciaId;
	}
	public void setCompetenciaId(Long competenciaId) {
		this.competenciaId = competenciaId;
	}
	public String getCompetenciaNome() {
		return competenciaNome;
	}
	public void setCompetenciaNome(String competenciaNome) {
		this.competenciaNome = competenciaNome;
	}
	public LinkedList<ResultadoCompetencia> getResultadoCompetencias() {
		return resultadoCompetencias;
	}
	public void setResultadoCompetencias(LinkedList<ResultadoCompetencia> resultadoCompetencias) {
		this.resultadoCompetencias = resultadoCompetencias;
	}
	
}
