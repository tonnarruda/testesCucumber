package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.TipoExtintor;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="extintor_sequence", allocationSize=1)
public class Extintor extends AbstractModel implements Serializable
{
	@Column(length=1)
	private String tipo;

	private Integer numeroCilindro;

	@Column(length=10)
	private String capacidade;

	@Column(length=50)
	private String fabricante;

	private Integer periodoMaxRecarga;
	private Integer periodoMaxInspecao;
	private Integer periodoMaxHidrostatico;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="extintor", cascade=CascadeType.ALL)
	private Collection<HistoricoExtintor> historicoExtintores;
	
	@Transient
	private HistoricoExtintor historicoExtintor;

	@Transient
	private String descricao;

	private boolean ativo=true;

	@ManyToOne(fetch=FetchType.LAZY)
	private Empresa empresa;
	
	public Extintor()
	{
		super();
	}

	// usado apenas pelo ExtintorDWR para preencher uma descrição
	public Extintor(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getTipoDic()
	{
		if (tipo == null)
			return "";

		return (String)new TipoExtintor().get(tipo);
	}
	
	/**
	 * atributo <i>transiente</i>. 
	 **/
	@NaoAudita
	public String getDescricao()
	{
		// apenas quando a descrição é populada pelo construtor. @see ExtintorDWR
		if (this.descricao != null)
			return this.descricao;
		
		String descricao = getTipoDicAbreviado();
		
		if (numeroCilindro != null)
			descricao += " - " + numeroCilindro;
		
		return descricao;
	}
	
	public String getTipoDicAbreviado()
	{
		if (tipo == null)
			return "";

		String tipoDic = (String)new TipoExtintor().get(tipo);
		tipoDic = tipoDic.split("-")[0].trim();

		return tipoDic;
	}

	//Proj
	public void setEmpresaIdProjection(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}

	public void setLocalizacaoProjection(String localizacao)
	{
		if (this.historicoExtintor == null)
			this.historicoExtintor = new HistoricoExtintor();
		
		this.historicoExtintor.setLocalizacao(localizacao);
	}

	public void setEstabelecimentoIdProjection(Long estabelecimentoId)
	{
		if (this.historicoExtintor == null)
			this.historicoExtintor = new HistoricoExtintor();
		
		if (this.historicoExtintor.getEstabelecimento() == null)
			this.historicoExtintor.setEstabelecimento(new Estabelecimento());
					
		this.historicoExtintor.getEstabelecimento().setId(estabelecimentoId);
	}

	public boolean isAtivo()
	{
		return ativo;
	}

	public void setAtivo(boolean ativo)
	{
		this.ativo = ativo;
	}

	public String getCapacidade()
	{
		return capacidade;
	}

	public void setCapacidade(String capacidade)
	{
		this.capacidade = capacidade;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public String getFabricante()
	{
		return fabricante;
	}

	public void setFabricante(String fabricante)
	{
		this.fabricante = fabricante;
	}

	public Integer getNumeroCilindro()
	{
		return numeroCilindro;
	}

	public void setNumeroCilindro(Integer numeroCilindro)
	{
		this.numeroCilindro = numeroCilindro;
	}

	public Integer getPeriodoMaxHidrostatico()
	{
		return periodoMaxHidrostatico;
	}

	public void setPeriodoMaxHidrostatico(Integer periodoMaxHidrostatico)
	{
		this.periodoMaxHidrostatico = periodoMaxHidrostatico;
	}

	public Integer getPeriodoMaxInspecao()
	{
		return periodoMaxInspecao;
	}

	public void setPeriodoMaxInspecao(Integer periodoMaxInspecao)
	{
		this.periodoMaxInspecao = periodoMaxInspecao;
	}

	public Integer getPeriodoMaxRecarga()
	{
		return periodoMaxRecarga;
	}

	public void setPeriodoMaxRecarga(Integer periodoMaxRecarga)
	{
		this.periodoMaxRecarga = periodoMaxRecarga;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public Collection<HistoricoExtintor> getHistoricoExtintores() {
		return historicoExtintores;
	}

	public void setHistoricoExtintores(
			Collection<HistoricoExtintor> historicoExtintores) {
		this.historicoExtintores = historicoExtintores;
	}

	public HistoricoExtintor getHistoricoExtintor() {
		return historicoExtintor;
	}

	public void setHistoricoExtintor(HistoricoExtintor historicoExtintor) {
		this.historicoExtintor = historicoExtintor;
	}
}