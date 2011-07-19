package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="quantidadelimitecolaboradoresporcargo_sequence", allocationSize=1)
public class QuantidadeLimiteColaboradoresPorCargo extends AbstractModel implements Serializable
{
	@ManyToOne
	private AreaOrganizacional areaOrganizacional;
	@ManyToOne
	private Cargo cargo;
	private int limite;
	
	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}
	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public int getLimite() {
		return limite;
	}
	public void setLimite(int limite) {
		this.limite = limite;
	}

}
