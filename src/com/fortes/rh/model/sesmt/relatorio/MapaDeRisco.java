package com.fortes.rh.model.sesmt.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.fortes.rh.model.dicionario.TipoRisco;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.RiscoAmbiente;

public class MapaDeRisco {

	private Ambiente ambiente;
	private Collection<RiscoAmbiente> riscoAmbientes;
	private Collection<Epi> epis;
	private int qtdDeColaboradoresMulheres = 0;
	private int qtdDeColaboradoresHomens = 0;
	HashMap<Character, Integer> mapGrausRisco = new HashMap<Character, Integer>();
	
	public MapaDeRisco() {
		mapGrausRisco.put('P',1);
		mapGrausRisco.put('M',2);
		mapGrausRisco.put('G',3);
	}

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
	
	public Integer getQtdTiposRiscos() 
	{
		Collection<String> tiposRisco = new ArrayList<String>();
		for (RiscoAmbiente riscoAmbiente : riscoAmbientes) {
			if(!tiposRisco.contains(riscoAmbiente.getRisco().getGrupoRisco()))
				tiposRisco.add(riscoAmbiente.getRisco().getGrupoRisco());
		}
		
		return tiposRisco.size();
	}
	
	public String getEpisNomes() 
	{
		if(epis.isEmpty())
			return "";
		
		String episNome = "";
		for (Epi epi : epis) {
			episNome += epi.getNome() + ", ";
		}
		
		return episNome.substring(0, episNome.length()-3) + ".";
	}
	
	public String getAcidentes()
	{
		return getRiscos(TipoRisco.ACIDENTE);
	}
	
	public String getErgonomico()
	{
		return getRiscos(TipoRisco.ERGONOMICO);
	}
	
	public String getBiologico()
	{
		return getRiscos(TipoRisco.BIOLOGICO);
	}
	
	public String getQuimico()
	{
		return getRiscos(TipoRisco.QUIMICO);
	}
	
	public String getFisico()
	{
		return getRiscos(TipoRisco.FISICO);
	}
	
	private String getRiscos(String tipo)
	{
		String listaNome = "";
		for (RiscoAmbiente riscoAmbiente : riscoAmbientes) {
			if(tipo.equals(riscoAmbiente.getRisco().getGrupoRisco()))
				listaNome +=  riscoAmbiente.getRisco().getDescricao()  + ", ";
		}
		if(listaNome.length() == 0)
			return "";

		return listaNome.substring(0, listaNome.length()-2) + ".";
	}
	
	public Integer getGrauAcidente()
	{
		return getGrau(TipoRisco.ACIDENTE);
	}
	
	public Integer getGrauErgonomico()
	{
		return getGrau(TipoRisco.ERGONOMICO);
	}
	
	public Integer getGrauBiologico()
	{
		return getGrau(TipoRisco.BIOLOGICO);
	}
	
	public Integer getGrauQuimico()
	{
		return getGrau(TipoRisco.QUIMICO);
	}
	
	public Integer getGrauFisico()
	{
		return getGrau(TipoRisco.FISICO);
	}
	
	private Integer getGrau(String tipo)
	{
		Integer grau = 0;

		for (RiscoAmbiente riscoAmbiente : riscoAmbientes) 
		{
			if(tipo.equals(riscoAmbiente.getRisco().getGrupoRisco()) && mapGrausRisco.get(riscoAmbiente.getGrauDeRisco()) > grau)
				grau = mapGrausRisco.get(riscoAmbiente.getGrauDeRisco());
		}

		return grau;
	}

	public String getRiscoAndGrau(){
		String risco = "";
		
		if(getGrauAcidente() != 0)
			risco = ";A-" + getGrauAcidente();
		if(getGrauErgonomico() != 0)
			risco += ";E-" + getGrauErgonomico();
		if(getGrauBiologico() != 0)
			risco += ";B-" + getGrauBiologico();
		if(getGrauQuimico() != 0)
			risco += ";Q-" + getGrauQuimico();
		if (getGrauFisico() != 0)
			risco += ";F-" + getGrauFisico();
		
		return risco.substring(1);
	}
}
