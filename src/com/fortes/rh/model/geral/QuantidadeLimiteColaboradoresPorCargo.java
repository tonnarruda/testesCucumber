package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fortes.rh.model.cargosalario.Cargo;

@SuppressWarnings("serial")
@Entity
public class QuantidadeLimiteColaboradoresPorCargo implements Serializable
{
	@Id
	@Column( name="areaorganizacional_id", unique=true, nullable=false)
	private AreaOrganizacional areaOrganizacional;
	@Column( name="cargo_id", unique=true, nullable=false)
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
