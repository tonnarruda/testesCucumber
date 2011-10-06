package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorperiodoexperienciaavaliacao_sequence", allocationSize=1)
public class ColaboradorPeriodoExperienciaAvaliacao extends AbstractModel implements Serializable
{
    @ManyToOne
    private Colaborador colaborador;
    
    @ManyToOne
    private PeriodoExperiencia periodoExperiencia;

    @ManyToOne
    private Avaliacao avaliacao;

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public PeriodoExperiencia getPeriodoExperiencia() {
		return periodoExperiencia;
	}

	public void setPeriodoExperiencia(PeriodoExperiencia periodoExperiencia) {
		this.periodoExperiencia = periodoExperiencia;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
}