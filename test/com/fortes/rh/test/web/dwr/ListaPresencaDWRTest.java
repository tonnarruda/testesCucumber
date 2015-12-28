package com.fortes.rh.test.web.dwr;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
import com.fortes.rh.web.dwr.ListaPresencaDWR;

public class ListaPresencaDWRTest extends MockObjectTestCase
{
	private ListaPresencaDWR listaPresencaDWR;
	private Mock colaboradorPresencaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		listaPresencaDWR = new ListaPresencaDWR();

		colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
		
		listaPresencaDWR.setColaboradorPresencaManager((ColaboradorPresencaManager) colaboradorPresencaManager.proxy());
	}

	public void testUpdatePresenca() throws Exception
	{
		colaboradorPresencaManager.expects(once()).method("updateFrequencia").with(eq(null),eq(null),eq(true),eq(false));
		assertEquals(true, listaPresencaDWR.updateFrequencia(null, null, true, FiltroControleVencimentoCertificacao.CURSO.getOpcao()));
	}

	public void testUpdatePresencaException() throws Exception
	{
		colaboradorPresencaManager.expects(once()).method("updateFrequencia").with(eq(null),eq(null),eq(true), eq(false)).will(throwException(new Exception()));

		Exception ex = null;
		try
		{			
			listaPresencaDWR.updateFrequencia(null, null, true, FiltroControleVencimentoCertificacao.CURSO.getOpcao());
		}
		catch (Exception e)
		{
			ex = e;
		}
		
		assertNotNull(ex);
	}
}
