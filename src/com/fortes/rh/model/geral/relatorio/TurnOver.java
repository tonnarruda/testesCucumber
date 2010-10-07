package com.fortes.rh.model.geral.relatorio;

import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TurnOver
{
	private Date mesAno;
	private Double turnOver;
	private Double qtdAdmitidos;
	private Double qtdDemitidos;
	private Double qtdAtivos;

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
	public Double getTurnOver()
	{
		return turnOver;
	}
	public void setTurnOver(Double turnOver)
	{
		this.turnOver = turnOver;
	}

	public Double getQtdAdmitidos() {
		return qtdAdmitidos;
	}

	public void setQtdAdmitidos(Double qtdAdmitidos) {
		this.qtdAdmitidos = qtdAdmitidos;
	}

	public Double getQtdDemitidos() {
		return qtdDemitidos;
	}

	public void setQtdDemitidos(Double qtdDemitidos) {
		this.qtdDemitidos = qtdDemitidos;
	}

	public Double getQtdAtivos() {
		return qtdAtivos;
	}

	public void setQtdAtivos(Double qtdAtivos) {
		this.qtdAtivos = qtdAtivos;
	}
}
