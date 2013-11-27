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
		if (this.risco == null)
			this.risco = new Risco();
		
		this.risco.setId(riscoId);
	}
	
	public Long getRiscoId() {
		if (this.risco == null)
			return null;
		
		return this.risco.getId();
	}
	
	public void setFasePcmatId(Long fasePcmatId) {
		if (this.fasePcmat == null)
			this.fasePcmat = new FasePcmat();
		
		this.fasePcmat.setId(fasePcmatId);
	}
}
