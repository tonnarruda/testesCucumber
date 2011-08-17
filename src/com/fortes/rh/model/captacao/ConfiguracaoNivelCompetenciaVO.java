package com.fortes.rh.model.captacao;

import java.util.Collection;

import com.fortes.rh.util.MathUtil;

/**
 * Utilizado para gerar Relatorio de configurações de nivel de experiencia
 */
public class ConfiguracaoNivelCompetenciaVO
{

	private String nome;
	private Integer totalPontos;
	private Integer totalPontosFaixa;
	private Collection<MatrizCompetenciaNivelConfiguracao> matrizes;

	public ConfiguracaoNivelCompetenciaVO() {
		super();
	}
	public ConfiguracaoNivelCompetenciaVO(String nome, Collection<MatrizCompetenciaNivelConfiguracao> matrizes) {
		super();
		this.nome = nome;
		this.matrizes = matrizes;
	}
	
	public void somaTotalPontos(int totalPontos)
	{
		if (this.totalPontos == null) this.totalPontos = 0;
		this.totalPontos = this.totalPontos + totalPontos;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isConfiguracaoFaixa() {
		return nome == null;
	}
	
	public boolean isConfiguracaoColaboradorOuCandidato() {
		return nome != null;
	}
	
	public Collection<MatrizCompetenciaNivelConfiguracao> getMatrizes() {
		return matrizes;
	}

	public void setMatrizes(Collection<MatrizCompetenciaNivelConfiguracao> matrizes) {
		this.matrizes = matrizes;
	}
	
	public void setTotalPontos(Integer totalPontos) {
		this.totalPontos = totalPontos;
	}
	
	public void setTotalPontosFaixa(Integer totalPontosFaixa) {
		this.totalPontosFaixa = totalPontosFaixa;
	}
	
	public String getTotaisDescricao() {
		return totalPontos + " / " + totalPontosFaixa + " = " + MathUtil.formataValor((totalPontos.doubleValue() / totalPontosFaixa.doubleValue()) * 100.0) + " %";
	}
}