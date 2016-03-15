package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.QuestionarioRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.avaliacao.AvaliacaoEditAction;

public class AvaliacaoEditActionTest extends MockObjectTestCase
{
	private AvaliacaoEditAction action;
	private Mock manager;
	private Mock perguntaManager;
	private Mock empresaManager;
	private Mock periodoExperienciaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AvaliacaoManager.class);
		action = new AvaliacaoEditAction();
		action.setAvaliacaoManager((AvaliacaoManager) manager.proxy());
		
		perguntaManager = mock(PerguntaManager.class);
		action.setPerguntaManager((PerguntaManager) perguntaManager.proxy());

		empresaManager = mock(EmpresaManager.class);
		action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		
		periodoExperienciaManager = mock(PeriodoExperienciaManager.class);
		action.setPeriodoExperienciaManager((PeriodoExperienciaManager) periodoExperienciaManager.proxy());
		
		action.setAvaliacao(new Avaliacao());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
    	MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("getCount").will(returnValue(0));
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Avaliacao>()));
		empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getAvaliacao());
	}

	public void testDelete() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacao(avaliacao);

		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}

	public void testInsert() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacao(avaliacao);

		manager.expects(once()).method("save").with(eq(avaliacao)).will(returnValue(avaliacao));

		assertEquals("success", action.insert());
	}
	
	public void testPrepareInsert() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setAtivo(false);
		action.setAvaliacao(avaliacao);
		
		periodoExperienciaManager.expects(once()).method("findAllSelect").with(eq(1L), eq(false)).will(returnValue(new ArrayList<PeriodoExperiencia>()));
		
		assertEquals("success",action.prepareInsert());
		assertTrue(action.getAvaliacao().isAtivo());
	}
	public void testPrepareUpdate() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacao(avaliacao);
		
		manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(avaliacao));
		periodoExperienciaManager.expects(once()).method("findAllSelect").with(eq(1L), eq(false)).will(returnValue(new ArrayList<PeriodoExperiencia>()));
		
		assertEquals("success",action.prepareUpdate());
	}
	
	public void testVisualizar() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacao(avaliacao);
		
		manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(avaliacao));
		perguntaManager.expects(once()).method("getPerguntasRespostaByQuestionarioAgrupadosPorAspecto").with(eq(1L), ANYTHING).will(returnValue(new ArrayList<Pergunta>()));
		
		assertEquals("success",action.visualizar());
		assertEquals("list.action?modeloAvaliacao=D", action.getUrlVoltar());
	}

	public void testUpdate() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		action.setAvaliacao(avaliacao);

		manager.expects(once()).method("update").with(eq(avaliacao)).isVoid();

		assertEquals("success", action.update());
	}
	
	public void testImprimir() throws Exception
    {
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(22L);

    	Map<String, Object> parametros = new HashMap<String, Object>();
    	parametros.put("test", new Object());

    	action.setAvaliacao(avaliacao);

    	manager.expects(once()).method("findById").with(eq(avaliacao.getId())).will(returnValue(avaliacao));
    	manager.expects(once()).method("getQuestionarioRelatorioCollection").with(eq(avaliacao),ANYTHING).will(returnValue(new ArrayList<QuestionarioRelatorio>()));

    	assertEquals("success", action.imprimir());
    	assertNotNull(action.getParametros());
    	assertNotNull(action.getDataSource());
    }

}
