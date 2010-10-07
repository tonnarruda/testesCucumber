package com.fortes.rh.model.captacao.relatorio;

import java.io.Serializable;

import com.fortes.rh.model.captacao.EtapaSeletiva;

// Utilizado para gerar relatório de gastos empresas.
public class ProdutividadeRelatorio implements Serializable
{
	private EtapaSeletiva etapa;
	private int qtdJan;
	private int qtdFev;
	private int qtdMar;
	private int qtdAbr;
	private int qtdMai;
	private int qtdJun;
	private int qtdJul;
	private int qtdAgo;
	private int qtdSet;
	private int qtdOut;
	private int qtdNov;
	private int qtdDez;

	public String getEtapaNome()
	{
		return etapa.getNome();
	}

	public int getTotal()
	{
		return qtdJan + qtdFev + qtdMar + qtdAbr + qtdMai + qtdJun + qtdJul +
			qtdAgo + qtdSet + qtdOut + qtdNov + qtdDez;
	}

	public int getQtdAbr()
	{
		return qtdAbr;
	}
	public void setQtdAbr(int qtdAbr)
	{
		this.qtdAbr = qtdAbr;
	}
	public int getQtdAgo()
	{
		return qtdAgo;
	}
	public void setQtdAgo(int qtdAgo)
	{
		this.qtdAgo = qtdAgo;
	}
	public int getQtdDez()
	{
		return qtdDez;
	}
	public void setQtdDez(int qtdDez)
	{
		this.qtdDez = qtdDez;
	}
	public int getQtdFev()
	{
		return qtdFev;
	}
	public void setQtdFev(int qtdFev)
	{
		this.qtdFev = qtdFev;
	}
	public int getQtdJan()
	{
		return qtdJan;
	}
	public void setQtdJan(int qtdJan)
	{
		this.qtdJan = qtdJan;
	}
	public int getQtdJul()
	{
		return qtdJul;
	}
	public void setQtdJul(int qtdJul)
	{
		this.qtdJul = qtdJul;
	}
	public int getQtdJun()
	{
		return qtdJun;
	}
	public void setQtdJun(int qtdJun)
	{
		this.qtdJun = qtdJun;
	}
	public int getQtdMai()
	{
		return qtdMai;
	}
	public void setQtdMai(int qtdMai)
	{
		this.qtdMai = qtdMai;
	}
	public int getQtdMar()
	{
		return qtdMar;
	}
	public void setQtdMar(int qtdMar)
	{
		this.qtdMar = qtdMar;
	}
	public int getQtdNov()
	{
		return qtdNov;
	}
	public void setQtdNov(int qtdNov)
	{
		this.qtdNov = qtdNov;
	}
	public int getQtdOut()
	{
		return qtdOut;
	}
	public void setQtdOut(int qtdOut)
	{
		this.qtdOut = qtdOut;
	}
	public int getQtdSet()
	{
		return qtdSet;
	}
	public void setQtdSet(int qtdSet)
	{
		this.qtdSet = qtdSet;
	}
	public EtapaSeletiva getEtapa()
	{
		return etapa;
	}
	public void setEtapa(EtapaSeletiva etapa)
	{
		this.etapa = etapa;
	}
}
