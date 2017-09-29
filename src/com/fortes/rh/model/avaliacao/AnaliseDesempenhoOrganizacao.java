package com.fortes.rh.model.avaliacao;

import java.util.LinkedHashMap;

public class AnaliseDesempenhoOrganizacao 
{
	public static final String POR_CARGO = "CARGO";
	public static final String POR_AREA = "AREA";
	public static final String POR_EMPRESA = "EMPRESA";
	
	private String agrupador;
	private String competenciaNome;
	private Double performance;

	public AnaliseDesempenhoOrganizacao() {
		super();
	}

	public AnaliseDesempenhoOrganizacao(String competenciaNome, String agrupador, Double performance) {
		this.competenciaNome = competenciaNome;
		this.agrupador = agrupador;
		this.performance = performance;
	}
	
	public String getAgrupador() {
		return agrupador;
	}

	public void setAgrupador(String agrupador) {
		this.agrupador = agrupador;
	}

	public String getCompetenciaNome() {
		return competenciaNome;
	}

	public void setCompetenciaNome(String competenciaNome) {
		this.competenciaNome = competenciaNome;
	}

	public Double getPerformance() {
		return performance;
	}
	
	public void setPerformance(Double performance) {
		this.performance = performance;
	}
	
	public LinkedHashMap<String, String> getListaAgrupamentoDasCompetencias() {
		LinkedHashMap<String, String> lista = new LinkedHashMap<String, String>();
		lista.put(POR_EMPRESA, "Empresa");
		lista.put(POR_AREA, "Área Organizacional");
		lista.put(POR_CARGO, "Cargo");
		
		return lista;
	}
	
	public String getAgrupamento(String agrupamento) {
		return this.getListaAgrupamentoDasCompetencias().get(agrupamento);
	}
}
