package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="riscoambiente_sequence", allocationSize=1)
public class RiscoAmbiente extends AbstractModel implements Serializable
{
	private boolean epcEficaz;
	private Character periodicidadeExposicao;
	private String medidaDeSeguranca;
	
	@ManyToOne
	private HistoricoAmbiente historicoAmbiente;

	@ManyToOne
	private Risco risco;
	private Character grauDeRisco;
	
	@Transient
	private boolean epiEficaz;
	
	public RiscoAmbiente() { }
	
	public RiscoAmbiente(HistoricoAmbiente historicoAmbiente, Risco risco) 
	{
		this.historicoAmbiente = historicoAmbiente;
		this.risco = risco;
	}
	
	public RiscoAmbiente(Long id, boolean epcEficaz, Risco risco, String riscoDescricao, String riscoGrupoRisco, Date data, Ambiente ambiente)
	{
		setId(id);
		this.epcEficaz = epcEficaz;
		this.risco = risco;
		this.risco.setDescricao(riscoDescricao);
		this.risco.setGrupoRisco(riscoGrupoRisco);
		this.historicoAmbiente = new HistoricoAmbiente();
		this.historicoAmbiente.setData(data);
		this.historicoAmbiente.setAmbiente(ambiente);
	}
	
	public RiscoAmbiente(Long ambienteId, String ambienteNome, String riscoDescricao, String riscoGrupoRisco, Character grauDeRisco, Date data, Long id)
	{
		setId(id);
		this.grauDeRisco = grauDeRisco;
		this.risco = new Risco();
		this.risco.setDescricao(riscoDescricao);
		this.risco.setGrupoRisco(riscoGrupoRisco);
		this.historicoAmbiente = new HistoricoAmbiente();
		this.historicoAmbiente.setData(data);
		this.historicoAmbiente.setAmbiente(new Ambiente());
		this.historicoAmbiente.getAmbiente().setId(ambienteId);
		this.historicoAmbiente.getAmbiente().setNome(ambienteNome);
	}

	public boolean isEpiEficaz() {
		return epiEficaz;
	}

	public void setEpiEficaz(boolean epiEficaz) {
		this.epiEficaz = epiEficaz;
	}

	public boolean isEpcEficaz() {
		return epcEficaz;
	}

	public void setEpcEficaz(boolean epcEficaz) {
		this.epcEficaz = epcEficaz;
	}

	public HistoricoAmbiente getHistoricoAmbiente() {
		return historicoAmbiente;
	}

	public void setHistoricoAmbiente(HistoricoAmbiente historicoAmbiente) {
		this.historicoAmbiente = historicoAmbiente;
	}

	public Risco getRisco() {
		return risco;
	}

	public void setRisco(Risco risco) {
		this.risco = risco;
	}

	public Character getPeriodicidadeExposicao() {
		return periodicidadeExposicao;
	}

	public void setPeriodicidadeExposicao(Character periodicidadeExposicao) {
		this.periodicidadeExposicao = periodicidadeExposicao;
	}
	
	public Character getGrauDeRisco() {
		return grauDeRisco;
	}

	public void setGrauDeRisco(Character grauDeRisco) {
		this.grauDeRisco = grauDeRisco;
	}

	public String getMedidaDeSeguranca()
	{
		return medidaDeSeguranca;
	}
	
	public void setMedidaDeSeguranca(String medidaDeSeguranca)
	{
		this.medidaDeSeguranca = medidaDeSeguranca;
	}
}
