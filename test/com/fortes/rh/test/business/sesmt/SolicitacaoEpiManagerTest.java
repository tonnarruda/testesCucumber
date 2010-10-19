package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.ctc.wstx.util.DataUtil;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class SolicitacaoEpiManagerTest extends MockObjectTestCase
{
	private SolicitacaoEpiManagerImpl solicitacaoEpiManager = new SolicitacaoEpiManagerImpl();
	private Mock transactionManager;
	private Mock solicitacaoEpiDao;
	private Mock solicitacaoEpiItemManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        solicitacaoEpiDao = new Mock(SolicitacaoEpiDao.class);
        solicitacaoEpiManager.setDao((SolicitacaoEpiDao) solicitacaoEpiDao.proxy());

        solicitacaoEpiItemManager = new Mock(SolicitacaoEpiItemManager.class);
        solicitacaoEpiManager.setSolicitacaoEpiItemManager((SolicitacaoEpiItemManager)solicitacaoEpiItemManager.proxy());

        transactionManager = new Mock(PlatformTransactionManager.class);
        solicitacaoEpiManager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
    }

	public void testFindAllSelect()
	{
		SituacaoSolicitacaoEpi situacaoSolicitacaoEpi = SituacaoSolicitacaoEpi.TODAS;
		Collection<SolicitacaoEpi> colecao = new ArrayList<SolicitacaoEpi>();
		colecao.add(new SolicitacaoEpi());
		solicitacaoEpiDao.expects(once()).method("findAllSelect").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(colecao));

		assertEquals(colecao, solicitacaoEpiManager.findAllSelect(0, 0, 1L, null, null, new Colaborador(), situacaoSolicitacaoEpi));
	}

	public void testGetCount()
	{
		solicitacaoEpiDao.expects(once()).method("getCount").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(0));
		assertEquals(Integer.valueOf(0), solicitacaoEpiManager.getCount(1L, new Date(), new Date(), new Colaborador(), SituacaoSolicitacaoEpi.TODAS));
	}

	public void testFindByIdProjection()
	{
		Long solicitacaoEpiId = 1L;
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(solicitacaoEpiId);
		solicitacaoEpiDao.expects(once()).method("findByIdProjection").with(eq(solicitacaoEpiId)).will(returnValue(solicitacaoEpi));

		SolicitacaoEpi resultado = solicitacaoEpiManager.findByIdProjection(solicitacaoEpiId);

		assertEquals(solicitacaoEpi.getId(), resultado.getId());
	}

	public void testSave()
	{
		String[] epiIds = new String[]{"1","2"};
		String[] selectQtdSolicitado = new String[]{"12","3"};
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);

		solicitacaoEpiDao.expects(once()).method("save").with(eq(solicitacaoEpi)).isVoid();
		solicitacaoEpiItemManager.expects(once()).method("save").with(eq(solicitacaoEpi), eq(epiIds), eq(selectQtdSolicitado)).isVoid();

		Exception exception = null;
		try
		{
			solicitacaoEpiManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado);
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}

	public void testSaveException()
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);

		solicitacaoEpiDao.expects(once()).method("save").with(eq(solicitacaoEpi)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(solicitacaoEpi.getId(),""))));

		Exception exception = null;
		try
		{
			solicitacaoEpiManager.save(solicitacaoEpi, null, null);
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNotNull(exception);
	}

	public void testUpdate()
	{
		String[] epiIds = new String[]{"1","2"};
		String[] selectQtdSolicitado = new String[]{"3","3"};
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);

		solicitacaoEpiDao.expects(once()).method("update").with(eq(solicitacaoEpi)).isVoid();
		solicitacaoEpiItemManager.expects(once()).method("removeAllBySolicitacaoEpi").with(eq(solicitacaoEpi.getId())).isVoid();
		solicitacaoEpiItemManager.expects(once()).method("save").with(eq(solicitacaoEpi), eq(epiIds), eq(selectQtdSolicitado)).isVoid();

		solicitacaoEpiManager.update(solicitacaoEpi, epiIds, selectQtdSolicitado);
	}

	public void testRemove()
	{
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));

		solicitacaoEpiItemManager.expects(once()).method("removeAllBySolicitacaoEpi").with(eq(solicitacaoEpi.getId())).isVoid();
		solicitacaoEpiDao.expects(once()).method("remove").with(eq(solicitacaoEpi.getId())).isVoid();
		transactionManager.expects(atLeastOnce()).method("commit");

		solicitacaoEpiManager.remove(solicitacaoEpi.getId());
	}

	public void testRemoveException()
	{
		Long solicitacaoEpiId=1L;

		transactionManager.expects(atLeastOnce()).method("getTransaction").with(ANYTHING).will(returnValue(new MockTransactionStatus()));
		solicitacaoEpiItemManager.expects(once()).method("removeAllBySolicitacaoEpi").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		transactionManager.expects(atLeastOnce()).method("rollback");

		Exception exception = null;
		try
		{
			solicitacaoEpiManager.remove(solicitacaoEpiId);
		}
		catch (PersistenceException e)
		{
			exception = e;
		}
		assertNotNull(exception);

	}

	public void testFindRelatorioVencimentoEpi() throws Exception
	{
		char agruparPor = 'E';
		Date hoje = Calendar.getInstance().getTime();
		Long empresaId = 1L;

		Calendar dataSeisMesesAtras = Calendar.getInstance();
    	dataSeisMesesAtras.add(Calendar.MONTH, -6);

    	Calendar dataSeteMesesAtras = Calendar.getInstance();
    	dataSeteMesesAtras.add(Calendar.MONTH, -7);

    	Integer validadeUso = 6;

		Epi epi = EpiFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(100L);

		SolicitacaoEpi solicitacaoEpi =
			new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), colaborador.getNome(), "Cargo",
					dataSeisMesesAtras.getTime(), validadeUso, 1);

		SolicitacaoEpi solicitacaoEpiAnteriorFora =
			new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), colaborador.getNome(), "Cargo",
					dataSeteMesesAtras.getTime(), validadeUso, 1);

		Collection<SolicitacaoEpi> colecao = new ArrayList<SolicitacaoEpi>();
		colecao.add(solicitacaoEpiAnteriorFora);
		colecao.add(solicitacaoEpi);

		Collection<SolicitacaoEpi> resultado = null;

		solicitacaoEpiDao.expects(once()).method("findVencimentoEpi").with(eq(empresaId),eq(hoje), eq(false), ANYTHING).will(returnValue(colecao));

		resultado = solicitacaoEpiManager.findRelatorioVencimentoEpi(empresaId, hoje, agruparPor, false, null );

		assertEquals(1, resultado.size());

		assertEquals(solicitacaoEpi, ((SolicitacaoEpi)resultado.toArray()[0]));
	}

	public void testFindRelatorioEntregaEpi() throws Exception
	{
		Date hoje = new Date();
		Date dataFrente = DateUtil.incrementa(hoje, 7, 0);
		Long empresaId = 1L;
		
		Calendar dataSeisMesesParaFrente = Calendar.getInstance();
		dataSeisMesesParaFrente.add(Calendar.MONTH, 6);
		
		Calendar dataOitoMesesParaFrente = Calendar.getInstance();
		dataOitoMesesParaFrente.add(Calendar.MONTH, 8);

		Calendar dataSeteMesesAntes = Calendar.getInstance();
		dataSeteMesesAntes.add(Calendar.MONTH, -1);
		
		Integer validadeUso = 6;
		
		Epi epi = EpiFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(100L);
		
		SolicitacaoEpi solicitacaoEpi =
			new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), colaborador.getNome(), "Cargo",
					dataSeisMesesParaFrente.getTime(), validadeUso, 1);
		
		SolicitacaoEpi solicitacaoEpiAnteriorFora =
			new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), colaborador.getNome(), "Cargo",
					dataSeteMesesAntes.getTime(), validadeUso, 1);

		SolicitacaoEpi solicitacaoEpiPosteriorFora =
			new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), colaborador.getNome(), "Cargo",
					dataSeteMesesAntes.getTime(), validadeUso, 1);
		
		Collection<SolicitacaoEpi> colecao = new ArrayList<SolicitacaoEpi>();
		colecao.add(solicitacaoEpiAnteriorFora);
		colecao.add(solicitacaoEpiPosteriorFora);
		colecao.add(solicitacaoEpi);
		
		Collection<SolicitacaoEpi> resultado = null;
		
		String[] epiCheck = {"1","2"};
		Long[] epiCheckLong = LongUtil.arrayStringToArrayLong(epiCheck);
		
		solicitacaoEpiDao.expects(once()).method("findEntregaEpi").with(eq(empresaId),eq(epiCheckLong)).will(returnValue(colecao));
		
		resultado = solicitacaoEpiManager. findRelatorioEntregaEpi(empresaId, hoje, dataFrente, epiCheck);
		
		assertEquals(1, resultado.size());
		
		assertEquals(solicitacaoEpi, ((SolicitacaoEpi)resultado.toArray()[0]));
		
		//sem resultado
		colecao.clear();
		colecao.add(solicitacaoEpiAnteriorFora);
		colecao.add(solicitacaoEpiPosteriorFora);
		
		solicitacaoEpiDao.expects(once()).method("findEntregaEpi").with(eq(empresaId),eq(epiCheckLong)).will(returnValue(colecao));
		
		try {
			resultado = solicitacaoEpiManager. findRelatorioEntregaEpi(empresaId, hoje, dataFrente, epiCheck);
		} catch (Exception e) {
			assertEquals("Não existem EPIs a serem listados para os filtros informados.", e.getMessage());
		}
		
	}

	public void testFindRelatorioVencimentoEpiException()
	{
		char agruparPor = 'E';
		Date hoje = new Date();
		Long empresaId = 1L;

		Collection<SolicitacaoEpi> colecao = new ArrayList<SolicitacaoEpi>();

		solicitacaoEpiDao.expects(once()).method("findVencimentoEpi").with(eq(empresaId),eq(hoje), eq(false), ANYTHING).will(returnValue(colecao));
		Exception exception = null;

		try
		{
			colecao = solicitacaoEpiManager.findRelatorioVencimentoEpi(empresaId, hoje, agruparPor, false, null);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testEntrega() throws Exception
	{
		String[] epiIds = {"1"};
		String[] selectQtdSolicitado = {"3"};

		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setEntregue(false);

		Collection<SolicitacaoEpiItem> solicitacaoEpiItems = new ArrayList<SolicitacaoEpiItem>();
		SolicitacaoEpiItem item = new SolicitacaoEpiItem();
		item.setQtdSolicitado(3);
		item.setQtdEntregue(3);
		solicitacaoEpiItems.add(item);

		solicitacaoEpiItemManager.expects(once()).method("entrega")
					.with(eq(solicitacaoEpi), eq(epiIds), eq(selectQtdSolicitado));
		solicitacaoEpiDao.expects(once()).method("findById").will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemManager.expects(once()).method("findBySolicitacaoEpi")
					.with(eq(solicitacaoEpi.getId())).will(returnValue(solicitacaoEpiItems));
		solicitacaoEpiDao.expects(once()).method("update").with(eq(solicitacaoEpi)).isVoid();

		solicitacaoEpiManager.entrega(solicitacaoEpi, epiIds, selectQtdSolicitado);

		assertTrue(solicitacaoEpi.isEntregue());
	}

	// Entrega com qtd entregue menor que o solicitado
	public void testEntregaParcial() throws Exception
	{
		String[] epiIds = {"1"};
		String[] selectQtdSolicitado = {"3"};

		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setEntregue(false);

		Collection<SolicitacaoEpiItem> solicitacaoEpiItems = new ArrayList<SolicitacaoEpiItem>();
		SolicitacaoEpiItem item = new SolicitacaoEpiItem();
		item.setQtdSolicitado(3);
		item.setQtdEntregue(1);
		solicitacaoEpiItems.add(item);

		solicitacaoEpiItemManager.expects(once()).method("entrega")
					.with(eq(solicitacaoEpi), eq(epiIds), eq(selectQtdSolicitado));
		solicitacaoEpiDao.expects(once()).method("findById").will(returnValue(solicitacaoEpi));
		solicitacaoEpiItemManager.expects(once()).method("findBySolicitacaoEpi")
					.with(eq(solicitacaoEpi.getId())).will(returnValue(solicitacaoEpiItems));
		solicitacaoEpiDao.expects(once()).method("update").with(eq(solicitacaoEpi)).isVoid();

		solicitacaoEpiManager.entrega(solicitacaoEpi, epiIds, selectQtdSolicitado);

		assertFalse(solicitacaoEpi.isEntregue());
	}

	public void testEntregaException()
	{
		solicitacaoEpiItemManager.expects(once()).method("entrega").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));

		String[] epiIds = {"1"};
		String[] selectQtdSolicitado = {"3"};

		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);
		solicitacaoEpi.setEntregue(false);

		Exception exception = null;
		try
		{
			solicitacaoEpiManager.entrega(solicitacaoEpi, epiIds, selectQtdSolicitado);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
		assertFalse(solicitacaoEpi.isEntregue());
	}
}