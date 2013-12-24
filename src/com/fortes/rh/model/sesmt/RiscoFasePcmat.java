package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="riscofasepcmat_sequence", allocationSize=1)
public class RiscoFasePcmat extends AbstractModel implements Serializable
{
	@ManyToOne
	private Risco risco;
	@ManyToOne
	private FasePcmat fasePcmat;
	
	@OneToMany(mappedBy="riscoFasePcmat")
	private Collection<MedidaRiscoFasePcmat> medidasRiscoFasePcmat;
	
	public Risco getRisco() {
		return risco;
	}
	
	public void setRisco(Risco risco) {
		this.risco = risco;
	}
	
	public FasePcmat getFasePcmat() {
		return fasePcmat;
	}
	
	public void setFasePcmat(FasePcmat fasePcmat) {
		this.fasePcmat = fasePcmat;
	}

	public Collection<MedidaRiscoFasePcmat> getMedidasRiscoFasePcmat() {
		return medidasRiscoFasePcmat;
	}

	public void setMedidasRiscoFasePcmat(Collection<MedidaRiscoFasePcmat> medidasRiscoFasePcmat) {
		this.medidasRiscoFasePcmat = medidasRiscoFasePcmat;
	}

	public void setRiscoId(Long riscoId) {
		newRisco();
		this.risco.setId(riscoId);
	}
	
	public void setRiscoDescricao(String riscoDescricao) {
		newRisco();
		this.risco.setDescricao(riscoDescricao);
	}
	
	public void setRiscoGrupoRisco(String riscoGrupoRisco) {
		newRisco();
		this.risco.setGrupoRisco(riscoGrupoRisco);
	}
	
	public Long getRiscoId() {
		if (this.risco == null)
			return null;
		
		return this.risco.getId();
	}
	
	public void setFasePcmatId(Long fasePcmatId) {
		newFasePcmat();
		this.fasePcmat.setId(fasePcmatId);
	}
	
	public void setFasePcmatDescricao(String fasePcmatDescricao) {
		newFasePcmat();
		this.fasePcmat.setDescricao(fasePcmatDescricao);
	}
	
	public void setFaseId(Long faseId) {
		newFasePcmat();
		this.fasePcmat.setFaseId(faseId);
	}
	
	public void setFaseDescricao(String faseDescricao) {
		newFasePcmat();
		this.fasePcmat.setFaseDescricao(faseDescricao);
	}

	private void newRisco() {
		if (this.risco == null)
			this.risco = new Risco();
	}
	
	private void newFasePcmat() {
		if (this.fasePcmat == null)
			this.fasePcmat = new FasePcmat();
	}
}
