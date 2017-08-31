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
@SequenceGenerator(name="sequence", sequenceName="riscofuncao_sequence", allocationSize=1)
public class RiscoFuncao extends AbstractModel implements Serializable
{
	private boolean epcEficaz;
	private Character periodicidadeExposicao;
	private String medidaDeSeguranca;
	
	@ManyToOne
	private HistoricoFuncao historicoFuncao;

	@ManyToOne
	private Risco risco;
	
	@Transient
	private boolean epiEficaz;
	
	public RiscoFuncao() { }
	
	public RiscoFuncao(HistoricoFuncao historicoFuncao, Risco risco) 
	{
		this.historicoFuncao= historicoFuncao;
		this.risco = risco;
	}
	
	public RiscoFuncao(Long id, boolean epcEficaz, Risco risco, String riscoDescricao, String riscoGrupoRisco, Date data, Funcao funcao)
	{
		setId(id);
		this.epcEficaz = epcEficaz;
		this.risco = risco;
		this.risco.setDescricao(riscoDescricao);
		this.risco.setGrupoRisco(riscoGrupoRisco);
		this.historicoFuncao = new HistoricoFuncao();
		this.historicoFuncao.setData(data);
		this.historicoFuncao.setFuncao(funcao);
	}

	public RiscoFuncao(Long id) {
		this.setId(id);
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

	public HistoricoFuncao getHistoricoFuncao() {
		return historicoFuncao;
	}

	public void setHistoricoFuncao(HistoricoFuncao historicoFuncao) {
		this.historicoFuncao = historicoFuncao;
	}
	
	public String getMedidaDeSeguranca()
	{
		return medidaDeSeguranca;
	}

	public void setMedidaDeSeguranca(String medidaDeSeguranca)
	{
		this.medidaDeSeguranca = medidaDeSeguranca;
	}
	public void setRiscoDescricao(String riscoDescricao){
		if(this.risco == null)
			this.risco = new Risco();
		risco.setDescricao(riscoDescricao);
	}
}
