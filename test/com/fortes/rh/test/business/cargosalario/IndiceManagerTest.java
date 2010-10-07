package com.fortes.rh.test.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManagerImpl;
import com.fortes.rh.dao.cargosalario.IndiceDao;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceHistoricoFactory;
import com.fortes.rh.util.DateUtil;

public class IndiceManagerTest extends MockObjectTestCase
{
	IndiceManagerImpl indiceManager = null;
	Mock indiceDao = null;
	Mock transactionManager;
	Mock indiceHistoricoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		indiceManager = new IndiceManagerImpl();

		indiceDao = new Mock(IndiceDao.class);
		indiceManager.setDao((IndiceDao) indiceDao.proxy());

		transactionManager = new Mock(PlatformTransactionManager.class);
		indiceManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());

		indiceHistoricoManager = new Mock(IndiceHistoricoManager.class);
		indiceManager.setIndiceHistoricoManager((IndiceHistoricoManager) indiceHistoricoManager.proxy());
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testSave()
	{
		Indice indice = IndiceFactory.getEntity();
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();

		Exception exception = null;
		try
		{
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			indiceDao.expects(once()).method("save").with(ANYTHING);
			indiceHistoricoManager.expects(once()).method("save").with(ANYTHING);
			transactionManager.expects(once()).method("commit").with(ANYTHING);
			indiceManager.save(indice, indiceHistorico);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testSaveException()
	{
		Indice indice = IndiceFactory.getEntity(1L);
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity();

		Exception exception = null;
		try
		{
			transactionManager.expects(once()).method("getTransaction").with(ANYTHING);
			indiceDao.expects(once()).method("save").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(indice.getId(),""))));;
			transactionManager.expects(once()).method("rollback").with(ANYTHING);
			indiceManager.save(indice, indiceHistorico);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testFindByIdProjection()
	{
		Indice indice = IndiceFactory.getEntity(1L);

		indiceDao.expects(once()).method("findByIdProjection").with(eq(indice.getId())).will(returnValue(indice));

		assertEquals(indice, indiceManager.findByIdProjection(indice.getId()));
	}

	public void testFindByCodigo()
	{
		indiceDao.expects(once()).method("findByCodigo").withAnyArguments().will(returnValue(null));

		Indice retorno = indiceManager.findByCodigo(null);

		assertNull(retorno);
	}

	public void testRemove()
	{
		indiceDao.expects(once()).method("remove").withAnyArguments().will(returnValue(true));

		boolean retorno = indiceManager.remove("");

		assertTrue(retorno);
	}

	public void testFindHistoricoAtual()
	{
		indiceDao.expects(once()).method("findHistoricoAtual").withAnyArguments().will(returnValue(null));

		Indice retorno = indiceManager.findHistoricoAtual(null);

		assertNull(retorno);
	}

	public void testGetCodigoAc()
	{
		Indice indice = IndiceFactory.getEntity(1L);
		Indice retorno = IndiceFactory.getEntity(2L);
		retorno.setCodigoAC("001");

		indiceDao.expects(once()).method("findByIdProjection").with(eq(indice.getId())).will(returnValue(retorno));

		assertEquals("001", indiceManager.getCodigoAc(indice).getCodigoAC());
	}

//	TODO A exceção não foi coberta, pois nenhum dos métodos envolvidos lançava exceção.
	public void testRemoveIndice()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);

		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(indiceHistorico);

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setIndiceHistoricos(indiceHistoricos);

		indiceHistoricoManager.expects(once()).method("findAllSelect").with(eq(indice.getId())).will(returnValue(indiceHistoricos));
		indiceHistoricoManager.expects(once()).method("remove").with(ANYTHING);
		indiceDao.expects(once()).method("remove").with(eq(indice.getId()));

		Exception exception = null;
		try
		{
			indiceManager.removeIndice(indice.getId());
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNull(exception);
	}

	public void testFindHistorico()
	{
		IndiceHistorico indiceHistorico = IndiceHistoricoFactory.getEntity(1L);
		indiceHistorico.setData(DateUtil.criarDataMesAno(20, 03, 1950));

		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(indiceHistorico);

		Indice indice = IndiceFactory.getEntity(1L);
		indice.setIndiceHistoricos(indiceHistoricos);

		Date dataHistorico = DateUtil.criarDataMesAno(20, 03, 1950);

		indiceDao.expects(once()).method("findHistoricoAtual").with(ANYTHING, ANYTHING).will(returnValue(indice));

		Indice retorno = indiceManager.findHistorico(indice.getId(), dataHistorico);

		assertEquals(indice.getId(), retorno.getId());
	}

	public void testFindIndiceByCodigoAc()
	{
		Indice indice = IndiceFactory.getEntity(1L);
		indice.setCodigoAC("0025");

		indiceDao.expects(once()).method("findIndiceByCodigoAc").with(ANYTHING).will(returnValue(indice));

		Indice retorno = indiceManager.findIndiceByCodigoAc(indice.getCodigoAC());

		assertEquals(indice.getId(), retorno.getId());
	}
}

