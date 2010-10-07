package com.fortes.rh.test.business.desenvolvimento;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.DNTManagerImpl;
import com.fortes.rh.dao.desenvolvimento.DNTDao;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.DntFactory;

public class DNTManagerTest extends MockObjectTestCase
{
	DNTManagerImpl dntManager = new DNTManagerImpl();
	Mock dntDao = null;

	protected void setUp() throws Exception
	{
		dntDao = new Mock(DNTDao.class);
		dntManager.setDao((DNTDao) dntDao.proxy());
	}

	public void testGetUltimaDNT()
	{
		DNT dnt = DntFactory.getEntity(1L);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		dntDao.expects(once()).method("getUltimaDNT").with(eq(empresa.getId())).will(returnValue(dnt));
		assertEquals(dnt, dntManager.getUltimaDNT(empresa.getId()));
	}

}
