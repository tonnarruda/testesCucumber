package com.fortes.rh.model.captacao;

import java.util.Collection;

/**
 * Utilizado para gerar Relatorio de configurações de nivel de experiencia
 */
public class ConfiguracaoNivelCompetenciaVO
{

	private String nome;
	private int totalPontos;
	private int totalPontosFaixa;
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
	public int getTotalPontos() {
		return totalPontos;
	}
	public void setTotalPontos(int totalPontos) {
		this.totalPontos = totalPontos;
	}
	public int getTotalPontosFaixa() {
		return totalPontosFaixa;
	}
	public void setTotalPontosFaixa(int totalPontosFaixa) {
		this.totalPontosFaixa = totalPontosFaixa;
	}
}