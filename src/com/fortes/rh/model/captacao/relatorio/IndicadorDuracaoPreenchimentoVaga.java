package com.fortes.rh.model.captacao.relatorio;

import java.io.Serializable;

import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;

public class IndicadorDuracaoPreenchimentoVaga implements Serializable
{
	private static final long serialVersionUID = -4851953131448751720L;
	
	private Estabelecimento estabelecimento;
	private AreaOrganizacional areaOrganizacional;
	private Cargo cargo;
	private Double mediaDias;
	private Integer qtdCandidatos;
	private Integer qtdVagas;
	private Integer qtdContratados;
	private MotivoSolicitacao motivoSolicitacao;
	private Integer qtdAberturas;
	
	public IndicadorDuracaoPreenchimentoVaga() {
		
	}
	
	// usado por getIndicadorMediaDiasPreenchimentoVagas
	public IndicadorDuracaoPreenchimentoVaga(Long estabelecimentoId, Long areaId, Long cargoId, Integer qtdContratados, Double mediaDias)
    {
    	this.estabelecimento = new Estabelecimento();
    	this.estabelecimento.setId(estabelecimentoId);
    	this.areaOrganizacional = new AreaOrganizacional();
    	this.areaOrganizacional.setId(areaId);
    	this.cargo = new Cargo(); 
    	this.cargo.setId(cargoId);
    	this.qtdContratados = qtdContratados;
    	
    	if (mediaDias < 0)
    		mediaDias = 0.0;
    	
    	this.mediaDias = mediaDias;
    }
	
	// usado por getIndicadorQtdCandidatos
	public IndicadorDuracaoPreenchimentoVaga(Long estabelecimentoId, Long areaId, Long cargoId, Integer qtdCandidatos)
    {
    	this.estabelecimento = new Estabelecimento();
    	this.estabelecimento.setId(estabelecimentoId);
    	this.areaOrganizacional = new AreaOrganizacional();
    	this.areaOrganizacional.setId(areaId);
    	this.cargo = new Cargo(); 
    	this.cargo.setId(cargoId);
    	this.qtdCandidatos = qtdCandidatos;
    }
	
	// usado por getIndicadorQtdVagas
	public IndicadorDuracaoPreenchimentoVaga(Integer qtdVagas, Long estabelecimentoId, Long areaId, Long cargoId)
    {
    	this.estabelecimento = new Estabelecimento();
    	this.estabelecimento.setId(estabelecimentoId);
    	this.areaOrganizacional = new AreaOrganizacional();
    	this.areaOrganizacional.setId(areaId);
    	this.cargo = new Cargo(); 
    	this.cargo.setId(cargoId);
    	this.qtdVagas = qtdVagas;
    }
	
	// usado por getIndicadorMotivosSolicitacao
	public IndicadorDuracaoPreenchimentoVaga(String estabelecimentoNome, Long areaId, String cargoNome, Long motivoSolicitacaoID, String motivoDescricao, Integer qtdAberturas)
	{
		this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setNome(estabelecimentoNome);
		this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaId);
		this.cargo = new Cargo();
		this.cargo.setNome(cargoNome);
		this.qtdAberturas = qtdAberturas;
		this.motivoSolicitacao = new MotivoSolicitacao();
		this.motivoSolicitacao.setId(motivoSolicitacaoID);
		this.motivoSolicitacao.setDescricao(motivoDescricao);
	}

	// usado por getIndicadorMotivosSolicitacao
	public IndicadorDuracaoPreenchimentoVaga(String estabelecimentoNome, Long motivoSolicitacaoID, String motivoDescricao, Integer qtdAberturas)
	{
		this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setNome(estabelecimentoNome);
		this.qtdAberturas = qtdAberturas;
		this.motivoSolicitacao = new MotivoSolicitacao();
		this.motivoSolicitacao.setId(motivoSolicitacaoID);
		this.motivoSolicitacao.setDescricao(motivoDescricao);
	}
	
	public Double getMediaDias()
	{
		return mediaDias;
	}
	
	public Integer getQtdCandidatos() {
		return qtdCandidatos;
	}
	public void setQtdCandidatos(Integer qtdCandidatos) {
		this.qtdCandidatos = qtdCandidatos;
	}
	public void setMediaDias(Double mediaDias) {
		this.mediaDias = mediaDias;
	}
	public Integer getQtdVagas() {
		return qtdVagas;
	}
	public void setQtdVagas(Integer qtdVagas) {
		this.qtdVagas = qtdVagas;
	}

	public Integer getQtdContratados() {
		return qtdContratados;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Integer getQtdAberturas() {
		return qtdAberturas;
	}

	public MotivoSolicitacao getMotivoSolicitacao() {
		return motivoSolicitacao;
	}
}