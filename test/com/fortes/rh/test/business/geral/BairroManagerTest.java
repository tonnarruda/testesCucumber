package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.geral.BairroManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.geral.BairroDao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.geral.BairroFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;

public class BairroManagerTest extends MockObjectTestCaseManager<BairroManagerImpl> implements TesteAutomaticoManager
{
	Mock bairroDao = null;
	Mock transactionManager;
	Mock candidatoManager;
	Mock colaboradorManager;
	Mock solicitacaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new BairroManagerImpl();

		transactionManager = new Mock(PlatformTransactionManager.class);
        manager.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
        
		bairroDao = new Mock(BairroDao.class);
		manager.setDao((BairroDao) bairroDao.proxy());
		
		solicitacaoManager = new Mock(SolicitacaoManager.class);
		manager.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
		
		candidatoManager = new Mock(CandidatoManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);
		
		MockSpringUtil.mocks.put("candidatoManager", candidatoManager);		
		MockSpringUtil.mocks.put("colaboradorManager", colaboradorManager);		
		
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
	}

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    }

	public void testList()
	{
		Cidade cidade = new Cidade();
		Bairro bairro = BairroFactory.getEntity();
		bairro.setNome("bairro");
		bairro.setCidade(cidade);

		Collection<Bairro> bairros = new ArrayList<Bairro>();

		bairroDao.expects(once()).method("list").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING}).will(returnValue(bairros));

		assertEquals(bairros.size(), manager.list(1, 10, bairro).size());
	}

	public void testExisteBairro()
	{
		Cidade cidade = new Cidade();
		cidade.setId(1L);

		Bairro bairro = BairroFactory.getEntity();
		bairro.setNome("bairro");
		bairro.setCidade(cidade);

		bairroDao.expects(once()).method("existeBairro").with(eq(bairro)).will(returnValue(true));

		assertTrue(manager.existeBairro(bairro));
	}

	public void testFindAllSelect()
	{
		Cidade cidade = new Cidade();
		cidade.setId(1L);

		Bairro bairro = BairroFactory.getEntity();
		bairro.setNome("bairro");
		bairro.setCidade(cidade);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);
		
		Long[] cidadeIds = new Long[]{cidade.getId()};

		bairroDao.expects(once()).method("findAllSelect").with(eq(cidadeIds)).will(returnValue(bairros));

		assertEquals(1, manager.findAllSelect(cidade.getId()).size());
	}
	
	public void testFindByIdProjection()
	{
		Bairro bairro = BairroFactory.getEntity();
		bairro.setNome("bairro");
		
		bairroDao.expects(once()).method("findByIdProjection").with(eq(bairro.getId())).will(returnValue(bairro));
		
		assertEquals(bairro, manager.findByIdProjection(bairro.getId()));
	}

	public void testGetBairrosBySolicitacao()
	{
		Bairro bairro = BairroFactory.getEntity(1L);
		Bairro bairro2 = BairroFactory.getEntity(2L);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);
		bairros.add(bairro2);

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);
		solicitacao.setBairros(bairros);

		bairroDao.expects(once()).method("getBairrosBySolicitacao").with(eq(solicitacao.getId())).will(returnValue(bairros));

		assertEquals(2, manager.getBairrosBySolicitacao(solicitacao.getId()).size());
	}

	public void testGetBairrosByIds()
	{
		Bairro bairro = BairroFactory.getEntity(1L);
		Bairro bairro2 = BairroFactory.getEntity(2L);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);
		bairros.add(bairro2);

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(1L);
		solicitacao.setBairros(bairros);

		bairroDao.expects(once()).method("getBairrosByIds").with(eq(new Long[]{bairro.getId(),bairro2.getId()})).will(returnValue(bairros));

		assertEquals(2, manager.getBairrosByIds(new Long[]{bairro.getId(),bairro2.getId()}).size());
	}
	
	public void testFindByCidade()
	{
		Bairro bairro = BairroFactory.getEntity(1L);

		Collection<Bairro> bairros = new ArrayList<Bairro>();
		bairros.add(bairro);
		
		Cidade cidade = CidadeFactory.getEntity(1L);
		Long[] cidadeIds = new Long[]{cidade.getId()};
		
		bairroDao.expects(once()).method("findAllSelect").with(eq(cidadeIds)).will(returnValue(bairros));		
		assertEquals(1, manager.findByCidade(cidade).size());
	}
	
	public void testGetArrayBairros()
	{
		bairroDao.expects(once()).method("findBairrosNomes").will(returnValue(new ArrayList<String>()));		
		assertEquals("", manager.getArrayBairros());
	
		Collection<String> bairros = new ArrayList<String>();
		bairros.add("Aldeota");
		bairros.add("Alto do bode");
		
		bairroDao.expects(once()).method("findBairrosNomes").will(returnValue(bairros));		
		assertEquals("\"Aldeota\",\"Alto do bode\"", manager.getArrayBairros());
	}
	
	public void testMigrarRegistros()
	{
		Bairro bairro = BairroFactory.getEntity(1L);
		bairro.setNome("antigo");
		Bairro bairroDestino = BairroFactory.getEntity(2L);
		bairroDestino.setNome("novo");
	
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		
		bairroDao.expects(once()).method("findByIdProjection").with(eq(bairro.getId())).will(returnValue(bairro));		
		bairroDao.expects(once()).method("findByIdProjection").with(eq(bairroDestino.getId())).will(returnValue(bairroDestino));		

		colaboradorManager.expects(once()).method("migrarBairro").with(eq(bairro.getNome()), eq(bairroDestino.getNome())).isVoid();		
		candidatoManager.expects(once()).method("migrarBairro").with(eq(bairro.getNome()), eq(bairroDestino.getNome())).isVoid();		
		solicitacaoManager.expects(once()).method("migrarBairro").with(eq(bairro.getId()), eq(bairroDestino.getId())).isVoid();		
		
		bairroDao.expects(once()).method("remove").with(eq(bairro.getId())).isVoid();				
		transactionManager.expects(once()).method("commit").with(ANYTHING);
		
		Exception excep = null;
		try
		{
			manager.migrarRegistros(bairro, bairroDestino);			
		}
		catch (Exception e)
		{
			excep = e;
		}
		
		assertNull(excep);
	}
	
	public void testMigrarRegistrosException()
	{
		Bairro bairro = BairroFactory.getEntity(1L);
		bairro.setNome("antigo");
		Bairro bairroDestino = BairroFactory.getEntity(2L);
		bairroDestino.setNome("novo");
		
		transactionManager.expects(once()).method("getTransaction").with(ANYTHING).will(returnValue(null));
		
		bairroDao.expects(once()).method("findByIdProjection").with(eq(bairro.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));						
		transactionManager.expects(once()).method("rollback").with(ANYTHING);
		
		Exception excep = null;
		try
		{
			manager.migrarRegistros(bairro, bairroDestino);			
		}
		catch (Exception e)
		{
			excep = e;
		}
		
		assertNotNull(excep);
	}
	
	public void testFindByCidadeNull()
	{
		Cidade cidade = null;

		assertEquals(0, manager.findByCidade(cidade).size());
	}

	@Override
	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(bairroDao);
	}
}
