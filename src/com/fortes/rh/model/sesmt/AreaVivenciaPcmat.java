package com.fortes.rh.model.sesmt;

import java.io.Serializable;

import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="areaVivenciaPcmat_sequence", allocationSize=1)
public class AreaVivenciaPcmat extends AbstractModel implements Serializable
{
	@ManyToOne
	private AreaVivencia areaVivencia;
	@ManyToOne
	private Pcmat pcmat;
	
	private String descricao;

	public AreaVivencia getAreaVivencia() {
		return areaVivencia;
	}

	public void setAreaVivencia(AreaVivencia areaVivencia) {
		this.areaVivencia = areaVivencia;
	}

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
	
	public void setAreaVivenciaNome(String areaVivenciaNome) {
		if (this.areaVivencia == null)
			this.areaVivencia = new AreaVivencia();
		
		this.areaVivencia.setNome(areaVivenciaNome);
	}
}
