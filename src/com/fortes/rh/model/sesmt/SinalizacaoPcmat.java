package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="sinalizacaoPcmat_sequence", allocationSize=1)
public class SinalizacaoPcmat extends AbstractModel implements Serializable
{
	@ManyToOne
	private Pcmat pcmat;
	private String descricao;
	
	public Pcmat getPcmat() {
		return pcmat;
	}
	
	public void setPcmat(Pcmat pcmat) {
		this.pcmat = pcmat;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
