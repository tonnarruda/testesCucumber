package com.fortes.rh.model.captacao;


public class MatrizCompetenciaNivelConfiguracao
{
	private String competencia;
	private String nivel;
	private Boolean configuracaoFaixa;
	private Boolean configuracao;
	
	public MatrizCompetenciaNivelConfiguracao(String competencia, String nivel, Boolean configuracaoFaixa, Boolean configuracao) {
		super();
		this.competencia = competencia;
		this.nivel = nivel;
		this.configuracaoFaixa = configuracaoFaixa;
		this.configuracao = configuracao;
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
	
	
}