package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="epcPcmat_sequence", allocationSize=1)
public class EpcPcmat extends AbstractModel implements Serializable
{
	@ManyToOne
	private Pcmat pcmat;
	
	@ManyToOne
	private Epc epc;
	
	private String descricao;

	public Pcmat getPcmat() {
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}

	public Epc getEpc() {
		return epc;
	}

	public void setEpc(Epc epc) {
		this.epc = epc;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setEpcId(Long epcId) 
	{
		if(epc == null)
			epc = new Epc();
		
		epc.setId(epcId);
	}
	
	public void setEpcDescricao(String epcDescricao) 
	{
		if(epc == null)
			epc = new Epc();
		
		epc.setDescricao(epcDescricao);
	}

	public void setPcmatId(Long pcmatId) 
	{
		if(this.pcmat == null)
			this.pcmat = new Pcmat();
		
		this.pcmat.setId(pcmatId);
	}
}
