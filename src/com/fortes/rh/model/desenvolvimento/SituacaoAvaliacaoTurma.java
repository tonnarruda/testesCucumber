package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class SituacaoAvaliacaoTurma implements Serializable 
{
	@Id
	private long turmaId; 
	
	private char status;
	
	public long getTurmaId() {
		return turmaId;
	}
	
	public void setTurmaId(long turmaId) {
		this.turmaId = turmaId;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
}