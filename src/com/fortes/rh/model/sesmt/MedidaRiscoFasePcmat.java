package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="medidariscofasepcmat_sequence", allocationSize=1)
public class MedidaRiscoFasePcmat extends AbstractModel implements Serializable
{
	@ManyToOne
	private MedidaSeguranca medidaSeguranca;
	@ManyToOne
	private RiscoFasePcmat riscoFasePcmat;
	
	public MedidaSeguranca getMedidaSeguranca() {
		return medidaSeguranca;
	}
	
	public void setMedidaSeguranca(MedidaSeguranca medidaSeguranca) {
		this.medidaSeguranca = medidaSeguranca;
	}
	
	public RiscoFasePcmat getRiscoFasePcmat() {
		return riscoFasePcmat;
	}
	
	public void setRiscoFasePcmat(RiscoFasePcmat riscoFasePcmat) {
		this.riscoFasePcmat = riscoFasePcmat;
	}
}
