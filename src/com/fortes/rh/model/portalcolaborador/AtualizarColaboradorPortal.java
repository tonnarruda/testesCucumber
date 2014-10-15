package com.fortes.rh.model.portalcolaborador;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaborador_sequence", allocationSize=1)
public class AtualizarColaboradorPortal extends AbstractModel implements Serializable
{
	@OneToOne
	private Colaborador colaborador;

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	public void setProjectionColaboradorId(Long projectionColaboradorId){
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setId(projectionColaboradorId);
	}
}
