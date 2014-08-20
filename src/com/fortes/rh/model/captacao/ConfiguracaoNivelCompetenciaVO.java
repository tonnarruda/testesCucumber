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
	private Integer totalGapExcedenteAoCargo = 0;
	private Integer totalPontosFaixa;
	private Collection<MatrizCompetenciaNivelConfiguracao> matrizes;
	private String avaliadorNome;
	private String data;

	public ConfiguracaoNivelCompetenciaVO() {
		super();
	}
	public ConfiguracaoNivelCompetenciaVO(String nome, String avaliadorNome, String data, Collection<MatrizCompetenciaNivelConfiguracao> matrizes) {
		super();
		this.nome = nome;
		this.matrizes = matrizes;
		this.avaliadorNome = avaliadorNome;
		this.data = data;
	}
	
	public ConfiguracaoNivelCompetenciaVO(String candidatoNome, Collection<MatrizCompetenciaNivelConfiguracao> matriz, int totalPontosFaixa) 
	{
		this.nome = candidatoNome;
		this.matrizes = matriz;
		this.totalPontosFaixa = totalPontosFaixa;
	}
	public void somaTotalPontos(int totalPontos)
	{
		if (this.totalPontos == null) 
			this.totalPontos = 0;
		
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
		if (totalPontos != null && totalPontosFaixa != null)
			return totalPontos + " / " + totalPontosFaixa + " = " + MathUtil.formataValor((totalPontos.doubleValue() / totalPontosFaixa.doubleValue()) * 100.0) + " %";
		
		return "";
	}
	
	public String getTotalGapExcedenteCargoDescricao() {
		if (totalPontos != null && totalPontosFaixa != null)
		{
			Integer gapPositivo = totalPontos - totalGapExcedenteAoCargo;
			gapPositivo = gapPositivo > totalPontosFaixa ? totalPontosFaixa: gapPositivo; 
			return gapPositivo + " / " + totalPontosFaixa + " = " + MathUtil.formataValor(( (gapPositivo.doubleValue()) / totalPontosFaixa.doubleValue()) * 100.0) + " %";
		}
		
		return "";
	}
	
	public void configuraNivelCandidato(String competenciaDescricao, NivelCompetencia nivelCompetencia) 
	{
		for (MatrizCompetenciaNivelConfiguracao competenciaNivel : matrizes) 
		{
			if(competenciaNivel.getCompetencia().equals(competenciaDescricao))
			{
				if(competenciaNivel.getNivel().equals(nivelCompetencia.getOrdem() + " - " + nivelCompetencia.getDescricao()))
				{
					competenciaNivel.setConfiguracao(true);
					somaTotalPontos(nivelCompetencia.getOrdem());
				}
				
				if(competenciaNivel.getNivel().equals("GAP"))
				{
					competenciaNivel.setGap(nivelCompetencia.getOrdem() - competenciaNivel.getNivelDaFaixa());
					if(competenciaNivel.getGap() != null && competenciaNivel.getGap() > 0)
						this.totalGapExcedenteAoCargo += competenciaNivel.getGap(); 
				}
			}
		}
	}
	public Integer getTotalGapExcedenteAoCargo() {
		return totalGapExcedenteAoCargo;
	}
	public void setTotalGapExcedenteAoCargo(Integer totalGapExcedenteAoCargo) {
		this.totalGapExcedenteAoCargo = totalGapExcedenteAoCargo;
	}
	public String getAvaliadorNome() {
		return avaliadorNome;
	}
	public String getData() {
		return data;
	}
}