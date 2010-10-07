package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.HistoricoFuncao;


public class PCMSO 
{
	//flags
	private boolean exibirAgenda;
	private boolean exibirDistColaboradorSetor;
	private boolean exibirRiscos;
	private boolean exibirEpis;
	private boolean exibirExames;
	private boolean exibirAcidentes;
	
	private Collection<Agenda> agendas;
	private Collection<AreaOrganizacional> setores;
	private Integer qtdTotalColaboradores=0;
	private Collection<ExameAnualRelatorio> exames;
	private Collection<CatRelatorioAnual> cats;
	private Collection<HistoricoFuncao> historicoFuncaos;
	
	public PCMSO() {
	}
	
	public PCMSO(boolean exibirAgenda, boolean exibirDistColaboradorSetor, boolean exibirRiscos, boolean exibirEpis, boolean exibirExames, boolean exibirAcidentes) {
		
		this.exibirAgenda = exibirAgenda;
		this.exibirDistColaboradorSetor = exibirDistColaboradorSetor;
		this.exibirRiscos = exibirRiscos;
		this.exibirEpis = exibirEpis;
		this.exibirExames = exibirExames;
		this.exibirAcidentes = exibirAcidentes;
	}

	public void setSetores(Collection<AreaOrganizacional> setores) 
	{
		this.setores = setores;
		setQtdTotalColaboradores();
	}
	
	public void setQtdTotalColaboradores() 
	{
		for (AreaOrganizacional area : setores) {
			
			if (area.getColaboradorCount() != null)
				qtdTotalColaboradores += area.getColaboradorCount();
		}
		
	}
	
	public Integer getQtdTotalColaboradores() {
		return qtdTotalColaboradores;
	}
	
	public Collection<Agenda> getAgendas()
	{
		return agendas;
	}

	public void setAgendas(Collection<Agenda> agendas)
	{
		this.agendas = agendas;
	}

	public Collection<AreaOrganizacional> getSetores() {
		return setores;
	}

	public boolean isExibirAgenda() {
		return exibirAgenda;
	}

	public void setExibirAgenda(boolean exibirAgenda) {
		this.exibirAgenda = exibirAgenda;
	}

	public boolean isExibirDistColaboradorSetor() {
		return exibirDistColaboradorSetor;
	}

	public void setExibirDistColaboradorSetor(boolean exibirDistColaboradorSetor) {
		this.exibirDistColaboradorSetor = exibirDistColaboradorSetor;
	}

	public boolean isExibirRiscos() {
		return exibirRiscos;
	}

	public void setExibirRiscos(boolean exibirRiscos) {
		this.exibirRiscos = exibirRiscos;
	}

	public boolean isExibirEpis() {
		return exibirEpis;
	}

	public void setExibirEpis(boolean exibirEpis) {
		this.exibirEpis = exibirEpis;
	}

	public boolean isExibirExames() {
		return exibirExames;
	}

	public void setExibirExames(boolean exibirExames) {
		this.exibirExames = exibirExames;
	}

	public boolean isExibirAcidentes() {
		return exibirAcidentes;
	}

	public void setExibirAcidentes(boolean exibirAcidentes) {
		this.exibirAcidentes = exibirAcidentes;
	}

	public Collection<ExameAnualRelatorio> getExames() {
		return exames;
	}

	public void setExames(Collection<ExameAnualRelatorio> exames) {
		this.exames = exames;
	}

	public Collection<CatRelatorioAnual> getCats() {
		return cats;
	}

	public void setCats(Collection<CatRelatorioAnual> cats) {
		this.cats = cats;
	}

	public Collection<HistoricoFuncao> getHistoricoFuncaos()
	{
		return historicoFuncaos;
	}

	public void setHistoricoFuncaos(Collection<HistoricoFuncao> historicoFuncaos)
	{
		this.historicoFuncaos = historicoFuncaos;
	}
}
