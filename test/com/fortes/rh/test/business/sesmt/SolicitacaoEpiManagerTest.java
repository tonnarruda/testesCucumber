package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.SituacaoSolicitacaoEpi;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoEpiFactory;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.util.DateUtil;

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

	public void testFindAllSelectByData()
	{
		String situacaoSolicitacaoEpi = SituacaoSolicitacaoEpi.TODAS;
		Collection<SolicitacaoEpi> colecao = new ArrayList<SolicitacaoEpi>();
		colecao.add(new SolicitacaoEpi());
		solicitacaoEpiDao.expects(once()).method("findAllSelect").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(colecao));
		solicitacaoEpiItemManager.expects(once()).method("findAllEntregasBySolicitacaoEpi").with(ANYTHING).will(returnValue(new ArrayList<SolicitacaoEpiItem>()));
		solicitacaoEpiItemManager.expects(once()).method("findAllDevolucoesBySolicitacaoEpi").with(ANYTHING).will(returnValue(new ArrayList<SolicitacaoEpiItem>()));

		assertEquals(colecao, solicitacaoEpiManager.findAllSelect(0, 0, 1L, null, null, new Colaborador(), situacaoSolicitacaoEpi, null, SituacaoColaborador.TODOS, null, 'D'));
	}
	
	public void testFindAllSelectByNome() {
		String situacaoSolicitacaoEpi = SituacaoSolicitacaoEpi.TODAS;
		Collection<SolicitacaoEpi> colecao = new ArrayList<SolicitacaoEpi>();
		colecao.add(new SolicitacaoEpi());
		solicitacaoEpiDao.expects(once()).method("findAllSelect").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(colecao));
		solicitacaoEpiItemManager.expects(once()).method("findAllEntregasBySolicitacaoEpi").with(ANYTHING).will(returnValue(new ArrayList<SolicitacaoEpiItem>()));
		solicitacaoEpiItemManager.expects(once()).method("findAllDevolucoesBySolicitacaoEpi").with(ANYTHING).will(returnValue(new ArrayList<SolicitacaoEpiItem>()));
		
		assertEquals(colecao, solicitacaoEpiManager.findAllSelect(0, 0, 1L, null, null, new Colaborador(), situacaoSolicitacaoEpi, null, SituacaoColaborador.TODOS, null, 'N'));
	}

	public void testGetCountByData() {
		solicitacaoEpiDao.expects(once()).method("getCount").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(0));
		assertEquals(Integer.valueOf(0), solicitacaoEpiManager.getCount(1L, new Date(), new Date(), new Colaborador(), SituacaoSolicitacaoEpi.TODAS, null, SituacaoColaborador.TODOS, null, 'D'));
	}
	
	public void testGetCountByNome() {
		solicitacaoEpiDao.expects(once()).method("getCount").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(0));
		assertEquals(Integer.valueOf(0), solicitacaoEpiManager.getCount(1L, new Date(), new Date(), new Colaborador(), SituacaoSolicitacaoEpi.TODAS, null, SituacaoColaborador.TODOS, null, 'N'));
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
		String[] selectMotivoSolicitacaoEpi = new String[]{"1","5"};
		String[] selectTamanhoEpi = new String[]{"1","5"};
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);

		solicitacaoEpiDao.expects(once()).method("save").with(eq(solicitacaoEpi)).isVoid();
		solicitacaoEpiItemManager.expects(once()).method("save").with(new Constraint[] { eq(solicitacaoEpi), eq(epiIds), eq(selectQtdSolicitado) ,eq(selectMotivoSolicitacaoEpi), ANYTHING, eq(false), eq(selectTamanhoEpi) }).isVoid();

		Exception exception = null;
		try
		{
			solicitacaoEpiManager.save(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, null, false, selectTamanhoEpi);
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
			solicitacaoEpiManager.save(solicitacaoEpi, null, null, null, null, false, null);
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
		String[] selectMotivoSolicitacaoEpi = new String[]{"1","2"};
		String[] selectTamanhoEpi = new String[]{"1","5"};
		SolicitacaoEpi solicitacaoEpi = SolicitacaoEpiFactory.getEntity(1L);

		solicitacaoEpiDao.expects(once()).method("update").with(eq(solicitacaoEpi)).isVoid();
		solicitacaoEpiItemManager.expects(once()).method("removeAllBySolicitacaoEpi").with(eq(solicitacaoEpi.getId())).isVoid();
		solicitacaoEpiItemManager.expects(once()).method("save").with(new Constraint[] {eq(solicitacaoEpi), eq(epiIds), eq(selectQtdSolicitado), eq(selectMotivoSolicitacaoEpi), ANYTHING, eq(false), eq(selectTamanhoEpi)}).isVoid();

		solicitacaoEpiManager.update(solicitacaoEpi, epiIds, selectQtdSolicitado, selectMotivoSolicitacaoEpi, selectTamanhoEpi);
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
			new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), epi.isAtivo(), colaborador.getNome(), "Cargo",
					dataSeisMesesAtras.getTime(), validadeUso, null, 1, null);

		SolicitacaoEpi solicitacaoEpi2 =
			new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), epi.isAtivo(), colaborador.getNome(), "Cargo",
					dataSeteMesesAtras.getTime(), validadeUso, null, 1, null);

		Collection<SolicitacaoEpi> colecao = new ArrayList<SolicitacaoEpi>();
		colecao.add(solicitacaoEpi2);
		colecao.add(solicitacaoEpi);

		Collection<SolicitacaoEpi> resultado = null;

		solicitacaoEpiDao.expects(once()).method("findVencimentoEpi").with(new Constraint[]{eq(empresaId),eq(hoje), eq(false), ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colecao));

		resultado = solicitacaoEpiManager.findRelatorioVencimentoEpi(empresaId, hoje, agruparPor, false, null, null, null );

		assertEquals(2, resultado.size());
	}

	public void testFindRelatorioEntregaEpi() throws Exception
	{
		Date hoje = new Date();
		Date data = DateUtil.criarDataMesAno(01, 02, 2011);
		Long empresaId = 1L;
		Integer validadeUso = 30;
		
		Epi epi = EpiFactory.getEntity(1L);
		epi.setNome("EpiNome");
		Colaborador colaborador = ColaboradorFactory.getEntity(100L);
		
		SolicitacaoEpi solicitacaoEpi =	new SolicitacaoEpi(epi.getId(), colaborador.getId(), epi.getNome(), epi.isAtivo(), colaborador.getNome(), "Cargo",	data, validadeUso, null, 1, null);
		
		Collection<SolicitacaoEpi> solicitacaoEpisRetorno = new ArrayList<SolicitacaoEpi>();
		solicitacaoEpisRetorno.add(solicitacaoEpi);
		
		solicitacaoEpiDao.expects(once()).method("findEntregaEpi").with(new Constraint[]{eq(empresaId),ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(solicitacaoEpisRetorno));
		
		assertEquals(1, solicitacaoEpiManager.findRelatorioEntregaEpi(empresaId, hoje, null, null, null, null, 'E', false).size());

		solicitacaoEpiDao.expects(once()).method("findEntregaEpi").with(new Constraint[]{eq(empresaId),ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(solicitacaoEpisRetorno));
		
		try {
			solicitacaoEpiManager.findRelatorioEntregaEpi(empresaId, hoje, null, null, null, null, 'E', false);
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

		solicitacaoEpiDao.expects(once()).method("findVencimentoEpi").with(new Constraint[] {eq(empresaId),eq(hoje), eq(false), ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colecao));
		Exception exception = null;

		try
		{
			colecao = solicitacaoEpiManager.findRelatorioVencimentoEpi(empresaId, hoje, agruparPor, false, null, null, null);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testFndRelatorioDevolucaoEpiSemDevolucoes(){
		solicitacaoEpiDao.expects(once()).method("findDevolucaoEpi").withAnyArguments().will(returnValue(new ArrayList<SolicitacaoEpiItemDevolucao>()));
		Exception exception = new Exception();
		try {
			solicitacaoEpiManager.findRelatorioDevolucaoEpi(1L, new Date(), new Date(), null, null, null, 'E', false);
		} catch (Exception e) {
			exception = e;
		}
		assertEquals("Não existem EPIs a serem listados para os filtros informados.", exception.getMessage());
	}
	
	public void testFndRelatorioDevolucaoEpi(){
		solicitacaoEpiDao.expects(once()).method("findDevolucaoEpi").withAnyArguments().will(returnValue(Arrays.asList(new SolicitacaoEpiItemDevolucao())));
		Exception exception = null;
		try {
			solicitacaoEpiManager.findRelatorioDevolucaoEpi(1L, new Date(), new Date(), null, null, null, 'E', false);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testFindEpisWithItens() {
		solicitacaoEpiDao.expects(once()).method("findEpisWithItens").withAnyArguments().will(returnValue(Arrays.asList(new SolicitacaoEpiItemVO())));
		Exception exception = null;
		try {
			solicitacaoEpiManager.findEpisWithItens(1L, new Date(), new Date(), "", null, null, null, null, 'D');
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testFindByColaboradorId() {
		solicitacaoEpiDao.expects(once()).method("findByColaboradorId").withAnyArguments().will(returnValue(Arrays.asList(new SolicitacaoEpi())));
		Exception exception = null;
		try {
			solicitacaoEpiManager.findByColaboradorId(1L);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
}