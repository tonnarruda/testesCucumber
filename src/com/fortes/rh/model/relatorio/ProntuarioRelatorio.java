package com.fortes.rh.model.relatorio;

import java.util.Collection;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Prontuario;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.util.DateUtil;


public class ProntuarioRelatorio
{
	private Colaborador colaborador;
	private String colaboradorNome = "";
	private String colaboradorCargo = "";
	private String dataAdmissao = "";
	private DateUtil data;
	private Collection<Prontuario> prontuarios;
	private Collection<RealizacaoExame> realizacaoExames;
	private Collection<FichaMedica> fichasMedicas;
	Collection<SolicitacaoExame> consultas;
	Collection<SolicitacaoExame> atestadosExternos;
	Collection<ColaboradorAfastamento> afastamentos;
	private boolean possuiResultados;

	public ProntuarioRelatorio()
	{
	}

	public ProntuarioRelatorio(Colaborador colaborador, Collection<Prontuario> prontuarios, Collection<RealizacaoExame> realizacaoExames, Collection<FichaMedica> fichasMedicas, Collection<SolicitacaoExame> consultas, Collection<SolicitacaoExame> atestadosExternos, Collection<ColaboradorAfastamento> afastamentos)
	{
		this.colaborador = colaborador;
		this.prontuarios = prontuarios;
		this.realizacaoExames = realizacaoExames;
		this.fichasMedicas = fichasMedicas;
		this.consultas = consultas;
		this.atestadosExternos = atestadosExternos;
		this.afastamentos = afastamentos;
		
		setColaboradorNome(colaborador.getNome()+ " (" + colaborador.getNomeComercial() + ")");
		setColaboradorCargo(colaborador.getFaixaSalarial().getNomeDoCargo());
		dataAdmissao = data.formataDiaMesAno(colaborador.getDataAdmissao());
		
		if (colaborador.getNome().equals(colaborador.getNomeComercial()))
			setColaboradorNome(colaborador.getNome());
			
		
		this.possuiResultados = ((prontuarios != null && !prontuarios.isEmpty())
				|| (realizacaoExames != null && !realizacaoExames.isEmpty())
				||	(fichasMedicas != null && !fichasMedicas.isEmpty())
				||	(colaborador != null)
				||	(atestadosExternos != null && !atestadosExternos.isEmpty())
				||	(consultas != null && !consultas.isEmpty())
				||	(afastamentos != null && !afastamentos.isEmpty()));

	}
	public Collection<FichaMedica> getFichasMedicas()
	{
		return fichasMedicas;
	}
	public Collection<Prontuario> getProntuarios()
	{
		return prontuarios;
	}
	public Collection<RealizacaoExame> getRealizacaoExames()
	{
		return realizacaoExames;
	}

	public boolean getPossuiResultados()
	{
		return possuiResultados;
	}

	public Collection<SolicitacaoExame> getConsultas() {
		return consultas;
	}

	public Collection<SolicitacaoExame> getAtestadosExternos() {
		return atestadosExternos;
	}
	public boolean getPossuiFichasMedicas()
	{
		return (fichasMedicas != null && !fichasMedicas.isEmpty()); 
	}
	public boolean getPossuiProntuarios()
	{
		return (prontuarios != null && !prontuarios.isEmpty());
	}
	public boolean getPossuiExamesRealizados()
	{
		return (realizacaoExames != null && !realizacaoExames.isEmpty());
	}
	public boolean getPossuiAtestados()
	{
		return (atestadosExternos != null && !atestadosExternos.isEmpty());
	}
	public boolean getPossuiConsultas()
	{
		return (consultas != null && !consultas.isEmpty());
	}
	public boolean getPossuiAfastamentos()
	{
		return (afastamentos != null && !afastamentos.isEmpty());
	}
	public boolean getColaborador()
	{
		return (colaborador != null && !afastamentos.isEmpty());
	}

	public Collection<ColaboradorAfastamento> getAfastamentos() {
		return afastamentos;
	}
	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setColaboradorNome(String colaboradorNome) {
		this.colaboradorNome = colaboradorNome;
	}

	public String getColaboradorCargo() {
		return colaboradorCargo;
	}

	public void setColaboradorCargo(String colaboradorCargo) {
		this.colaboradorCargo = colaboradorCargo;
	}
	public String getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}	
}