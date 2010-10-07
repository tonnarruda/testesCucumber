package com.fortes.rh.test.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoPeriodoManager;
import com.fortes.rh.web.dwr.ComissaoPeriodoDWR;

public class ComissaoPeriodoDWRTest extends MockObjectTestCase
{
	private ComissaoPeriodoDWR comissaoPeriodoDWR;
	private Mock comissaoPeriodoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		comissaoPeriodoDWR = new ComissaoPeriodoDWR();

		comissaoPeriodoManager = new Mock(ComissaoPeriodoManager.class);
		comissaoPeriodoDWR.setComissaoPeriodoManager((ComissaoPeriodoManager) comissaoPeriodoManager.proxy());
	}

	public void testValidaData() throws ParseException
	{
		Long comissaoPeriodoId = 1L;

		String dataStr = "28/01/2010";
		Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
		
		comissaoPeriodoManager.expects(once()).method("validaDataComissaoPeriodo").with(eq(data),eq(comissaoPeriodoId)).will(returnValue(true));
		Exception exception = null;
		try
		{
			comissaoPeriodoDWR.validaDataDaComissao(dataStr, comissaoPeriodoId);
		}catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testValidaDataException()
	{
		Long comissaoPeriodoId = 1L;
		
		String dataStr = "da/ta/inválida!";
		
		assertFalse(comissaoPeriodoDWR.validaDataDaComissao(dataStr, comissaoPeriodoId));
	}
}
