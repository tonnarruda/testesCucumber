package com.fortes.rh.test.business.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GrupoGastoManagerImpl;
import com.fortes.rh.dao.geral.GrupoGastoDao;
import com.fortes.rh.model.geral.GrupoGasto;

public class GrupoGastoManagerTest extends MockObjectTestCase
{
	GrupoGastoManagerImpl grupoGastoManager = null;
	Mock grupoGastoDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		grupoGastoManager = new GrupoGastoManagerImpl();

		grupoGastoDao = new Mock(GrupoGastoDao.class);
		grupoGastoManager.setDao((GrupoGastoDao) grupoGastoDao.proxy());
	}

	public void testFindByIdProjection() throws Exception
	{
		GrupoGasto grupoGasto = new GrupoGasto();
		grupoGasto.setId(1L);

		grupoGastoDao.expects(once()).method("findByIdProjection").with(eq(grupoGasto.getId())).will(returnValue(grupoGasto));

		GrupoGasto grupoGastoRetorno = grupoGastoManager.findByIdProjection(grupoGasto.getId());

		assertEquals(grupoGasto, grupoGastoRetorno);
	}
}
