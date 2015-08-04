package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.RiscoAmbiente;

public class MapaDeRisco {

	private Ambiente ambiente;
	
	private Collection<RiscoAmbiente> riscoAmbientes;
	
	private Collection<Epi> epis;
	
	private int qtdDeColaboradoresMulheres;
	
	private int qtdDeColaboradoresHomens;
	
	public MapaDeRisco() {}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Collection<Epi> getEpis() {
		return epis;
	}

	public void setEpis(Collection<Epi> epis) {
		this.epis = epis;
	}

	public int getQtdDeColaboradoresMulheres() {
		return qtdDeColaboradoresMulheres;
	}

	public void setQtdDeColaboradoresMulheres(int qtdDeColaboradoresMulheres) {
		this.qtdDeColaboradoresMulheres = qtdDeColaboradoresMulheres;
	}

	public int getQtdDeColaboradoresHomens() {
		return qtdDeColaboradoresHomens;
	}

	public void setQtdDeColaboradoresHomens(int qtdDeColaboradoresHomens) {
		this.qtdDeColaboradoresHomens = qtdDeColaboradoresHomens;
	}

	public Collection<RiscoAmbiente> getRiscoAmbientes() {
		return riscoAmbientes;
	}

	public void setRiscoAmbientes(Collection<RiscoAmbiente> riscoAmbientes) {
		this.riscoAmbientes = riscoAmbientes;
	}
}
