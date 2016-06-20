package com.fortes.rh.model.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

public class ResultadoCompetenciaColaborador
{
	private Long competenciaId;
	private String competenciaNome;
	private Collection<ResultadoCompetencia> resultadoCompetencias = new ArrayList<ResultadoCompetencia>();
	
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
	public Collection<ResultadoCompetencia> getResultadoCompetencias() {
		return resultadoCompetencias;
	}
	public void setResultadoCompetencias(Collection<ResultadoCompetencia> resultadoCompetencias) {
		this.resultadoCompetencias = resultadoCompetencias;
	}
}
