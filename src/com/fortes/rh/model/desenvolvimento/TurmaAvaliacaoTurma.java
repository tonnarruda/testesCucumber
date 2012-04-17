package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;

@SuppressWarnings("serial")
@Entity
@Table(name="turma_avaliacaoturma")
@IdClass(TurmaAvaliacaoTurmaId.class)
public class TurmaAvaliacaoTurma extends AbstractModel implements Serializable, Cloneable
{
	@Id
	private Long turmaId;
	@Id
	private Long avaliacaoTurmaId;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="turma_id", referencedColumnName="id")
	private Turma turma;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="avaliacaoturmas_id", referencedColumnName="id")
	private AvaliacaoTurma avaliacaoTurma;

	public Long getTurmaId() {
		return turmaId;
	}

	public void setTurmaId(Long turmaId) {
		this.turmaId = turmaId;
	}

	public Long getAvaliacaoTurmaId() {
		return avaliacaoTurmaId;
	}

	public void setAvaliacaoTurmaId(Long avaliacaoTurmaId) {
		this.avaliacaoTurmaId = avaliacaoTurmaId;
	}

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
}