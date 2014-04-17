package com.fortes.rh.model.geral.relatorio;

import java.util.Date;

public class Absenteismo
{
	private String mes;
	private String ano;
	private Double absenteismo = 0.0;
	private Integer qtdTotalFaltas = 0;
	private Integer qtdDiasTrabalhados = 0;
	private Integer qtdAtivos = 0;
	
	public Absenteismo(String ano, String mes, Integer qtdTotalFaltas)
	{
		super();
		this.mes = mes;
		this.ano = ano;
		this.qtdTotalFaltas = qtdTotalFaltas;
	}

	public Absenteismo()
	{
		super();
	}

	public String getMesAnoFormatado()
	{
		return mes + "/" + ano;
	}

	public void setMesAnoQtdAtivos(Date dataMesAno, Integer qtdAtivos) 
	{
		this.setQtdAtivos(qtdAtivos);
	}

	public Integer getQtdDiasTrabalhados() 
	{
		return qtdDiasTrabalhados;
	}

	public void setQtdDiasTrabalhados(Integer qtdDiasTrabalhados) 
	{
		this.qtdDiasTrabalhados = qtdDiasTrabalhados;
	}

	public Integer getQtdAtivos() 
	{
		return qtdAtivos;
	}

	public void setQtdAtivos(Integer qtdAtivos) 
	{
		this.qtdAtivos = qtdAtivos;
	}

	public Double getAbsenteismo()
	{
		return this.absenteismo;
	}
	
	public void setAbsenteismo(Double absenteismo) 
	{
		this.absenteismo = absenteismo;
	}

	public Integer getQtdTotalFaltas() {
		return qtdTotalFaltas;
	}

	public void setQtdTotalFaltas(Integer qtdTotalFaltas) {
		this.qtdTotalFaltas = qtdTotalFaltas;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
}
