package com.fortes.rh.model.geral.relatorio;

import java.util.ArrayList;
import java.util.Collection;

public class TurnOverCollection
{
	private Long empresaId;
	private String empresaNome;
	private String formula;
	private Double qtdAdmitidos;
	private Double qtdDemitidos;
	private String nomeAreaOuCargo;
	private Collection<TurnOver> turnOvers = new ArrayList<TurnOver>();
	
	public TurnOverCollection() 
	{
	}

	public TurnOverCollection(Collection<TurnOver> turnOvers) 
	{
		this.turnOvers = turnOvers;
	}
	
	public TurnOverCollection(Long empresaId, Collection<TurnOver> turnOvers) 
	{
		this.empresaId = empresaId;
		this.turnOvers = turnOvers;
	}

	public Double getMedia()
	{
		Double soma = 0D;
		for (TurnOver turnOverTmp : turnOvers)
		{
			soma += turnOverTmp.getTurnOver();
		}

		return soma / turnOvers.size();
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}
	
	public String getEmpresaNome() {
		return empresaNome;
	}

	public Double getQtdAdmitidos() {
		return qtdAdmitidos;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setQtdAdmitidos(Double qtdAdmitidos) {
		this.qtdAdmitidos = qtdAdmitidos;
	}

	public Double getQtdDemitidos() {
		return qtdDemitidos;
	}

	public void setQtdDemitidos(Double qtdDemitidos) {
		this.qtdDemitidos = qtdDemitidos;
	}
	
	public Collection<TurnOver> getTurnOvers() {
		return turnOvers;
	}

	public void setTurnOvers(Collection<TurnOver> turnOvers) {
		this.turnOvers = turnOvers;
	}

	public String getNomeAreaOuCargo() {
		return nomeAreaOuCargo;
	}

	public void setNomeAreaOuCargo(String nomeAreaOuCargo) {
		this.nomeAreaOuCargo = nomeAreaOuCargo;
	}
}
