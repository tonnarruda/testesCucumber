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
			return Double.parseDouble(df.format(taxaDemissao).replace(",", "."));
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

	public Double getQtdAtivosFinalMes() {
		return qtdAtivosFinalMes;
	}

	public void setQtdAtivosFinalMes(Double qtdAtivosFinalMes) {
		this.qtdAtivosFinalMes = qtdAtivosFinalMes;
	}

	public Double getQtdDemitidosReducaoQuadro() {
		return qtdDemitidosReducaoQuadro;
	}

	public void setQtdDemitidosReducaoQuadro(Double qtdDemitidosReducaoQuadro) {
		this.qtdDemitidosReducaoQuadro = qtdDemitidosReducaoQuadro;
	}
}
