package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@Table(name="solicitacaoepi_item")
@SequenceGenerator(name="sequence", sequenceName="solicitacaoepi_item_sequence", allocationSize=1)
public class SolicitacaoEpiItem extends AbstractModel implements Serializable
{
	@ManyToOne(fetch = FetchType.EAGER)
	private Epi epi;

	@ManyToOne(fetch = FetchType.EAGER)
	private SolicitacaoEpi solicitacaoEpi;

	private Integer qtdSolicitado=0;
	
	@OneToMany(mappedBy="solicitacaoEpiItem", cascade=CascadeType.ALL)
	private Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas;

	public void setProjectionSolicitacaoEpiId(Long id)
	{
		if (this.solicitacaoEpi == null)
			this.solicitacaoEpi = new SolicitacaoEpi();

		this.solicitacaoEpi.setId(id);
	}

	public void setProjectionSolicitacaoEpiData(Date data)
	{
		if (this.solicitacaoEpi == null)
			this.solicitacaoEpi = new SolicitacaoEpi();

		this.solicitacaoEpi.setData(data);
	}

	public void setProjectionEpiId(Long id)
	{
		if (this.epi == null)
			this.epi = new Epi();

		this.epi.setId(id);
	}

	public void setProjectionEpiNome(String nome)
	{
		if (this.epi == null)
			this.epi = new Epi();

		this.epi.setNome(nome);
	}

	public Epi getEpi()
	{
		return epi;
	}
	
	public void setEpi(Epi epi)
	{
		this.epi = epi;
	}
	
	public Integer getQtdSolicitado()
	{
		return qtdSolicitado;
	}
	
	public void setQtdSolicitado(Integer qtdSolicitado)
	{
		this.qtdSolicitado = qtdSolicitado;
	}
	
	public SolicitacaoEpi getSolicitacaoEpi()
	{
		return solicitacaoEpi;
	}
	
	public void setSolicitacaoEpi(SolicitacaoEpi solicitacaoEpi)
	{
		this.solicitacaoEpi = solicitacaoEpi;
	}

	public Collection<SolicitacaoEpiItemEntrega> getSolicitacaoEpiItemEntregas() {
		return solicitacaoEpiItemEntregas;
	}

	public void setSolicitacaoEpiItemEntregas(Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas) {
		this.solicitacaoEpiItemEntregas = solicitacaoEpiItemEntregas;
	}
}