package com.fortes.rh.model.cargosalario;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;

@Entity
public class SituacaoColaborador implements Comparable<SituacaoColaborador>
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
	
	@Transient
	private String dataExtenso;

	public int compareTo(SituacaoColaborador situacaoColaborador) 
	{
		return (estabelecimento.getNome() + " " + areaOrganizacional.getDescricao() + " " + colaborador.getNome()).compareTo(situacaoColaborador.getEstabelecimento().getNome() + " " + situacaoColaborador.getAreaOrganizacional().getDescricao() + " " + situacaoColaborador.getColaborador().getNome());
	}
	
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

	public void setProjectionEstabelecimentoNome(String projectionEstabelecimentoNome)
	{
		if (this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setNome(projectionEstabelecimentoNome);
	}

	public void setProjectionEstabelecimentoId(Long projectionEstabelecimentoId)
	{
		if (this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setId(projectionEstabelecimentoId);
	}
	
	public void setProjectionFaixaSalarialId(Long projectionFaixaSalarialId)
	{
		if (this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		
		this.faixaSalarial.setId(projectionFaixaSalarialId);
	}
	
	public void setProjectionAreaOrganizacionalId(Long projectionAreaOrganizacionalId)
	{
		if (this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		
		this.areaOrganizacional.setId(projectionAreaOrganizacionalId);
	}
	
	public void setProjectionCargoId(Long projectionCargoId)
	{
		if (this.cargo == null)
			this.cargo = new Cargo();
		
		this.cargo.setId(projectionCargoId);
	}
	
	
	public void setProjectionColaboradorId(Long projectionColaboradorId)
	{
		inicializaColaborador();
		this.colaborador.setId(projectionColaboradorId);
	}
	
	public void setProjectionColaboradorNome(String projectionColaboradorNome)
	{
		inicializaColaborador();
		this.colaborador.setNome(projectionColaboradorNome);
	}

	public void setProjectionColaboradorNomeComercial(String projectionColaboradorNomeComercial)
	{
		inicializaColaborador();
		this.colaborador.setNomeComercial(projectionColaboradorNomeComercial);
	}

	public void setProjectionColaboradorMatricula(String projectionColaboradorMatricula)
	{
		inicializaColaborador();
		this.colaborador.setMatricula(projectionColaboradorMatricula);
	}
	
	private void inicializaColaborador() {
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
	}
	
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

	public String getDataExtenso() {
		return dataExtenso;
	}

	public void setDataExtenso(String dataExtenso) {
		this.dataExtenso = dataExtenso;
	}
	
}