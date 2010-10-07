package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EpcManagerImpl;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class EpcManagerTest extends MockObjectTestCase
{
	private EpcManagerImpl epcManager = new EpcManagerImpl();
	private Mock epcDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        epcDao = new Mock(EpcDao.class);
        epcManager.setDao((EpcDao) epcDao.proxy());
    }

	public void testFindByIdProjection() throws Exception
	{
		Epc epc = new Epc();
		epc.setId(1L);

		epcDao.expects(once()).method("findByIdProjection").with(eq(epc.getId())).will(returnValue(epc));

		assertEquals(epc, epcManager.findByIdProjection(epc.getId()));
	}
	
	public void testFindAllSelect()
	{
		Epc epc = new Epc();
		epc.setId(1L);
		Empresa empresa= EmpresaFactory.getEmpresa(3L);
		epc.setEmpresa(empresa);
		
		epcDao.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(new ArrayList<Epc>()));
		assertEquals(0, epcManager.findAllSelect(empresa.getId()).size());
	}
	
	public void testpopulaCheckBox()
	{
		Collection<Epc> epcs = new ArrayList<Epc>();
		epcs.add(new Epc());
		epcDao.expects(once()).method("findAllSelect").will(returnValue(epcs));
		assertEquals(1, epcManager.populaCheckBox(1L).size());
	}
	
	public void testFindEpcsDoAmbiente()
	{
		Long ambienteId = 2L;
		Collection<Epc> epcs = new ArrayList<Epc>();
		epcs.add(new Epc());
		epcDao.expects(once()).method("findEpcsDoAmbiente").will(returnValue(epcs));
		assertEquals(1, epcManager.findEpcsDoAmbiente(ambienteId, new Date()).size());
	}
}