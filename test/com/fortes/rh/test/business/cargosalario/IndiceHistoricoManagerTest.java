package com.fortes.rh.test.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.IndiceHistoricoManagerImpl;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;

public class IndiceHistoricoManagerTest extends MockObjectTestCase
{
	IndiceHistoricoManagerImpl indiceHistoricoManager = null;
	Mock indiceHistoricoDao;

	protected void setUp() throws Exception
	{
		super.setUp();
		indiceHistoricoManager = new IndiceHistoricoManagerImpl();

		indiceHistoricoDao = new Mock(IndiceHistoricoDao.class);
		indiceHistoricoManager.setDao((IndiceHistoricoDao) indiceHistoricoDao.proxy());
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testFindAllSelect()
	{
		Indice indice = IndiceFactory.getEntity(1L);

		Collection<IndiceHistorico> indiceHistoricos = IndiceHistoricoFactory.getCollection();

		indiceHistoricoDao.expects(once()).method("findAllSelect").with(eq(indice.getId())).will(returnValue(indiceHistoricos));

		assertEquals(indiceHistoricos, indiceHistoricoManager.findAllSelect(indice.getId()));
	}

	public void testFindByIdProjection()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);

		indiceHistoricoDao.expects(once()).method("findByIdProjection").with(eq(indiceHistorico.getId())).will(returnValue(indiceHistorico));

		assertEquals(indiceHistorico, indiceHistoricoManager.findByIdProjection(indiceHistorico.getId()));
	}

	public void testVerifyDataUpdate()
	{
		Indice indice = IndiceFactory.getEntity(1L);
		Date data = new Date();
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);

		indiceHistoricoDao.expects(once()).method("verifyData").with(eq(indiceHistorico.getId()), eq(data), eq(indice.getId())).will(returnValue(true));

		assertEquals(true, indiceHistoricoManager.verifyData(indiceHistorico.getId(), data, indice.getId()));
	}

	public void testFindUltimoSalarioIndice()
	{
		Indice indice = IndiceFactory.getEntity(1L);

		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
		indiceHistorico.setIndice(indice);
		indiceHistorico.setValor(150.00);

		Double valor = indiceHistorico.getValor();

		indiceHistoricoDao.expects(once()).method("findUltimoSalarioIndice").with(ANYTHING).will(returnValue(valor));

		assertEquals(150.00, indiceHistoricoManager.findUltimoSalarioIndice(indice.getId()));
	}

	public void testFindByPeriodo()
	{
		indiceHistoricoDao.expects(once()).method("findByPeriodo").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(null));

		Collection<IndiceHistorico> retorno = indiceHistoricoManager.findByPeriodo(1L, null, null);

		assertNull(retorno);
	}

	public void testRemove()
	{
		indiceHistoricoDao.expects(once()).method("remove").with(ANYTHING, ANYTHING).will(returnValue(true));

		boolean retorno = indiceHistoricoManager.remove(new Date(), 1L);

		assertTrue(retorno);
	}

	public void testVerifyDataIndice()
	{
		Indice indice = IndiceFactory.getEntity(1L);
		Date data = new Date();

		indiceHistoricoDao.expects(once()).method("existsAnteriorByDataIndice").with(eq(data), eq(indice.getId())).will(returnValue(true));

		assertEquals(true, indiceHistoricoManager.existsAnteriorByDataIndice(data, indice.getId()));
	}
}