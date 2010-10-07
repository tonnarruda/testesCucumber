package com.fortes.rh.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;

public class ComissaoPeriodoDWR
{
	private ComissaoPeriodoManager comissaoPeriodoManager;

	public boolean validaDataDaComissao(String aPartirDe, Long comissaoPeriodoId)
	{
		Date data = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
			data = sdf.parse(aPartirDe);
		} 
		catch (ParseException e) {
			return false;
		}
		
		return comissaoPeriodoManager.validaDataComissaoPeriodo(data, comissaoPeriodoId);
	}

	public void setComissaoPeriodoManager(ComissaoPeriodoManager comissaoPeriodoManager) {
		this.comissaoPeriodoManager = comissaoPeriodoManager;
	}
}
