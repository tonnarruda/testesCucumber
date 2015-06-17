package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="pausapreenchimentovagas_sequence", allocationSize=1)
public class PausaPreenchimentoVagas extends AbstractModel implements Serializable
{
	@ManyToOne
	private Solicitacao solicitacao;
	
	@Temporal(TemporalType.DATE)
	private Date dataPausa;
	
	@Temporal(TemporalType.DATE)
	private Date dataReinicio;

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Date getDataPausa() {
		return dataPausa;
	}

	public void setDataPausa(Date dataPausa) {
		this.dataPausa = dataPausa;
	}

	public Date getDataReinicio() {
		return dataReinicio;
	}

	public void setDataReinicio(Date dataReinicio) {
		this.dataReinicio = dataReinicio;
	}

}