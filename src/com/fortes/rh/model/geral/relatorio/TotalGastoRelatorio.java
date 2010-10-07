package com.fortes.rh.model.geral.relatorio;

import java.io.Serializable;
import java.util.Date;

import com.fortes.rh.util.DateUtil;

public class TotalGastoRelatorio implements Serializable
{
	private Date mesAno;
	private Double total;

	public String getMesAnoFormatado()
	{
		if (mesAno != null)
			return DateUtil.formataDate(mesAno, "MM/yyyy");
		else
			return "Total";
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


}
