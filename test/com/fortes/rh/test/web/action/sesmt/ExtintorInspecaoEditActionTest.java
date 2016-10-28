package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoItemManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorInspecaoFactory;
import com.fortes.rh.test.factory.sesmt.HistoricoExtintorFactory;
import com.fortes.rh.web.action.sesmt.ExtintorInspecaoEditAction;

public class ExtintorInspecaoEditActionTest extends MockObjectTestCase
{
	private ExtintorInspecaoEditAction action;
	private Mock manager;
	private Mock estabelecimentoManager;
	private Mock extintorManager;
	private Mock extintorInspecaoItemManager;


	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ExtintorInspecaoManager.class);
		action = new ExtintorInspecaoEditAction();
		action.setExtintorInspecaoManager((ExtintorInspecaoManager) manager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

		extintorManager = new Mock(ExtintorManager.class);
		action.setExtintorManager((ExtintorManager) extintorManager.proxy());

		extintorInspecaoItemManager = new Mock(ExtintorInspecaoItemManager.class);
		action.setExtintorInspecaoItemManager((ExtintorInspecaoItemManager) extintorInspecaoItemManager.proxy());

		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setExtintorInspecao(new ExtintorInspecao());
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
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<ExtintorInspecao>()));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getExtintorInspecaos());
	}

	public void testDelete() throws Exception
	{
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity(1L);
		action.setExtintorInspecao(extintorInspecao);

		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}

	public void testPrepareInsert() throws Exception
	{
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		action.setExtintorInspecao(extintorInspecao);

		extintorInspecaoItemManager.expects(once()).method("findAll");
		manager.expects(once()).method("getEmpresasResponsaveis");
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));

		assertEquals("success", action.prepareInsert());
		assertNotNull(action.getEstabelecimentos());
	}

	public void testPrepareUpdate() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		
		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setExtintor(extintor);
		historico.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));

		extintor.setUltimoHistorico(historico);
		
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity(1L);
		extintorInspecao.setExtintor(extintor);
		
		action.setExtintorInspecao(extintorInspecao);

		extintorInspecaoItemManager.expects(once()).method("findAll").will(returnValue(new ArrayList<ExtintorInspecaoItem>()));
		manager.expects(once()).method("getEmpresasResponsaveis");
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("findByIdProjection").with(eq(extintorInspecao.getId())).will(returnValue(extintorInspecao));
		
		extintorManager.expects(once()).method("findAllComHistAtual").with(eq(true), eq(1L), eq(null)).will(returnValue(new ArrayList<Extintor>()));

		assertEquals("success", action.prepareUpdate());
		assertNotNull(action.getEstabelecimentos());
		assertNotNull(action.getExtintors());
		assertNotNull(action.getExtintorInspecaoItems());
	}

	public void testInsert() throws Exception
	{
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		action.setExtintorInspecao(extintorInspecao);
		action.setEstabelecimento(EstabelecimentoFactory.getEntity(2L));

		manager.expects(once()).method("saveOrUpdate").will(returnValue(extintorInspecao));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		extintorManager.expects(once()).method("findAllComHistAtual");
		extintorInspecaoItemManager.expects(once()).method("findAll");
		manager.expects(once()).method("getEmpresasResponsaveis");

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("saveOrUpdate").will(throwException(new org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		extintorInspecaoItemManager.expects(once()).method("findAll");
		manager.expects(once()).method("getEmpresasResponsaveis");
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity(1L);
		action.setExtintorInspecao(extintorInspecao);

		manager.expects(once()).method("saveOrUpdate");

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		
		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setExtintor(extintor);
		historico.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));

		extintor.setUltimoHistorico(historico);
		
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity(1L);
		extintorInspecao.setExtintor(extintor);
		
		action.setExtintorInspecao(extintorInspecao);

		manager.expects(once()).method("saveOrUpdate").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("findByIdProjection").with(eq(extintorInspecao.getId())).will(returnValue(extintorInspecao));
		extintorInspecaoItemManager.expects(once()).method("findAll");
		manager.expects(once()).method("getEmpresasResponsaveis");
		extintorManager.expects(once()).method("findAllComHistAtual");

		assertEquals("input", action.update());
	}


	public void testGetSet() throws Exception
	{
		action.setExtintorInspecao(null);

		assertNotNull(action.getExtintorInspecao());
		assertTrue(action.getExtintorInspecao() instanceof ExtintorInspecao);

		action.setEstabelecimentoId(1L);
		action.setExtintorId(1L);

		action.getEstabelecimentoId();
		action.getExtintorId();
		action.setInicio(null);
		action.setFim(null);
		action.getInicio();
		action.getFim();
		action.setItemChecks(new String[]{""});
		action.getItemChecks();
		action.getEstabelecimento();
		action.getEmpresasResponsaveis();
	}
}