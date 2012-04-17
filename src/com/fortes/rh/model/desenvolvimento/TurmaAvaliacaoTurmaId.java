package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;

@SuppressWarnings("serial")
@Embeddable 
public class TurmaAvaliacaoTurmaId extends AbstractModel implements Serializable
{
	@ManyToOne
	private Turma turma;
	
	@ManyToOne
	private AvaliacaoTurma avaliacaoTurma;
	
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
		
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
 
        TurmaAvaliacaoTurmaId that = (TurmaAvaliacaoTurmaId) o;
 
        if (turma != null ? !turma.equals(that.turma) : that.turma != null) 
        	return false;
        
        if (avaliacaoTurma != null ? !avaliacaoTurma.equals(that.avaliacaoTurma) : that.avaliacaoTurma != null)
            return false;
 
        return true;
    }
 
    public int hashCode() {
        int result;
        result = (turma != null ? turma.hashCode() : 0);
        result = 31 * result + (avaliacaoTurma != null ? avaliacaoTurma.hashCode() : 0);
        return result;
    }
}