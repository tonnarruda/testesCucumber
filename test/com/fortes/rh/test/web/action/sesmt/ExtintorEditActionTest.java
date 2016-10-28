package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.ExtintorInspecaoManager;
import com.fortes.rh.business.sesmt.ExtintorManager;
import com.fortes.rh.business.sesmt.ExtintorManutencaoManager;
import com.fortes.rh.business.sesmt.HistoricoExtintorManager;
import com.fortes.rh.model.dicionario.TipoExtintor;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorInspecaoFactory;
import com.fortes.rh.test.factory.sesmt.HistoricoExtintorFactory;
import com.fortes.rh.web.action.sesmt.ExtintorEditAction;

public class ExtintorEditActionTest extends MockObjectTestCase
{
	private ExtintorEditAction action;
	private Mock manager;
	private Mock estabelecimentoManager;
	private Mock extintorInspecaoManager;
	private Mock extintorManutencaoManager;
	private Mock historicoExtintorManager;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ExtintorManager.class);
		action = new ExtintorEditAction();
		action.setExtintorManager((ExtintorManager) manager.proxy());

		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());

		extintorInspecaoManager = new Mock(ExtintorInspecaoManager.class);
		action.setExtintorInspecaoManager((ExtintorInspecaoManager) extintorInspecaoManager.proxy());
		
		extintorManutencaoManager = new Mock(ExtintorManutencaoManager.class);
		action.setExtintorManutencaoManager((ExtintorManutencaoManager) extintorManutencaoManager.proxy());

		historicoExtintorManager = new Mock(HistoricoExtintorManager.class);
		action.setHistoricoExtintorManager((HistoricoExtintorManager) historicoExtintorManager.proxy());

		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setExtintor(new Extintor());
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
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Extintor>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getExtintors());
	}

	public void testDelete() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		action.setExtintor(extintor);

		manager.expects(once()).method("remove");
		
		manager.expects(once()).method("getCount").will(returnValue(1));
		manager.expects(once()).method("findAllSelect");
		
		assertEquals(action.delete(), "success");
	}

	public void testPrepare() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		action.setExtintor(extintor);

		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("getFabricantes").with(ANYTHING).will(returnValue("extint"));
		manager.expects(once()).method("getLocalizacoes").with(ANYTHING).will(returnValue("extint"));
		manager.expects(once()).method("findById").with(eq(extintor.getId())).will(returnValue(extintor));
		historicoExtintorManager.expects(once()).method("findByExtintor").with(eq(extintor.getId())).will(returnValue(new ArrayList<HistoricoExtintor>()));

		assertEquals("success", action.prepare());
		assertNotNull(action.getEstabelecimentos());
	}

	public void testInsert() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		action.setExtintor(extintor);
		action.setAtivo('S');

		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setExtintor(extintor);
		historico.setLocalizacao("Loc");
		historico.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		action.setHistoricoExtintor(historico);
		
		manager.expects(once()).method("save").with(eq(extintor)).will(returnValue(extintor));
		historicoExtintorManager.expects(once()).method("save").with(eq(historico));

		assertEquals("success", action.insert());
	}

	public void testInsertComInspecaoEMovimetacao() throws Exception
	{
		Date hoje = new Date();
		
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		extintorInspecao.setData(hoje);
		
		Extintor extintor = ExtintorFactory.getEntity(1L);
		action.setExtintor(extintor);
		action.setAtivo('S');
		action.setExtintorInspecao(extintorInspecao);
		action.setDataHidro(hoje);
		
		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setExtintor(extintor);
		historico.setLocalizacao("Loc");
		historico.setEstabelecimento(EstabelecimentoFactory.getEntity(1L));
		action.setHistoricoExtintor(historico);
		
		extintorInspecaoManager.expects(once()).method("saveOrUpdate").with(ANYTHING, ANYTHING);
		extintorManutencaoManager.expects(once()).method("save").with(ANYTHING, ANYTHING);
		manager.expects(once()).method("save").with(eq(extintor)).will(returnValue(extintor));
		historicoExtintorManager.expects(once()).method("save").with(eq(historico));
		
		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		action.setExtintor(extintor);

		manager.expects(once()).method("update").with(eq(extintor)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		action.setExtintor(extintor);

		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
		manager.expects(once()).method("getFabricantes").with(ANYTHING).will(returnValue("extint"));
		manager.expects(once()).method("getLocalizacoes").with(ANYTHING).will(returnValue("extint"));
		manager.expects(once()).method("findById").with(eq(extintor.getId())).will(returnValue(extintor));
		historicoExtintorManager.expects(once()).method("findByExtintor").with(eq(extintor.getId())).will(returnValue(new ArrayList<HistoricoExtintor>()));

		assertEquals("input", action.update());
	}


	public void testGetSet() throws Exception
	{
		action.setExtintor(null);

		assertNotNull(action.getExtintor());
		assertTrue(action.getExtintor() instanceof Extintor);

		action.setTipos(new TipoExtintor());
		assertNotNull(action.getTipos());
		action.setAtivo('N');
		action.getAtivo();
		action.setNumeroBusca(123);
		action.getNumeroBusca();
		action.setTipoBusca(TipoExtintor.AGUA_GAS);
		assertEquals(action.getTipoBusca(), TipoExtintor.AGUA_GAS);

	}

	public void setExtintorInspecaoManager(Mock extintorInspecaoManager) {
		this.extintorInspecaoManager = extintorInspecaoManager;
	}

	public void setExtintorManutencaoManager(Mock extintorManutencaoManager) {
		this.extintorManutencaoManager = extintorManutencaoManager;
	}
}