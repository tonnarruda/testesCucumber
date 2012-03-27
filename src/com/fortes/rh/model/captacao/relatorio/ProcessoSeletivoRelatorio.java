package com.fortes.rh.model.captacao.relatorio;

import java.io.Serializable;

import com.fortes.rh.model.captacao.EtapaSeletiva;

// Utilizado para gerar relatório de processo seletivo
@SuppressWarnings("serial")
public class ProcessoSeletivoRelatorio implements Serializable
{
	private EtapaSeletiva etapa;

	private double qtdJan;
	private double qtdFev;
	private double qtdMar;
	private double qtdAbr;
	private double qtdMai;
	private double qtdJun;
	private double qtdJul;
	private double qtdAgo;
	private double qtdSet;
	private double qtdOut;
	private double qtdNov;
	private double qtdDez;

	//porcentagem de aprovação em cada mes
	private double qtdAprovJan;
	private double qtdAprovFev;
	private double qtdAprovMar;
	private double qtdAprovAbr;
	private double qtdAprovMai;
	private double qtdAprovJun;
	private double qtdAprovJul;
	private double qtdAprovAgo;
	private double qtdAprovSet;
	private double qtdAprovOut;
	private double qtdAprovNov;
	private double qtdAprovDez;
	
	private int exibirTarjaRelatorio = 0;

	public EtapaSeletiva getEtapa()
	{
		return etapa;
	}
	public void setEtapa(EtapaSeletiva etapa)
	{
		this.etapa = etapa;
	}
	
	public double getQtdAprovJan()
	{
		return qtdAprovJan;
	}
	public double getQtdAprovFev()
	{
		return qtdAprovFev;
	}
	public double getQtdAprovMar()
	{
		return qtdAprovMar;
	}
	public double getQtdAprovAbr()
	{
		return qtdAprovAbr;
	}
	public double getQtdAprovMai()
	{
		return qtdAprovMai;
	}
	public double getQtdAprovJun()
	{
		return qtdAprovJun;
	}
	public double getQtdAprovJul()
	{
		return qtdAprovJul;
	}
	public double getQtdAprovAgo()
	{
		return qtdAprovAgo;
	}
	public double getQtdAprovSet()
	{
		return qtdAprovSet;
	}
	public double getQtdAprovOut()
	{
		return qtdAprovOut;
	}
	public double getQtdAprovNov()
	{
		return qtdAprovNov;
	}
	public double getQtdAprovDez()
	{
		return qtdAprovDez;
	}
	public void addQtdParticipantes(Integer historicoMes, Double qtdHistoricos, boolean apto)
	{
		switch (historicoMes)
		{
			case 1:
				this.qtdJan += qtdHistoricos;
				if(apto)
					this.qtdAprovJan = calculaAprovacao(this.qtdJan, qtdHistoricos);
			break;
			case 2:
				this.qtdFev += qtdHistoricos;
				if(apto)
					this.qtdAprovFev = calculaAprovacao(this.qtdFev, qtdHistoricos);
				break;
			case 3:
				this.qtdMar += qtdHistoricos;
				if(apto)
					this.qtdAprovMar = calculaAprovacao(this.qtdMar, qtdHistoricos);
				break;
			case 4:
				this.qtdAbr += qtdHistoricos;
				if(apto)
					this.qtdAprovAbr = calculaAprovacao(this.qtdAbr, qtdHistoricos);
				break;
			case 5:
				this.qtdMai += qtdHistoricos;
				if(apto)
					this.qtdAprovMai = calculaAprovacao(this.qtdMai, qtdHistoricos);
				break;
			case 6:
				this.qtdJun += qtdHistoricos;
				if(apto)
					this.qtdAprovJun = calculaAprovacao(this.qtdJun, qtdHistoricos);
				break;
			case 7:
				this.qtdJul += qtdHistoricos;
				if(apto)
					this.qtdAprovJul = calculaAprovacao(this.qtdJul, qtdHistoricos);
				break;
			case 8:
				this.qtdAgo += qtdHistoricos;
				if(apto)
					this.qtdAprovAgo = calculaAprovacao(this.qtdAgo , qtdHistoricos);
				break;
			case 9:
				this.qtdSet += qtdHistoricos;
				if(apto)
					this.qtdAprovSet = calculaAprovacao(this.qtdSet , qtdHistoricos);
				break;
			case 10:
				this.qtdOut += qtdHistoricos;
				if(apto)
					this.qtdAprovOut = calculaAprovacao(this.qtdOut , qtdHistoricos);
				break;
			case 11:
				this.qtdNov += qtdHistoricos;
				if(apto)
					this.qtdAprovNov = calculaAprovacao(this.qtdNov , qtdHistoricos);
				break;
			case 12:
				this.qtdDez += qtdHistoricos;
				if(apto)
					this.qtdAprovDez = calculaAprovacao(this.qtdDez , qtdHistoricos);
				break;
		}
	}

	private double calculaAprovacao(Double total, Double qtdHistoricos)
	{
		return (qtdHistoricos / total) * 100.00;
	}
	
	public double getQtdJan()
	{
		return qtdJan;
	}
	public double getQtdFev()
	{
		return qtdFev;
	}
	public double getQtdMar()
	{
		return qtdMar;
	}
	public double getQtdAbr()
	{
		return qtdAbr;
	}
	public double getQtdMai()
	{
		return qtdMai;
	}
	public double getQtdJun()
	{
		return qtdJun;
	}
	public double getQtdJul()
	{
		return qtdJul;
	}
	public double getQtdAgo()
	{
		return qtdAgo;
	}
	public double getQtdSet()
	{
		return qtdSet;
	}
	public double getQtdOut()
	{
		return qtdOut;
	}
	public double getQtdNov()
	{
		return qtdNov;
	}
	public double getQtdDez()
	{
		return qtdDez;
	}
	public int getExibirTarjaRelatorio() {
		return exibirTarjaRelatorio;
	}
	public void setNumExibirTarjaRelatorio(int exibirTarjaRelatorio) {
		this.exibirTarjaRelatorio = exibirTarjaRelatorio;
	}
}