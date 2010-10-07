package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="medicaorisco_sequence", allocationSize=1)
public class MedicaoRisco extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
    private Date data;
	
	@ManyToOne
	private Ambiente ambiente;
    
	@OneToMany(mappedBy="medicaoRisco", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Collection<RiscoMedicaoRisco> riscoMedicaoRiscos;
	
	public MedicaoRisco() {
	}

	public MedicaoRisco(Long id, RiscoMedicaoRisco rmr, boolean epcEficaz, Date data) 
	{
		setId(id);
		
		if (riscoMedicaoRiscos == null)
			riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		
		riscoMedicaoRiscos.add(rmr);
		
		this.data = data;
	}
	
	
	public void setProjectionAmbienteId(Long ambienteId)
	{
		if (ambiente == null)
			ambiente = new Ambiente();
		
		ambiente.setId(ambienteId);
	}
	
	public void setProjectionAmbienteNome(String ambienteNome)
	{
		if (ambiente == null)
			ambiente = new Ambiente();
		
		ambiente.setNome(ambienteNome);
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Collection<RiscoMedicaoRisco> getRiscoMedicaoRiscos() {
		return riscoMedicaoRiscos;
	}

	public void setRiscoMedicaoRiscos(
			Collection<RiscoMedicaoRisco> riscoMedicaoRiscos) {
		this.riscoMedicaoRiscos = riscoMedicaoRiscos;
	}
}
