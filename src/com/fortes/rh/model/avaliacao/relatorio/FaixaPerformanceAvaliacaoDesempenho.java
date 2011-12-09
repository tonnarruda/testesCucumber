package com.fortes.rh.model.avaliacao.relatorio;

import java.util.Collection;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.MathUtil;

public class FaixaPerformanceAvaliacaoDesempenho
{
	private Double percentIni;
	private Double percentFim;
	private int quantidade = 0;
	private int totalColaboradores;
	
	public FaixaPerformanceAvaliacaoDesempenho(Double percentIni, Double percentFim, int totalColaboradores) 
	{
		this.percentIni = percentIni;
		this.percentFim = percentFim;
		this.totalColaboradores = totalColaboradores;
	}

	public String getFaixa() {
		return "De " + MathUtil.round(percentIni, 2) + "% à " + MathUtil.round(percentFim, 2) + "%";
	}
	
	public String getPercentual() {
		double retorno = ((double)quantidade / totalColaboradores) * 100;
		return MathUtil.round(retorno, 2) + "";
	}
	
	public int getQuantidade() {
		return quantidade;
	}

	public static void selecionaColaborador(Colaborador colaborador, Collection<FaixaPerformanceAvaliacaoDesempenho> faixas) 
	{
		for (FaixaPerformanceAvaliacaoDesempenho faixa : faixas) 
		{
			if(faixa.percentIni <= (colaborador.getPerformanceDouble()*100) && (faixa.percentFim + 0.9999999) >= (colaborador.getPerformanceDouble()*100))
				faixa.quantidade++;
		}
		
	}

}
