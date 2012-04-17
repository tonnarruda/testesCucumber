package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Id;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
public class TurmaAvaliacaoTurmaId extends AbstractModel implements Serializable, Cloneable
{
	@Id
	private Long turmaId;
	
	@Id
	private Long avaliacaoTurmaId;

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
}