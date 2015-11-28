package com.fortes.rh.model.sesmt.relatorio;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;

public class SolicitacaoEpiItemVO
{
	private Long solicitacaoEpiId;
	private Long empresaId;
	private Long estabelecimentoId;
	private String estabelecimentoNome;
	private Long colaboradorId;
	private String colaboradorMatricula;
	private String colaboradorNome;
	private boolean colaboradorDesligado;
	@Temporal(TemporalType.DATE)
	private Date solicitacaoEpiData;
	private String cargoNome;
	private Integer qtdSolicitadoTotal;
	private Integer qtdEntregue;
	private String solicitacaoEpiSituacaoEntrega;
	private String epiNome;
	private Long itemId;
	private Integer qtdSolicitadoItem;
	private Integer qtdEntregueItem;
	private String descricaoMotivoSolicitacaoEpi;
	private String descricaoTamanhoEpi;
	private String solicitacaoEpiSituacaoDevolucao;
	private Integer qtdDevolvida;
	private Integer qtdDevolvidaItem;

	public Long getSolicitacaoEpiId() {
		return solicitacaoEpiId;
	}
	
	public void setSolicitacaoEpiId(Long solicitacaoEpiId) {
		this.solicitacaoEpiId = solicitacaoEpiId;
	}
	
	public Long getEmpresaId() {
		return empresaId;
	}
	
	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
	
	public Long getEstabelecimentoId() {
		return estabelecimentoId;
	}
	
	public void setEstabelecimentoId(Long estabelecimentoId) {
		this.estabelecimentoId = estabelecimentoId;
	}
	
	public String getEstabelecimentoNome() {
		return estabelecimentoNome;
	}
	
	public void setEstabelecimentoNome(String estabelecimentoNome) {
		this.estabelecimentoNome = estabelecimentoNome;
	}
	
	public String getColaboradorMatricula() {
		return colaboradorMatricula;
	}
	
	public void setColaboradorMatricula(String colaboradorMatricula) {
		this.colaboradorMatricula = colaboradorMatricula;
	}
	
	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public String getColaboradorNomeDesligado() {
		if (this.colaboradorDesligado)
			return colaboradorNome + " (Desligado)";
		
		return colaboradorNome;
	}
	
	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}
	
	public boolean isColaboradorDesligado() {
		return colaboradorDesligado;
	}

	public void setColaboradorDesligado(boolean colaboradorDesligado) {
		this.colaboradorDesligado = colaboradorDesligado;
	}
	
	public Date getSolicitacaoEpiData() {
		return solicitacaoEpiData;
	}
	
	public void setSolicitacaoEpiData(Date solicitacaoEpiData) {
		this.solicitacaoEpiData = solicitacaoEpiData;
	}
	
	public String getCargoNome() {
		return cargoNome;
	}
	
	public void setCargoNome(String cargoNome) {
		this.cargoNome = cargoNome;
	}
	
	public Integer getQtdEntregue() {
		return qtdEntregue;
	}
	
	public void setQtdEntregue(Integer qtdEntregue) {
		this.qtdEntregue = qtdEntregue;
	}
	
	public Integer getQtdSolicitadoTotal() {
		return qtdSolicitadoTotal;
	}
	
	public void setQtdSolicitadoTotal(Integer qtdSolicitadoTotal) {
		this.qtdSolicitadoTotal = qtdSolicitadoTotal;
	}
	
	public String getEpiNome() {
		return epiNome;
	}
	
	public void setEpiNome(String epiNome) {
		this.epiNome = epiNome;
	}
	
	public Integer getQtdSolicitadoItem() {
		return qtdSolicitadoItem;
	}
	
	public void setQtdSolicitadoItem(Integer qtdSolicitadoItem) {
		this.qtdSolicitadoItem = qtdSolicitadoItem;
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public String getSolicitacaoEpiSituacaoEntrega() {
		return solicitacaoEpiSituacaoEntrega;
	}

	public void setSolicitacaoEpiSituacao(String solicitacaoEpiSituacaoEntrega) {
		this.solicitacaoEpiSituacaoEntrega = solicitacaoEpiSituacaoEntrega;
	}
	
	public String getSituacaoDescricaoEntrega()
	{
		return SituacaoSolicitacaoEpi.getDescricao(solicitacaoEpiSituacaoEntrega);
	}

	public String getSituacaoDescricaoDevolucao()
	{
		return SituacaoSolicitacaoEpi.getDescricao(solicitacaoEpiSituacaoDevolucao);
	}
	
	public Long getColaboradorId()
	{
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId)
	{
		this.colaboradorId = colaboradorId;
	}

	public Integer getQtdEntregueItem()
	{
		return qtdEntregueItem;
	}

	public void setQtdEntregueItem(Integer qtdEntregueItem)
	{
		this.qtdEntregueItem = qtdEntregueItem;
	}

	public String getDescricaoMotivoSolicitacaoEpi()
	{
		return descricaoMotivoSolicitacaoEpi;
	}

	public void setDescricaoMotivoSolicitacaoEpi(String descricaoMotivoSolicitacaoEpi)
	{
		this.descricaoMotivoSolicitacaoEpi = descricaoMotivoSolicitacaoEpi;
	}

	public void setDescricaoTamanhoEpi(String descricaoTamanhoEpi) {
		this.descricaoTamanhoEpi = descricaoTamanhoEpi;
	}
	
	public String getEpiNomeComTamanho(){
		if(this.descricaoTamanhoEpi != null && !this.descricaoTamanhoEpi.isEmpty())
			return getEpiNome() + " (" + descricaoTamanhoEpi + ")";
		else return getEpiNome();
	}

	public String getSolicitacaoEpiSituacaoDevolucao() {
		return solicitacaoEpiSituacaoDevolucao;
	}

	public void setSolicitacaoEpiSituacaoDevolucao(String solicitacaoEpiSituacaoDevolucao) {
		this.solicitacaoEpiSituacaoDevolucao = solicitacaoEpiSituacaoDevolucao;
	}

	public Integer getQtdDevolvida() {
		return qtdDevolvida;
	}

	public void setQtdDevolvida(Integer qtdDevolvida) {
		this.qtdDevolvida = qtdDevolvida;
	}

	public Integer getQtdDevolvidaItem() {
		return qtdDevolvidaItem;
	}

	public void setQtdDevolvidaItem(Integer qtdDevolvidaItem) {
		this.qtdDevolvidaItem = qtdDevolvidaItem;
	}
}