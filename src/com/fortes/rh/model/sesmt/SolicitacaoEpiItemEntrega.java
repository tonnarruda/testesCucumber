package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="solicitacaoepiitementrega_sequence", allocationSize=1)
public class SolicitacaoEpiItemEntrega extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.EAGER)
	private SolicitacaoEpiItem solicitacaoEpiItem;

	private Integer qtdEntregue=0;
	
	@Temporal(TemporalType.DATE)
	private Date dataEntrega = null;

	public SolicitacaoEpiItem getSolicitacaoEpiItem() {
		return solicitacaoEpiItem;
	}

	public void setSolicitacaoEpiItem(SolicitacaoEpiItem solicitacaoEpiItem) {
		this.solicitacaoEpiItem = solicitacaoEpiItem;
	}

	public Integer getQtdEntregue() {
		return qtdEntregue;
	}

	public void setQtdEntregue(Integer qtdEntregue) {
		this.qtdEntregue = qtdEntregue;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
}