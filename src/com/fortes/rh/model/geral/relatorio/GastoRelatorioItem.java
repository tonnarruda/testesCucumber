package com.fortes.rh.model.geral.relatorio;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.util.DateUtil;

//Utilizado para gerar relatório de gastos empresas.
public class GastoRelatorioItem implements Serializable
{
	private Gasto gasto;
	private Date mesAno;
	private Double total;

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
	public Double getTotal()
	{
		return total;
	}
	public void setTotal(Double total)
	{
		this.total = total;
	}
	public Gasto getGasto()
	{
		return gasto;
	}
	public void setGasto(Gasto gasto)
	{
		this.gasto = gasto;
	}
}