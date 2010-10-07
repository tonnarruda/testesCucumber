package com.fortes.rh.test.web.dwr;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.test.factory.sesmt.AfastamentoFactory;
import com.fortes.rh.web.dwr.AfastamentoDWR;

public class AfastamentoDWRTest extends MockObjectTestCase
{
	private AfastamentoDWR afastamentoDWR;
	private Mock afastamentoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		afastamentoDWR = new AfastamentoDWR();

		afastamentoManager = new Mock(AfastamentoManager.class);
		afastamentoDWR.setAfastamentoManager((AfastamentoManager) afastamentoManager.proxy());
	}
	
	public void testIsAfastamentoInss()
	{
		Afastamento afastamento = AfastamentoFactory.getEntity(50L);
		afastamento.setInss(true);
		
		afastamentoManager.expects(once()).method("findById").with(eq(50L)).will(returnValue(afastamento));
		
		assertEquals(true, afastamentoDWR.isAfastamentoInss("50"));
	}
	
	public void testIsAfastamentoInssEmBranco()
	{
		assertEquals(false, afastamentoDWR.isAfastamentoInss(""));
	}
}
