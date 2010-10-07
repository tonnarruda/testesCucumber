package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.web.dwr.RiscosDWR;

public class RiscosDWRTest extends MockObjectTestCase
{
	private RiscosDWR riscosDWR;
	private Mock riscoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		riscosDWR = new RiscosDWR();

		riscoManager = new Mock(RiscoManager.class);
		riscosDWR.setRiscoManager((RiscoManager) riscoManager.proxy());
	}

	public void testGetRiscos()
	{
		Risco risco = new Risco();
		risco.setId(1L);
		risco.setGrupoRisco("calor");

		Collection<Risco> riscos = new ArrayList<Risco>();
		riscos.add(risco);

		riscoManager.expects(once()).method("find").with(ANYTHING, ANYTHING).will(returnValue(riscos));

		Map retorno = riscosDWR.getRiscos("calor");

		assertEquals(1, retorno.size());

	}

	public void testGetRiscosSemGrupoRisco()
	{
		Collection<Risco> riscos = new ArrayList<Risco>();

		riscoManager.expects(once()).method("find").with(ANYTHING, ANYTHING).will(returnValue(riscos));

		Map retorno = riscosDWR.getRiscos("frio");

		assertEquals(1, retorno.size());
	}

	public void testGetsSets()
	{
		riscosDWR.setRiscoManager(null);
	}
}
