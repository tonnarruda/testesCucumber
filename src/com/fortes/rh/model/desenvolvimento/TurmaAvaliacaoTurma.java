package com.fortes.rh.model.desenvolvimento;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="turma_avaliacaoturma")
@AssociationOverrides({
		@AssociationOverride(name = "pk.turma", 
			joinColumns = @JoinColumn(name = "turma_id")),
		@AssociationOverride(name = "pk.turmaAvaliacaoTurma", 
			joinColumns = @JoinColumn(name = "avaliacaoturmas_id")) }) 
public class TurmaAvaliacaoTurma
{
	@EmbeddedId
	@GeneratedValue(generator="pkGenerator")
	@GenericGenerator(name="pkGenerator", strategy="com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurmaIdentifierGenerator") 
	private TurmaAvaliacaoTurmaId pk = new TurmaAvaliacaoTurmaId();
	
	private boolean liberada;

	public TurmaAvaliacaoTurma() {
				
	}
	
	public TurmaAvaliacaoTurmaId getPk() {
		return pk;
	}

	public void setPk(TurmaAvaliacaoTurmaId pk) {
		this.pk = pk;
	}

	public boolean isLiberada() {
		return liberada;
	}

	public void setLiberada(boolean liberada) {
		this.liberada = liberada;
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
 
		TurmaAvaliacaoTurma that = (TurmaAvaliacaoTurma) o;
 
		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null)
			return false;
 
		return true;
	}
 
	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
}