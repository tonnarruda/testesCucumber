package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="historicoambiente_sequence", allocationSize=1)
public class HistoricoAmbiente extends AbstractModel implements Serializable
{
	@ManyToOne
	private Ambiente ambiente;
	@Lob
	private String descricao;
	@Temporal(TemporalType.DATE)
	private Date data;
	@Temporal(TemporalType.DATE)
	private Date dataInativo;
	
	@Column(length=40)
	private String tempoExposicao;
	
	@ManyToMany(fetch=FetchType.EAGER)
    private Collection<Epc> epcs;
	
	@OneToMany(mappedBy="historicoAmbiente", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Collection<RiscoAmbiente> riscoAmbientes;
	
	@Transient
	private Date dataProximoHistorico;

	public HistoricoAmbiente()
	{	}

	public HistoricoAmbiente(String descricao, String ambienteNome)
	{
		this.descricao = descricao;
		this.ambiente = new Ambiente();
		this.ambiente.setNome(ambienteNome);
	}
	
	public HistoricoAmbiente(Long id, Date data, Ambiente ambiente)
	{
		setId(id);
		this.data = data;
		this.ambiente = ambiente;
	}

	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public Ambiente getAmbiente()
	{
		return ambiente;
	}
	public void setAmbiente(Ambiente ambiente)
	{
		this.ambiente = ambiente;
	}
	public Date getDataInativo()
	{
		return dataInativo;
	}
	public void setDataInativo(Date dataInativo)
	{
		this.dataInativo = dataInativo;
	}

	public Collection<RiscoAmbiente> getRiscoAmbientes() {
		return riscoAmbientes;
	}

	public void setRiscoAmbientes(Collection<RiscoAmbiente> riscoAmbientes) {
		this.riscoAmbientes = riscoAmbientes;
	}

	public Collection<Epc> getEpcs() {
		return epcs;
	}

	public void setEpcs(Collection<Epc> epcs) {
		this.epcs = epcs;
	}

	public String getTempoExposicao() {
		return tempoExposicao;
	}

	public void setTempoExposicao(String tempoExposicao) {
		this.tempoExposicao = tempoExposicao;
	}

	/**
	 * variável <b>Transiente</b>.
	 */
	public Date getDataProximoHistorico() {
		return dataProximoHistorico;
	}

	public void setDataProximoHistorico(Date dataProximoHistorico) {
		this.dataProximoHistorico = dataProximoHistorico;
	}
}