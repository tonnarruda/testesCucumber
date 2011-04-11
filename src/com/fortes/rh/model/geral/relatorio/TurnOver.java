package com.fortes.rh.model.geral.relatorio;

import java.text.DecimalFormat;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TurnOver
{
	private Date mesAno;
	private Double turnOver;
	private Double qtdAdmitidos;
	private Double qtdDemitidos;
	private Double qtdAtivos;
	
	public TurnOver() 
	{
		super();
	}

	public void setMesAnoQtdDemitidos(Date mesAno, Double qtdDemitidos) 
	{
		this.setMesAno(mesAno);
		this.setQtdDemitidos(qtdDemitidos);
	}
	
	public void setMesAnoQtdAdmitidos(Date mesAno, Double qtdAdmitidos) 
	{
		this.setMesAno(mesAno);
		this.setQtdAdmitidos(qtdAdmitidos);
	}

	public void setMesAnoQtdAtivos(Date dataMesAno, double qtdAtivos) 
	{
		this.setMesAno(mesAno);
		this.setQtdAtivos(qtdAtivos);
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
	public Double getTurnOver()
	{
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.parseDouble(df.format(turnOver).replace(",", "."));
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
