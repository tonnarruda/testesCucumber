package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="epiPcmat_sequence", allocationSize=1)
public class EpiPcmat extends AbstractModel implements Serializable
{
	@ManyToOne
	private Pcmat pcmat;
	
	@ManyToOne
	private Epi epi;
	
	private String atividades;

	public Pcmat getPcmat() {
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}

	public Epi getEpi() {
		return epi;
	}

	public void setEpi(Epi epi) {
		this.epi = epi;
	}

	public String getAtividades() {
		return atividades;
	}

	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}
	
	public void setEpiId(Long epiId) 
	{
		newEpi();
		epi.setId(epiId);
	}
	
	public void setEpiNome(String epiNome) 
	{
		newEpi();
		epi.setNome(epiNome);
	}
	
	public void setEpiDescricao(String epiDescricao) 
	{
		newEpi();
		epi.setDescricao(epiDescricao);
	}

	public void setPcmatId(Long pcmatId) 
	{
		if (this.pcmat == null)
			this.pcmat = new Pcmat();
		
		this.pcmat.setId(pcmatId);
	}
	
	private void newEpi() {
		if(epi == null)
			epi = new Epi();
	}
}
