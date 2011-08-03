package com.fortes.rh.model.geral;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

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
	@Transient
	private String descricao;
	@Transient
	private int qtdColaboradoresCadastrados;
	
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
	public void setProjectionCargoNome(String cargoNome)
	{
		iniciaCargo();
		cargo.setNome(cargoNome);
	}
	public void setProjectionCargoId(Long cargoId)
	{
		iniciaCargo();
		cargo.setId(cargoId);
	}
	public void setProjectionAreaOrganizacionalId(Long areaOrganizacionalId)	
	{
		iniciaArea();
		areaOrganizacional.setId(areaOrganizacionalId);
	}
	public void setProjectionAreaOrganizacionalNome(String areaOrganizacionalNome)	
	{
		iniciaArea();
		areaOrganizacional.setNome(areaOrganizacionalNome);
	}
	private void iniciaArea() {
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
	}
	private void iniciaCargo() {
		if(cargo == null)
			cargo = new Cargo();
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getQtdColaboradoresCadastrados() {
		return qtdColaboradoresCadastrados;
	}
	public void setQtdColaboradoresCadastrados(int qtdColaboradoresCadastrados) {
		this.qtdColaboradoresCadastrados = qtdColaboradoresCadastrados;
	}

}
