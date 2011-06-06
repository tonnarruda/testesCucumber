package com.fortes.rh.model.cargosalario;

import java.util.Date;

import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;

public class FaixaSalarialHistoricoVO {

	private Long id;
	private Integer tipo;
	private Integer status;
	private Date dataFaixa;
	private Double valorFaixa;

	private Date dataIndice;
	private String nomeIndice;
	private Double valorIndice;
	private Double qtdeIndice;

	private Date proximaDataFaixa;
	private Date dataIndiceAnterior;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getDataFaixa() {
		return (this.dataIndice == null || this.dataIndice.equals(this.dataIndiceAnterior)) ? this.dataFaixa : this.dataIndice;
	}

	public void setDataFaixa(Date dataFaixa) {
		this.dataFaixa = dataFaixa;
	}

	public Double getValorFaixa() {
		return this.tipo == 2 ? this.valorIndice : this.valorFaixa;
	}

	public void setValorFaixa(Double valorFaixa) {
		this.valorFaixa = valorFaixa;
	}

	public Date getDataIndice() {
		return dataIndice;
	}

	public void setDataIndice(Date dataIndice) {
		this.dataIndice = dataIndice;
	}

	public String getNomeIndice() {
		return (this.tipo == 2) ? TipoAplicacaoIndice.getDescricao(this.tipo) + " (" + this.qtdeIndice.intValue() + " X " + this.nomeIndice + ")" : TipoAplicacaoIndice.getDescricao(this.tipo);
	}

	public void setNomeIndice(String nomeIndice) {
		this.nomeIndice = nomeIndice;
	}

	public Double getValorIndice() {
		return valorIndice;
	}

	public void setValorIndice(Double valorIndice) {
		this.valorIndice = valorIndice;
	}

	public void setQtdeIndice(Double qtdeIndice) {
		this.qtdeIndice = qtdeIndice;
	}

	public Double getQtdeIndice() {
		return qtdeIndice;
	}

	public Date getProximaDataFaixa() {
		return proximaDataFaixa;
	}

	public void setProximaDataFaixa(Date proximaDataFaixa) {
		this.proximaDataFaixa = proximaDataFaixa;
	}

	public Date getDataIndiceAnterior() {
		return dataIndiceAnterior;
	}

	public void setDataIndiceAnterior(Date dataIndiceAnterior) {
		this.dataIndiceAnterior = dataIndiceAnterior;
	}

	public boolean isEditavel() {
		return (this.dataIndice == null || this.dataIndice.equals(this.dataIndiceAnterior));
	}
}
