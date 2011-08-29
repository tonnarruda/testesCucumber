package com.fortes.rh.model.geral.relatorio;

import java.util.Collection;

import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Colaborador;

public class CartaoAcompanhamentoExperienciaVO
{
    private Colaborador colaborador;
    private Collection<PeriodoExperiencia> periodosExperiencias;
    private String observacao;
	
    public Colaborador getColaborador() {
		return colaborador;
	}
    
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Collection<PeriodoExperiencia> getPeriodosExperiencias() {
		return periodosExperiencias;
	}

	public void setPeriodosExperiencias(
			Collection<PeriodoExperiencia> periodosExperiencias) {
		this.periodosExperiencias = periodosExperiencias;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}