package com.fortes.rh.model.avaliacao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;

@Entity
@SequenceGenerator(name="sequence", sequenceName="avaliado_sequence", allocationSize=1)//TODO utilizamos a sequence do questionario para não alterar as consultas, Francisco
public class Avaliador extends AbstractModel implements Serializable, Cloneable
{
	@Transient private static final long serialVersionUID = 1L;

	@ManyToOne
    private Colaborador colaborador;
	
	@ManyToOne
	private AvaliacaoDesempenho avaliacaoDesempenho;

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}
}
