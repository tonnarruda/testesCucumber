package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoServicoManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorManutencaoFactory;
import com.fortes.rh.test.factory.sesmt.HistoricoExtintorFactory;
import com.fortes.rh.web.action.sesmt.ExtintorManutencaoEditAction;

public class ExtintorManutencaoEditActionTest extends MockObjectTestCase
{
	private ExtintorManutencaoEditAction action;
	private Mock manager;
	private Mock estabelecimentoManager;
	private Mock extintorManager;
	private Mock extintorManutencaoServicoManager;


	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ExtintorManutencaoManager.class);
		action = new ExtintorManutencaoEditAction();
		action.setExtintorManutencaoManager((ExtintorManutencaoManager) manager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

		extintorManager = new Mock(ExtintorManager.class);
		action.setExtintorManager((ExtintorManager) extintorManager.proxy());

		extintorManutencaoServicoManager = new Mock(ExtintorManutencaoServicoManager.class);
		action.setExtintorManutencaoServicoManager((ExtintorManutencaoServicoManager) extintorManutencaoServicoManager.proxy());

		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setExtintorManutencao(new ExtintorManutencao());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}


	public void testList() throws Exception
	{
		manager.expects(once()).method("getCount").will(returnValue(0));
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<ExtintorManutencao>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getExtintorManutencaos());
	}

	public void testDelete() throws Exception
	{
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity(1L);
		action.setExtintorManutencao(extintorManutencao);

		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}

	public void testPrepareInsert() throws Exception
	{
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity();
		action.setExtintorManutencao(extintorManutencao);

		extintorManutencaoServicoManager.expects(once()).method("findAll");
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));

		assertEquals("success", action.prepareInsert());
		assertNotNull(action.getEstabelecimentos());
	}

	public void testPrepareUpdate() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setExtintor(extintor);
		historico.setEstabelecimento(estabelecimento);
		
		extintor.setUltimoHistorico(historico);
		
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity(1L);
		extintorManutencao.setExtintor(extintor);
		
		action.setExtintorManutencao(extintorManutencao);

		extintorManutencaoServicoManager.expects(once()).method("findAll").will(returnValue(new ArrayList<ExtintorManutencaoServico>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("findByIdProjection").with(eq(extintorManutencao.getId())).will(returnValue(extintorManutencao));
		extintorManager.expects(once()).method("findAllComHistAtual").will(returnValue(new ArrayList<Extintor>()));

		assertEquals("success", action.prepareUpdate());
		assertNotNull(action.getEstabelecimentos());
		assertNotNull(action.getExtintors());
		assertNotNull(action.getExtintorManutencaoServicos());
	}

	public void testInsert() throws Exception
	{
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity();
		action.setExtintorManutencao(extintorManutencao);
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));

		manager.expects(once()).method("saveOrUpdate").will(returnValue(extintorManutencao));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		extintorManutencaoServicoManager.expects(once()).method("findAll");
		extintorManager.expects(once()).method("findAllComHistAtual").will(returnValue(new ArrayList<Extintor>()));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("saveOrUpdate").will(throwException(new org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		extintorManutencaoServicoManager.expects(once()).method("findAll");
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity(1L);
		action.setExtintorManutencao(extintorManutencao);

		manager.expects(once()).method("saveOrUpdate");

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);

		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setExtintor(extintor);
		historico.setEstabelecimento(estabelecimento);
		
		extintor.setUltimoHistorico(historico);
		
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity(1L);
		extintorManutencao.setExtintor(extintor);
		
		action.setExtintorManutencao(extintorManutencao);

		manager.expects(once()).method("saveOrUpdate").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("findByIdProjection").with(eq(extintorManutencao.getId())).will(returnValue(extintorManutencao));
		extintorManutencaoServicoManager.expects(once()).method("findAll");
		extintorManager.expects(once()).method("findAllComHistAtual");

		assertEquals("input", action.update());
	}


	public void testGetSet() throws Exception
	{
		action.setExtintorManutencao(null);

		assertNotNull(action.getExtintorManutencao());
		assertTrue(action.getExtintorManutencao() instanceof ExtintorManutencao);

		action.setEstabelecimentoId(1L);
		action.setExtintorId(1L);

		action.getEstabelecimentoId();
		action.getExtintorId();
		action.setInicio(null);
		action.setFim(null);
		action.getInicio();
		action.getFim();
		action.setServicoChecks(new String[]{""});
		action.getServicoChecks();
		action.setEstabelecimento(null);
		action.getEstabelecimento();
	}
}