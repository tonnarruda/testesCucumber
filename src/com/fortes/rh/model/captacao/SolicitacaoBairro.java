package com.fortes.rh.model.captacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="solicitacao_bairro")
public class SolicitacaoBairro implements Serializable
{
	@Id
	@Column( name="solicitacao_id", unique=true, nullable=false)
	private Long solicitacao;
	@Id
	@Column( name="bairros_id", unique=true, nullable=false)
	private Long bairro;
	public Long getBairro()
	{
		return bairro;
	}
	public void setBairro(Long bairro)
	{
		this.bairro = bairro;
	}
	public Long getSolicitacao()
	{
		return solicitacao;
	}
	public void setSolicitacao(Long solicitacao)
	{
		this.solicitacao = solicitacao;
	}	
}