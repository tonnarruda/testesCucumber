package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="solicitacaoEpiItemDevolucao_sequence", allocationSize=1)
public class SolicitacaoEpiItemDevolucao extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.EAGER)
	private SolicitacaoEpiItem solicitacaoEpiItem;

	private Integer qtdDevolvida=0;
	
	@Temporal(TemporalType.DATE)
	private Date dataDevolucao = null;
	
	private String observacao;

	@Transient
	private int qtdEntregue;
	
	public SolicitacaoEpiItem getSolicitacaoEpiItem() {
		return solicitacaoEpiItem;
	}

	public void setSolicitacaoEpiItem(SolicitacaoEpiItem solicitacaoEpiItem) {
		this.solicitacaoEpiItem = solicitacaoEpiItem;
	}

	public Integer getQtdDevolvida() {
		return qtdDevolvida;
	}

	public void setQtdDevolvida(Integer qtdDevolvida) {
		this.qtdDevolvida = qtdDevolvida;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	
	public String getDataDevolucaoFormatada() {
		return DateUtil.formataDiaMesAno(dataDevolucao);
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getQtdEntregue() {
		return qtdEntregue;
	}

	public void setQtdEntregue(int qtdEntregue) {
		this.qtdEntregue = qtdEntregue;
	}
}
