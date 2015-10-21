package com.fortes.rh.model.captacao;



public class MatrizCompetenciaNivelConfiguracao implements Cloneable
{
	private String competencia;
	private String nivel;
	private Boolean configuracaoFaixa;
	private Boolean possuiCriterio;
	private Boolean criterio;
	private Boolean configuracao;
	private Integer gap;
	private Integer nivelDaFaixa;
	
	public MatrizCompetenciaNivelConfiguracao(String competencia, String nivel, Boolean configuracaoFaixa, Boolean configuracao) {
		super();
		this.competencia = competencia;
		this.nivel = nivel;
		this.configuracaoFaixa = configuracaoFaixa;
		this.configuracao = configuracao;
	}
	
	public MatrizCompetenciaNivelConfiguracao(String competencia, String nivel, Boolean configuracaoFaixa, Boolean configuracao, Integer gap) {
		super();
		this.competencia = competencia;
		this.nivel = nivel;
		this.configuracaoFaixa = configuracaoFaixa;
		this.configuracao = configuracao;
		this.gap = gap;
	}
	
	public MatrizCompetenciaNivelConfiguracao(Integer nivelDaFaixa, String competencia, String nivel, Boolean configuracaoFaixa, Boolean configuracao) {
		super();
		this.nivelDaFaixa = nivelDaFaixa;
		this.competencia = competencia;
		this.nivel = nivel;
		this.configuracaoFaixa = configuracaoFaixa;
		this.configuracao = configuracao;
	}
	
	public MatrizCompetenciaNivelConfiguracao(String competencia, String nivel, Boolean configuracaoFaixa, Boolean configuracao, Boolean possuiCriterio, Boolean criterio) {
		this(competencia, nivel, configuracaoFaixa, configuracao);
		this.possuiCriterio = possuiCriterio;
		this.criterio = criterio;
	}

	public Object clone()
	{
		try
		{
			MatrizCompetenciaNivelConfiguracao clone = (MatrizCompetenciaNivelConfiguracao) super.clone();

			return clone;
		}
		catch (CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}
	
	public String getCompetencia() {
		return competencia;
	}
	
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	
	public String getNivel() {
		return nivel;
	}
	
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public Boolean getConfiguracaoFaixa() {
		return configuracaoFaixa;
	}
	
	public void setConfiguracaoFaixa(Boolean configuracaoFaixa) {
		this.configuracaoFaixa = configuracaoFaixa;
	}
	
	public Boolean getConfiguracao() {
		return configuracao;
	}
	
	public void setConfiguracao(Boolean configuracao) {
		this.configuracao = configuracao;
	}

	public Integer getGap() {
		return gap;
	}

	public void setGap(Integer gap) {
		this.gap = gap;
	}

	public Integer getNivelDaFaixa() {
		return nivelDaFaixa;
	}

	public void setNivelDaFaixa(Integer nivelDaFaixa) {
		this.nivelDaFaixa = nivelDaFaixa;
	}

	public Boolean getPossuiCriterio() {
		return possuiCriterio;
	}

	public void setPossuiCriterio(Boolean possuiCriterio) {
		this.possuiCriterio = possuiCriterio;
	}

	public Boolean getCriterio() {
		return criterio;
	}

	public void setCriterio(Boolean criterio) {
		this.criterio = criterio;
	}
}