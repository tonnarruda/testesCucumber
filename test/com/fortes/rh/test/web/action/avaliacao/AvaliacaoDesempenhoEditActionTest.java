package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.AvaliacaoRespondidaException;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.avaliacao.AvaliacaoDesempenhoEditAction;
import com.fortes.web.tags.CheckBox;

public class AvaliacaoDesempenhoEditActionTest extends MockObjectTestCase
{
	private AvaliacaoDesempenhoEditAction action;
	private Mock manager;
	private Mock colaboradorManager;
	private Mock areaOrganizacionalManager;
	private Mock empresaManager;
	private Mock colaboradorQuestionarioManager;
	private Mock avaliacaoManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new AvaliacaoDesempenhoEditAction();
		manager = new Mock(AvaliacaoDesempenhoManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		empresaManager = new Mock(EmpresaManager.class);
		colaboradorQuestionarioManager = mock(ColaboradorQuestionarioManager.class);
		avaliacaoManager = mock(AvaliacaoManager.class);

		action.setAvaliacaoDesempenhoManager((AvaliacaoDesempenhoManager) manager.proxy());
		action.setAvaliacaoDesempenho(new AvaliacaoDesempenho());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		action.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());
		action.setAvaliacaoManager((AvaliacaoManager) avaliacaoManager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}
	
	public void testPrepareAvaliadosAndPrepareAvaliadores() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		Collection<Colaborador> colaboradoresAvaliados = new ArrayList<Colaborador>();
		colaboradoresAvaliados.add(ColaboradorFactory.getEntity(1000L));
		empresaManager.expects(once()).method("findToList").will(returnValue(new ArrayList<Empresa>()));
		manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(ANYTHING, ANYTHING).will(returnValue(colaboradoresAvaliados));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		
		action.prepareAvaliados();
		
		assertEquals(1, action.getParticipantes().size());
		assertEquals(true, action.getIsAvaliados());
		
		// Avaliadores
		
		empresaManager.expects(once()).method("findToList").will(returnValue(new ArrayList<Empresa>()));
		manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(ANYTHING, ANYTHING).will(returnValue(colaboradoresAvaliados));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		
		action.prepareAvaliadores();
		assertEquals(1, action.getParticipantes().size());
		assertEquals(false, action.getIsAvaliados());
	}
	
	public void testDeleteAvaliadoAndDeleteAvaliador() throws Exception
	{
		action.setParticipanteIds(new Long[]{1L});
		action.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity(2L));
		action.setIsAvaliados(true);
		
		colaboradorQuestionarioManager.expects(once()).method("remove").with(ANYTHING,eq(2L),eq(true));
		
		assertEquals("success",action.deleteAvaliado());
		
		action.setIsAvaliados(false);
		colaboradorQuestionarioManager.expects(once()).method("remove").with(ANYTHING,eq(2L),eq(false));
		
		assertEquals("success",action.deleteAvaliador());
	}
	
	public void testDeleteAvaliadorException() throws Exception
	{
		action.setParticipanteIds(new Long[]{1L});
		action.setAvaliacaoDesempenho(AvaliacaoDesempenhoFactory.getEntity(2L));
		
		action.setActionErrors(null);
		
		// avaliado
		action.setIsAvaliados(true);
		
		colaboradorQuestionarioManager.expects(once()).method("remove").with(ANYTHING,eq(2L),eq(true)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		action.deleteAvaliado();
		assertEquals(1, action.getActionErrors().size());
		
		// avaliador
		action.setIsAvaliados(false);
		action.setActionErrors(null);
		
		colaboradorQuestionarioManager.expects(once()).method("remove").with(ANYTHING,eq(2L),eq(false)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		action.deleteAvaliador();
		assertEquals(1, action.getActionErrors().size());
		
		// Exceção de Avaliação Respondida
		action.setActionMessages(null);
		colaboradorQuestionarioManager.expects(once()).method("remove").with(ANYTHING,eq(2L),eq(false)).will(throwException(new AvaliacaoRespondidaException("Já existem respostas.")));
		action.deleteAvaliador();
		assertEquals(1, action.getActionMessages().size());
	}
	
	public void testInsertAvaliadosAndInsertAvaliadores() throws Exception
	{
		action.setColaboradorsCheck(new String[]{"1","2"});
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		// Avaliado
		action.setIsAvaliados(true);
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("save").with(ANYTHING, ANYTHING, eq(true)).isVoid();
		
		empresaManager.expects(once()).method("findToList").will(returnValue(new ArrayList<Empresa>()));
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals("success",action.insertAvaliados());
		
		// Avaliador
		
		action.setIsAvaliados(false);
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("save").with(ANYTHING, ANYTHING, eq(false)).isVoid();
		
		empresaManager.expects(once()).method("findToList").will(returnValue(new ArrayList<Empresa>()));
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals("success",action.insertAvaliadores());
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getAvaliacaoDesempenhos());
	}

	public void testDelete() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);

		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}
	
	public void testDeleteException() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));;
		assertEquals(action.delete(), "success");
	}

	public void testInsert() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);

		manager.expects(once()).method("save").with(eq(avaliacaoDesempenho)).will(returnValue(avaliacaoDesempenho));

		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);

		manager.expects(once()).method("update").with(eq(avaliacaoDesempenho)).isVoid();

		assertEquals("success", action.update());
	}
	
	public void testprepareInsert() throws Exception
	{
		avaliacaoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Avaliacao>()));
		assertEquals("success",action.prepareInsert());
	}
	
	public void testprepareUpdate() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		action.setTemParticipantesAssociados(false);
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		avaliacaoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Avaliacao>()));
		colaboradorQuestionarioManager.expects(once()).method("verifyTemParticipantesAssociados").with(eq(2L)).will(returnValue(true));
		
		assertEquals("success",action.prepareUpdate());
		assertTrue(action.getTemParticipantesAssociados());
	}
	
	public void testPrepareResultado() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("success",action.prepareResultado());
	}
	
	public void testResultadoPorAvaliador() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho.setAnonima(false);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		action.setOpcaoResultado("avaliador");
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(ColaboradorQuestionarioFactory.getEntity(1L));
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("getPerformance").will(returnValue(colaboradorQuestionarios));
		
		assertEquals("success", action.resultado());
	}
	public void testResultadoPorAvaliadorAnonimo() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho.setAnonima(true);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		action.setOpcaoResultado("avaliador");
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(ColaboradorQuestionarioFactory.getEntity(1L));
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("getPerformance").will(returnValue(colaboradorQuestionarios));
		
		assertEquals("SUCCESS_ANONIMA", action.resultado());
	}

	public void testResultadoPorAvaliadorAnonimoException() throws Exception
	{
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho.setAnonima(true);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		action.setOpcaoResultado("avaliador");
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("getPerformance").will(returnValue(new ArrayList<ColaboradorQuestionario>()));

		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("input", action.resultado());
	}
	
	public void testResultadoPorCriterio() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		avaliacao.setCabecalho("Cabeçalho");
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		action.setOpcaoResultado("criterio");
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		manager.expects(once()).method("montaResultado").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(new ArrayList<ResultadoAvaliacaoDesempenho>()));
		
		assertEquals("SUCCESS_CRITERIO", action.resultado());
	}
	
	public void testResultadoPorCriterioColecaoVaziaException() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		avaliacao.setCabecalho("Cabeçalho");
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		action.setOpcaoResultado("criterio");
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		manager.expects(once()).method("montaResultado").with(ANYTHING,ANYTHING,ANYTHING).will(throwException(new ColecaoVaziaException()));
		//prepare
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("input",action.resultado());
		assertEquals(1, action.getActionMessages().size());
	}
	
	public void testClonar()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("clonar").with(eq(2L)).isVoid();
		
		assertEquals("success",action.clonar());
	}
	public void testClonarException()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("clonar").with(eq(2L)).will(throwException(new Exception()));
		
		assertEquals("success",action.clonar());
		assertEquals(1, action.getActionErrors().size());
	}
	
	public void testLiberar()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		manager.expects(once()).method("liberar").with(eq(avaliacaoDesempenho)).isVoid();
		
		assertEquals("success",action.liberar());
		
		//exception
		manager.expects(once()).method("liberar").with(eq(avaliacaoDesempenho)).will(throwException(new Exception()));
		assertEquals("success",action.liberar());
		assertEquals(1, action.getActionErrors().size());

	}
	
	public void testLiberarExceptionNumeroInsuficienteDeParticipantes()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		manager.expects(once()).method("liberar").with(eq(avaliacaoDesempenho)).isVoid();
		
		assertEquals("success",action.liberar());
		
		//exception
		manager.expects(once()).method("liberar").with(eq(avaliacaoDesempenho)).will(throwException(new FortesException("Não foi possível liberar esta avaliação: número insuficiente de participantes.")));
		assertEquals("success",action.liberar());
		assertEquals(1, action.getActionErrors().size());
		assertEquals("Não foi possível liberar esta avaliação: número insuficiente de participantes.", action.getActionErrors().toArray()[0]);
	}
	
	public void testBloquear()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		manager.expects(once()).method("bloquear").with(eq(avaliacaoDesempenho)).isVoid();
		
		assertEquals("success",action.bloquear());
		
		//exception
		manager.expects(once()).method("bloquear").with(eq(avaliacaoDesempenho)).will(throwException(new Exception()));
		assertEquals("success",action.bloquear());
		assertEquals(1, action.getActionErrors().size());
	}
	
	public void testAvaliacaoDesempenhoQuestionarioList()
	{
		action.setAvaliacaoDesempenho(null);
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = Arrays.asList(AvaliacaoDesempenhoFactory.getEntity(1L));
		action.setAvaliacaoDesempenhos(avaliacaoDesempenhos);
		
		action.setRespondida(' ');
		
		manager.expects(once()).method("findByAvaliador").with(ANYTHING, eq(true), ANYTHING).will(returnValue(avaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliadosByAvaliador").will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		
		assertEquals("success",action.avaliacaoDesempenhoQuestionarioList());
		
		// filtro respondida = '1'
		action.setRespondida('1');
		manager.expects(once()).method("findByAvaliador").with(ANYTHING, eq(true), ANYTHING).will(returnValue(avaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliadosByAvaliador").with(ANYTHING,ANYTHING,eq(true)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		
		assertEquals("success",action.avaliacaoDesempenhoQuestionarioList());
		
		// filtro respondida = '2'
		action.setRespondida('2');
		manager.expects(once()).method("findByAvaliador").with(ANYTHING, eq(true), ANYTHING).will(returnValue(avaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliadosByAvaliador").with(ANYTHING,ANYTHING,eq(false)).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		
		assertEquals("success",action.avaliacaoDesempenhoQuestionarioList());
	}
	
	public void testGetSet() throws Exception
	{
		action.setAvaliacaoDesempenho(null);
		action.getAvaliacaoDesempenho();
		action.getAvaliacaos();
		action.getColaboradorsCheck();
		action.getNomeBusca();
		action.setNomeBusca("Zé");
		action.getAreasCheckList();
		action.getColaboradorsCheckList();
		action.getEmpresas();
		action.getEmpresaId();
		action.setEmpresaId(1L);
		action.getColaboradorQuestionarios();
		action.getRespondida();
		action.setRespondida('1');
		action.getParametros();
		action.getOpcaoResultado();
		action.getPerguntas();
		action.setAgruparPorAspectos(true);
		assertTrue(action.isAgruparPorAspectos());
		action.getResultados();
		action.isExibirRespostas();
		action.setExibirRespostas(true);
		action.isExibirComentarios();
		action.setExibirComentarios(true);
	}
}
