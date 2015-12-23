package com.fortes.rh.model.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.util.MathUtil;

public class ResultadoAvaliacaoDesempenho extends ResultadoQuestionario
{
	private static final long serialVersionUID = 1L;
	
	private Long avaliadoId;
	private String avaliadoNome;
	private String obsAvaliadores;
	private Double performance;
	private Double performanceAutoAvaliacao;
	private Double produtividade;
	private Colaborador colaborador;
	private Collection<Competencia> competencias = new ArrayList<Competencia>();
	
	public ResultadoAvaliacaoDesempenho(Long avaliadoId, String avaliadoNome, Double performance)
	{
		this.avaliadoId = avaliadoId;
		this.avaliadoNome = avaliadoNome;
		this.performance = performance;
	}

	public ResultadoAvaliacaoDesempenho() {
	}

	public Long getAvaliadoId() {
		return avaliadoId;
	}

	public String getAvaliadoNome() {
		return avaliadoNome;
	}

	public Double getPerformance() {
		return performance;
	}
	
	public Double getPerformanceCalculada() 
	{
		if(competencias.size() == 0)
			return 0.0;
		
		Double somaPerformance = 0.0;
		
		for (Competencia competencia : competencias) 
			somaPerformance += competencia.getPerformance();
		
		Double retorno = somaPerformance/competencias.size();
		
		if(this.produtividade == null)	
			return retorno;
		else
			return (retorno + this.produtividade)/2;
	}

	public String getPerformanceCalculadaFormatada() {
		return MathUtil.round(getPerformanceCalculada(), 2) + "%";
	}

	public void setPerformance(Double performance) {
		this.performance = performance;
	}

	public String getObsAvaliadores() {
		return obsAvaliadores;
	}

	public void setObsAvaliadores(String obsAvaliadores) {
		this.obsAvaliadores = obsAvaliadores;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Collection<Competencia> getCompetenciasGrafico() {
		return competencias;
	}
	
	public Collection<Competencia> getCompetencias() {
		return competencias;
	}
	
	public Collection<Competencia> getCompetenciasPerformances() {
		
		Collection<Competencia> competenciasPerformance = new ArrayList<Competencia>();
		
		Competencia competenciaAutoAvaliacao = new Competencia();
		competenciaAutoAvaliacao.setNome("Auto Avaliação");
		competenciaAutoAvaliacao.setPerformance(performanceAutoAvaliacao);
		competenciasPerformance.add(competenciaAutoAvaliacao);

		Competencia competencieAvaliacoes = new Competencia();
		competencieAvaliacoes.setNome("Avaliação");
		competencieAvaliacoes.setPerformance(getPerformanceCalculada());
		competenciasPerformance.add(competencieAvaliacoes);
		
		return competenciasPerformance;
	}
	

	public void setCompetencias(Collection<Competencia> competencias) {
		this.competencias = competencias;
	}

	public Double getPerformanceAutoAvaliacao() {
		return performanceAutoAvaliacao;
	}

	public void setPerformanceAutoAvaliacao(Double performanceAutoAvaliacao) {
		this.performanceAutoAvaliacao = performanceAutoAvaliacao;
	}

	public String getPerformanceAutoAvaliacaoFormatado() {
		return MathUtil.round(performanceAutoAvaliacao, 2) + "%";
	}

	public Double getProdutividade() {
		return produtividade;
	}

	public void setProdutividade(Double produtividade) {
		this.produtividade = produtividade;
	}

	public String getProdutividadeFormatada() {
		return MathUtil.round(produtividade, 2) + "%";
	}
}
