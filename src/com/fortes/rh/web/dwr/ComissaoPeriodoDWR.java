package com.fortes.rh.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;

@Component
@RemoteProxy(name="ComissaoPeriodoDWR")
public class ComissaoPeriodoDWR
{
	@Autowired private ComissaoPeriodoManager comissaoPeriodoManager;

	@RemoteMethod
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
}