package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;

@SuppressWarnings("serial")
@Entity
@Table(name="turma_avaliacaoturma")
@SequenceGenerator(name="sequence", sequenceName="turma_avaliacaoturma_sequence", allocationSize=1)
public class TurmaAvaliacaoTurma extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne
	private Turma turma; 
	
	@ManyToOne
	private AvaliacaoTurma avaliacaoTurma;
	
	private boolean liberada;

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public AvaliacaoTurma getAvaliacaoTurma() {
		return avaliacaoTurma;
	}

	public void setAvaliacaoTurma(AvaliacaoTurma avaliacaoTurma) {
		this.avaliacaoTurma = avaliacaoTurma;
	}

	public boolean isLiberada() {
		return liberada;
	}

	public void setLiberada(boolean liberada) {
		this.liberada = liberada;
	}
	
	public void setProjectionTurmaId(Long turmaId) {
		if (turma == null)
			turma = new Turma();
		
		turma.setId(turmaId);
	}
	
	public void setProjectionAvaliacaoTurmaId(Long avaliacaoTurmaId) {
		if (avaliacaoTurma == null)
			avaliacaoTurma = new AvaliacaoTurma();
		
		avaliacaoTurma.setId(avaliacaoTurmaId);
	}
}