package com.fortes.rh.test.web.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManager;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.test.factory.sesmt.ComissaoReuniaoFactory;
import com.fortes.rh.web.dwr.ComissaoReuniaoDWR;

public class ComissaoReuniaoDWRTest extends MockObjectTestCase
{
	private ComissaoReuniaoDWR comissaoReuniaoDWR;
	private Mock comissaoReuniaoManager;
	private Mock comissaoReuniaoPresencaManager;
	private Mock comissaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		comissaoReuniaoDWR = new ComissaoReuniaoDWR();

		comissaoReuniaoManager = new Mock(ComissaoReuniaoManager.class);
		comissaoReuniaoDWR.setComissaoReuniaoManager((ComissaoReuniaoManager) comissaoReuniaoManager.proxy());

		comissaoReuniaoPresencaManager = new Mock(ComissaoReuniaoPresencaManager.class);
		comissaoReuniaoDWR.setComissaoReuniaoPresencaManager((ComissaoReuniaoPresencaManager)comissaoReuniaoPresencaManager.proxy());
		
		comissaoManager = mock(ComissaoManager.class);
		comissaoReuniaoDWR.setComissaoManager((ComissaoManager) comissaoManager.proxy());
	}

	public void testPrepareDados()
	{
		Long comissaoReuniaoId = 1L;
		ComissaoReuniao comissaoReuniao = ComissaoReuniaoFactory.getEntity(1L);

		comissaoReuniaoManager.expects(once()).method("findByIdProjection").with(eq(comissaoReuniaoId)).will(returnValue(comissaoReuniao));
		Exception exception = null;
		try
		{
			comissaoReuniaoDWR.prepareDadosReuniao(comissaoReuniao.getId());
		}catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testValidaData() throws ParseException
	{
		Long comissaoId = 1L;

		String dataStr = "28/01/2010";
		Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
		
		comissaoManager.expects(once()).method("validaData").with(eq(data),eq(comissaoId)).will(returnValue(true));
		Exception exception = null;
		try
		{
			comissaoReuniaoDWR.validaDataNoPeriodoDaComissao(dataStr, comissaoId);
		}catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testValidaDataException()
	{
		Long comissaoId = 1L;
		
		String dataStr = "askljdsakljda";
		
		assertFalse(comissaoReuniaoDWR.validaDataNoPeriodoDaComissao(dataStr, comissaoId));
	}
}
