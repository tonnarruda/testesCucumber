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

	public void setMedidaSegurancaId(Long medidaSegurancaId) {
		newMedidaSeguranca();
		this.medidaSeguranca.setId(medidaSegurancaId);
	}
	
	public void setMedidaSegurancaDescricao(String medidaSegurancaDescricao) {
		newMedidaSeguranca();
		this.medidaSeguranca.setDescricao(medidaSegurancaDescricao);
	}

	public Long getMedidaSegurancaId() {
		if (this.medidaSeguranca == null)
			return null;
		
		return this.medidaSeguranca.getId();
	}
	
	public void setRiscoFasePcmatId(Long riscoFasePcmatId) {
		if (this.riscoFasePcmat == null)
			this.riscoFasePcmat = new RiscoFasePcmat();
		
		this.riscoFasePcmat.setId(riscoFasePcmatId);
	}
	
	private void newMedidaSeguranca() {
		if (this.medidaSeguranca == null)
			this.medidaSeguranca = new MedidaSeguranca();
	}
}
