package com.fortes.rh.model.geral.relatorio;

import java.text.DecimalFormat;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TaxaDemissao
{
	private Date mesAno;
	private Double taxaDemissao = 0.0;
	private Double qtdDemitidosReducaoQuadro;
	private Double qtdDemitidos;
	private Double qtdAtivosInicioMes;
	private Double qtdAtivosFinalMes;
	
	private Integer qtdColaboradores;
	private Integer tempoServico;
	
	public TaxaDemissao() 
	{
		super();
	}

	public String getMesAnoFormatado()
	{
		return DateUtil.formataDate(mesAno, "MM/yyyy");
	}

	public Date getMesAno()
	{
		return mesAno;
	}
	
	public void setMesAno(Date mesAno)
	{
		this.mesAno = mesAno;
	}
	
	public String getTaxaDemissaoString()
	{
		return getTaxaDemissao() + "%";
	}
	
	public Double getTaxaDemissao()
	{
		try {
			DecimalFormat df = new DecimalFormat("0.00");
			return Double.parseDouble(df.format(calculaTaxaDemissao()).replace(",", "."));
		} catch (Exception e) {
			return 0.0;
		}
	}
	public void setTaxaDemissao(Double taxaDemissao)
	{
		this.taxaDemissao = taxaDemissao;
	}
	
	public Double getQtdDemitidos() {
		return qtdDemitidos;
	}

	public void setQtdDemitidos(Double qtdDemitidos) {
		this.qtdDemitidos = qtdDemitidos;
	}

	public void setQtdDemitidos(Integer qtdDemitidos) {
		this.qtdDemitidos = qtdDemitidos.doubleValue();
	}
	
	public Integer getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(Integer tempoServico) {
		this.tempoServico = tempoServico;
	}

	public Integer getQtdColaboradores() {
		return qtdColaboradores;
	}

	public void setQtdColaboradores(Integer qtdColaboradores) {
		this.qtdColaboradores = qtdColaboradores;
	}

	public Double getQtdAtivosInicioMes() {
		return qtdAtivosInicioMes;
	}

	public void setQtdAtivosInicioMes(Double qtdAtivosInicioMes) {
		this.qtdAtivosInicioMes = qtdAtivosInicioMes;
	}
	
	public void setQtdAtivosInicioMes(Integer qtdAtivosInicioMes) {
		this.qtdAtivosInicioMes = qtdAtivosInicioMes.doubleValue();
	}

	public Double getQtdAtivosFinalMes() {
		return qtdAtivosFinalMes;
	}

	public void setQtdAtivosFinalMes(Double qtdAtivosFinalMes) {
		this.qtdAtivosFinalMes = qtdAtivosFinalMes;
	}
	
	public void setQtdAtivosFinalMes(Integer qtdAtivosFinalMes) {
		this.qtdAtivosFinalMes = qtdAtivosFinalMes.doubleValue();
	}

	public Double getQtdDemitidosReducaoQuadro() {
		return qtdDemitidosReducaoQuadro;
	}

	public void setQtdDemitidosReducaoQuadro(Double qtdDemitidosReducaoQuadro) {
		this.qtdDemitidosReducaoQuadro = qtdDemitidosReducaoQuadro;
	}
	
	public void setQtdDemitidosReducaoQuadro(Integer qtdDemitidosReducaoQuadro) {
		this.qtdDemitidosReducaoQuadro = qtdDemitidosReducaoQuadro.doubleValue();
	}
	
	private Double calculaTaxaDemissao(){
		Double efetivoMedio = (qtdAtivosInicioMes + qtdAtivosFinalMes) / 2;
		if(efetivoMedio != null && efetivoMedio != 0)
			return ((qtdDemitidos - qtdDemitidosReducaoQuadro) / efetivoMedio) * 100;
		else
			return 0.0;
	}
}
