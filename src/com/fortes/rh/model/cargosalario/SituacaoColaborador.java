package com.fortes.rh.model.cargosalario;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

@Entity
public class SituacaoColaborador
{
	@Id
	private Long historicoColaboradorId;
	@Temporal(TemporalType.DATE)
	private Date data;
	private int tipo;
	private Double salario;
	@ManyToOne(fetch=FetchType.LAZY)
	private Cargo cargo;
	@ManyToOne(fetch=FetchType.LAZY)
	private FaixaSalarial faixaSalarial;
	@ManyToOne(fetch=FetchType.LAZY)
	private Estabelecimento estabelecimento;
	@ManyToOne(fetch=FetchType.LAZY)
	private AreaOrganizacional areaOrganizacional;
	@ManyToOne(fetch=FetchType.LAZY)
	private Colaborador colaborador;
	
	public SituacaoColaborador() {
		super();
	}
	public SituacaoColaborador(Double salario, Date data, int tipo, Cargo cargo, FaixaSalarial faixaSalarial, Estabelecimento estabelecimento,
			AreaOrganizacional areaOrganizacional, Colaborador colaborador, String motivo,  Long historicoColaboradorId) {
		super();
		this.historicoColaboradorId = historicoColaboradorId;
		this.data = data;
		this.tipo = tipo;
		this.salario = salario;
		this.cargo = cargo;
		this.faixaSalarial = faixaSalarial;
		this.estabelecimento = estabelecimento;
		this.areaOrganizacional = areaOrganizacional;
		this.colaborador = colaborador;
		this.motivo = motivo;
	}

	private String motivo;

	public Long getHistoricoColaboradorId() {
		return historicoColaboradorId;
	}

	public void setHistoricoColaboradorId(Long historicoColaboradorId) {
		this.historicoColaboradorId = historicoColaboradorId;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public FaixaSalarial getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
}