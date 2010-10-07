package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GastoManagerImpl;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class GastoManagerTest extends MockObjectTestCase
{
	GastoManagerImpl gastoManager = null;
	Mock gastoDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		gastoManager = new GastoManagerImpl();
		gastoDao = new Mock(GastoDao.class);
		gastoManager.setDao((GastoDao) gastoDao.proxy());
	}

	public void testFindByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		Gasto gasto = new Gasto();
		gasto.setId(1L);
		gasto.setEmpresa(empresa);

		Collection<Gasto> gastos = new ArrayList<Gasto>();
		gastos.add(gasto);

		gastoDao.expects(once()).method("findByEmpresa").with(eq(empresa.getId())).will(returnValue(gastos));

		Collection<Gasto> retorno = gastoManager.findByEmpresa(empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testFindByIdProjection() throws Exception
	{
		Gasto gasto = new Gasto();
		gasto.setId(1L);

		gastoDao.expects(once()).method("findByIdProjection").with(eq(gasto.getId())).will(returnValue(gasto));

		Gasto gastoRetorno = gastoManager.findByIdProjection(gasto.getId());

		assertEquals(gasto, gastoRetorno);
	}

}