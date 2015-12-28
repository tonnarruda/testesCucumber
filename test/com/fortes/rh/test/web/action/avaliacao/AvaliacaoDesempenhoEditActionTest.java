package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.ParticipanteAvaliacaoDesempenhoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ResultadoAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.FiltroSituacaoAvaliacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
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
	private Mock parametrosDoSistemaManager ;
	private Mock participanteAvaliacaoDesempenhoManager;
	private Mock configuracaoCompetenciaAvaliacaoDesempenhoManager;

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
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		participanteAvaliacaoDesempenhoManager = new Mock(ParticipanteAvaliacaoDesempenhoManager.class);
		configuracaoCompetenciaAvaliacaoDesempenhoManager = new Mock(ConfiguracaoCompetenciaAvaliacaoDesempenhoManager.class);

		action.setAvaliacaoDesempenhoManager((AvaliacaoDesempenhoManager) manager.proxy());
		action.setAvaliacaoDesempenho(new AvaliacaoDesempenho());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		action.setColaboradorQuestionarioManager((ColaboradorQuestionarioManager) colaboradorQuestionarioManager.proxy());
		action.setAvaliacaoManager((AvaliacaoManager) avaliacaoManager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
	    action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager .proxy());
	    action.setParticipanteAvaliacaoDesempenhoManager((ParticipanteAvaliacaoDesempenhoManager) participanteAvaliacaoDesempenhoManager.proxy());
	    action.setConfiguracaoCompetenciaAvaliacaoDesempenhoManager((ConfiguracaoCompetenciaAvaliacaoDesempenhoManager) configuracaoCompetenciaAvaliacaoDesempenhoManager.proxy());
		
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
	
	public void testPrepareParticipantes() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	parametrosDoSistema.setCompartilharCandidatos(true);
		
    	ParticipanteAvaliacaoDesempenho avaliado = new ParticipanteAvaliacaoDesempenho();
    	avaliado.setAvaliacaoDesempenho(avaliacaoDesempenho);
    	avaliado.setColaborador(ColaboradorFactory.getEntity(1000L));
    	
		Collection<ParticipanteAvaliacaoDesempenho> avaliados = new ArrayList<ParticipanteAvaliacaoDesempenho>();
		avaliados.add(avaliado);
		
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
		manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(avaliacaoDesempenho));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findParticipantes").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(avaliados));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findParticipantes").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(avaliados));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		
		colaboradorQuestionarioManager.expects(once()).method("findAvaliadosByAvaliador").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		
		action.prepareParticipantes();
		
		assertEquals(1, action.getParticipantesAvaliados().size());
		assertEquals(1, action.getParticipantesAvaliadores().size());
	}
	
	public void testSaveParticipantes() throws Exception
	{
		action.setColaboradorsCheck(new String[]{"1","2"});
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	parametrosDoSistema.setCompartilharCandidatos(true);
		
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		
		participanteAvaliacaoDesempenhoManager.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();
		participanteAvaliacaoDesempenhoManager.expects(once()).method("removeNotIn").with(ANYTHING, ANYTHING, ANYTHING).isVoid();
		
		participanteAvaliacaoDesempenhoManager.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();
		participanteAvaliacaoDesempenhoManager.expects(once()).method("removeNotIn").with(ANYTHING, ANYTHING, ANYTHING).isVoid();
		
		colaboradorQuestionarioManager.expects(once()).method("saveOrUpdate").with(ANYTHING).isVoid();
		colaboradorQuestionarioManager.expects(once()).method("removeNotIn").with(ANYTHING, ANYTHING).isVoid();
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findParticipantes").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Colaborador>()));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findParticipantes").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Colaborador>()));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals("success", action.saveParticipantes());
	}
	
	public void testPrepareCompetencias() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		Collection<Colaborador> colaboradoresAvaliadores = new ArrayList<Colaborador>();
		colaboradoresAvaliadores.add(ColaboradorFactory.getEntity(1000L));
		
		Collection<FaixaSalarial> faixasSalariais = new ArrayList<FaixaSalarial>();
		faixasSalariais.add(FaixaSalarialFactory.getEntity(1000L));
		
		Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos = new ArrayList<ConfiguracaoCompetenciaAvaliacaoDesempenho>();
		configuracaoCompetenciaAvaliacaoDesempenhos.add(new ConfiguracaoCompetenciaAvaliacaoDesempenho());
		
		manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(avaliacaoDesempenho));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findFaixasSalariaisDosAvaliadosComCompetenciasByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING}).will(returnValue(faixasSalariais));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findColaboradoresParticipantes").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(colaboradoresAvaliadores));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findFaixasSalariaisDosAvaliadosByAvaliador").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(faixasSalariais));
		configuracaoCompetenciaAvaliacaoDesempenhoManager.expects(once()).method("findByAvaliador").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING}).will(returnValue(configuracaoCompetenciaAvaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findRespondidasByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING}).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		
		action.prepareCompetencias();
		
		assertEquals(1, action.getFaixaSalariais().size());
		assertEquals(1, action.getAvaliadors().size());
	}
	
	public void testPrepareCompetenciasQuandoAvaliacaoLiberadaAndAvaliacoesRespondidas() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		avaliacaoDesempenho.setLiberada(true);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		Collection<Colaborador> colaboradoresAvaliadores = new ArrayList<Colaborador>();
		colaboradoresAvaliadores.add(ColaboradorFactory.getEntity(1000L));
		
		Collection<FaixaSalarial> faixasSalariais = new ArrayList<FaixaSalarial>();
		faixasSalariais.add(FaixaSalarialFactory.getEntity(1000L));
		
		Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos = new ArrayList<ConfiguracaoCompetenciaAvaliacaoDesempenho>();
		configuracaoCompetenciaAvaliacaoDesempenhos.add(new ConfiguracaoCompetenciaAvaliacaoDesempenho());
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(new ColaboradorQuestionario());
		
		manager.expects(once()).method("findById").with(eq(1L)).will(returnValue(avaliacaoDesempenho));
		configuracaoCompetenciaAvaliacaoDesempenhoManager.expects(once()).method("findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho").with(new Constraint[] {ANYTHING}).will(returnValue(faixasSalariais));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findColaboradoresParticipantes").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(colaboradoresAvaliadores));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findFaixasSalariaisDosAvaliadosByAvaliador").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(faixasSalariais));
		configuracaoCompetenciaAvaliacaoDesempenhoManager.expects(once()).method("findByAvaliador").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING}).will(returnValue(configuracaoCompetenciaAvaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findRespondidasByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING}).will(returnValue(colaboradorQuestionarios));
		
		action.prepareCompetencias();
		
		assertEquals(1, action.getFaixaSalariais().size());
		assertEquals(1, action.getAvaliadors().size());
		assertEquals(false, action.isEditarCompetencias());
	}
	
	public void testSaveCompetencias() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		
		configuracaoCompetenciaAvaliacaoDesempenhoManager.expects(once()).method("save").with(ANYTHING, ANYTHING).isVoid();
		configuracaoCompetenciaAvaliacaoDesempenhoManager.expects(once()).method("removeNotIn").with(ANYTHING, ANYTHING).isVoid();
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findFaixasSalariaisDosAvaliadosComCompetenciasByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING}).will(returnValue(new ArrayList<FaixaSalarial>()));
		participanteAvaliacaoDesempenhoManager.expects(once()).method("findColaboradoresParticipantes").with(new Constraint[] {ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Colaborador>()));
		colaboradorQuestionarioManager.expects(once()).method("findRespondidasByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING}).will(returnValue(new ArrayList<ColaboradorQuestionario>()));

		assertEquals("success", action.saveCompetencias());
	}
	
	public void testList() throws Exception
	{
		avaliacaoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Avaliacao>()));
		manager.expects(once()).method("findCountTituloModeloAvaliacao").will(returnValue(new Integer(1)));
		manager.expects(once()).method("findTituloModeloAvaliacao").will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getAvaliacaoDesempenhos());
	}

	public void testDelete() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);

		manager.expects(once()).method("remover");
		assertEquals(action.delete(), "success");
	}
	
	public void testDeleteException() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("remover").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));;
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
		action.setTemAvaliacoesRespondidas(false);
		
		Collection<ColaboradorQuestionario> colaboradorQuestionarios = new ArrayList<ColaboradorQuestionario>();
		colaboradorQuestionarios.add(new ColaboradorQuestionario());
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		avaliacaoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Avaliacao>()));
		colaboradorQuestionarioManager.expects(once()).method("findRespondidasByAvaliacaoDesempenho").with(eq(2L)).will(returnValue(colaboradorQuestionarios));
		
		assertEquals("success",action.prepareUpdate());
		assertTrue(action.isTemAvaliacoesRespondidas());
	}
	
	public void testPrepareResultado() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Colaborador>()));
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
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
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setCompartilharColaboradores(true);
		parametrosDoSistema.setCompartilharCandidatos(true);
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("getPerformance").will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");

		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Colaborador>()));
		
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
		manager.expects(once()).method("montaResultado").with(ANYTHING,ANYTHING,ANYTHING, ANYTHING).will(returnValue(new ArrayList<ResultadoAvaliacaoDesempenho>()));
		
		assertEquals("SUCCESS_CRITERIO", action.resultado());
	}
	
	public void testResultadoPorCriterioColecaoVaziaException() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
		avaliacao.setCabecalho("Cabeçalho");
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setCompartilharColaboradores(true);
		parametrosDoSistema.setCompartilharCandidatos(true);

		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		action.setOpcaoResultado("criterio");
		
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		manager.expects(once()).method("montaResultado").with(ANYTHING,ANYTHING,ANYTHING, ANYTHING).will(throwException(new ColecaoVaziaException()));
		//prepare
		manager.expects(once()).method("findById").with(eq(2L)).will(returnValue(avaliacaoDesempenho));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Colaborador>()));
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
		assertEquals("input",action.resultado());
		assertEquals(1, action.getActionMessages().size());
	}
	
	public void testClonar()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("clonar").with(eq(2L), ANYTHING, ANYTHING).isVoid();
		
		assertEquals("success", action.clonar());
	}
	public void testClonarException()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		
		manager.expects(once()).method("clonar").with(eq(2L), ANYTHING, ANYTHING).will(throwException(new Exception()));
		
		assertEquals("success",action.clonar());
		assertEquals(1, action.getActionErrors().size());
	}
	
	public void testLiberar()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		avaliacaoDesempenho.setInicio(DateUtil.criarDataMesAno(11, 07, 2011));
		avaliacaoDesempenho.setFim(DateUtil.criarDataMesAno(15, 07, 2011));
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		colaboradorQuestionarioManager.expects(once()).method("validaAssociacao").withAnyArguments().isVoid();
		manager.expects(once()).method("findByIdProjection").with(eq(avaliacaoDesempenho.getId())).will(returnValue(avaliacaoDesempenho));
		
		manager.expects(once()).method("liberarOrBloquear").with(eq(avaliacaoDesempenho), eq(true)).isVoid();

		assertEquals("success",action.liberar());
		
		//exception
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		manager.expects(once()).method("findByIdProjection").with(eq(avaliacaoDesempenho.getId())).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("validaAssociacao").withAnyArguments().isVoid();
		manager.expects(once()).method("liberarOrBloquear").with(eq(avaliacaoDesempenho), eq(true)).will(throwException(new Exception()));
		assertEquals("success",action.liberar());
		assertEquals(1, action.getActionErrors().size());

	}
	
	public void testLiberarExceptionNumeroInsuficienteDeParticipantes()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		manager.expects(once()).method("findByIdProjection").with(eq(avaliacaoDesempenho.getId())).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("validaAssociacao").withAnyArguments().isVoid();
		manager.expects(once()).method("liberarOrBloquear").with(eq(avaliacaoDesempenho),eq(true)).isVoid();
		
		assertEquals("success",action.liberar());
		  
		//exception
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		colaboradorManager.expects(once()).method("findParticipantesDistinctByAvaliacaoDesempenho").with(eq(avaliacaoDesempenho.getId()), ANYTHING, ANYTHING).will(returnValue(new ArrayList<Colaborador>(){}));
		manager.expects(once()).method("findByIdProjection").with(eq(avaliacaoDesempenho.getId())).will(returnValue(avaliacaoDesempenho));
		colaboradorQuestionarioManager.expects(once()).method("validaAssociacao").withAnyArguments().isVoid();
		manager.expects(once()).method("liberarOrBloquear").with(eq(avaliacaoDesempenho),eq(true)).will(throwException(new FortesException("Não foi possível liberar esta avaliação: número insuficiente de participantes.")));
		assertEquals("success",action.liberar());
		assertEquals(1, action.getActionWarnings().size());
		assertEquals("Não foi possível liberar esta avaliação: número insuficiente de participantes.", action.getActionWarnings().toArray()[0]);
	}

	public void testEnviarLembrete() throws Exception
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		manager.expects(once()).method("enviarLembrete").with(ANYTHING, ANYTHING).isVoid();
		
		assertEquals("success",action.enviarLembrete());
	}
	
	public void testBloquear()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(2L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		manager.expects(once()).method("liberarOrBloquear").with(eq(avaliacaoDesempenho),eq(false)).isVoid();
		
		assertEquals("success",action.bloquear());
		
		//exception
		manager.expects(once()).method("liberarOrBloquear").with(eq(avaliacaoDesempenho),eq(false)).will(throwException(new Exception()));
		assertEquals("success",action.bloquear());
		assertEquals(1, action.getActionErrors().size());
	}
	
	public void testAvaliacaoDesempenhoQuestionarioList()
	{
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhos = Arrays.asList(avaliacaoDesempenho);
		action.setAvaliacaoDesempenhos(avaliacaoDesempenhos);
		
		action.setRespondida('T');
		MockSecurityUtil.roles = new String[]{"ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO"};
		
		manager.expects(once()).method("findByAvaliador").with(ANYTHING, eq(true), ANYTHING).will(returnValue(avaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliadosByAvaliador").will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(new Constraint[]{eq(avaliacaoDesempenho.getId()), eq(false), eq(null), eq(null), eq(null)}).will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("success",action.avaliacaoDesempenhoQuestionarioList());
		
		// filtro respondida = 'R'
		action.setRespondida('R');
		manager.expects(once()).method("findByAvaliador").with(ANYTHING, eq(true), ANYTHING).will(returnValue(avaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliadosByAvaliador").with(new Constraint[]{ANYTHING,ANYTHING,eq(FiltroSituacaoAvaliacao.RESPONDIDA.getOpcao()),eq(false),eq(true), eq(false)}).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(new Constraint[]{eq(avaliacaoDesempenho.getId()), eq(false), eq(null), eq(null), eq(null)}).will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("success",action.avaliacaoDesempenhoQuestionarioList());
		
		// filtro respondida = 'N'
		action.setRespondida('N');
		manager.expects(once()).method("findByAvaliador").with(ANYTHING, eq(true), ANYTHING).will(returnValue(avaliacaoDesempenhos));
		colaboradorQuestionarioManager.expects(once()).method("findAvaliadosByAvaliador").with(new Constraint[]{ANYTHING,ANYTHING,eq(FiltroSituacaoAvaliacao.NAO_RESPONDIDA.getOpcao()),eq(false),eq(true), eq(false)}).will(returnValue(new ArrayList<ColaboradorQuestionario>()));
		empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));
		colaboradorManager.expects(once()).method("findParticipantesDistinctComHistoricoByAvaliacaoDesempenho").with(new Constraint[]{eq(avaliacaoDesempenho.getId()), eq(false), eq(null), eq(null), eq(null)}).will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("success",action.avaliacaoDesempenhoQuestionarioList());
	}
	
	public void testPrepareAnaliseDesempenhoCompetenciaColaborador() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		manager.expects(once()).method("findTituloModeloAvaliacao").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		
		assertEquals("success",action.prepareAnaliseDesempenhoCompetenciaColaborador());
	}
	
	public void testAnaliseDesempenhoCompetenciaColaboradorException() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(new AvaliacaoDesempenho()));
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		manager.expects(once()).method("findTituloModeloAvaliacao").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		
		assertEquals("input",action.analiseDesempenhoCompetenciaColaborador());
	}
	
	public void testAnaliseDesempenhoCompetenciaColaboradorSemCompetencias() throws Exception
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		action.setAvaliado(avaliado);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);

		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity();
		parametrosDoSistema.setCompartilharColaboradores(true);
		
		ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = new ResultadoAvaliacaoDesempenho();
		
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(new AvaliacaoDesempenho()));
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		manager.expects(once()).method("getResultadoAvaliacaoDesempenho").withAnyArguments().will(returnValue(resultadoAvaliacaoDesempenho));
		manager.expects(once()).method("findTituloModeloAvaliacao").withAnyArguments().will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
		
		assertEquals("input",action.analiseDesempenhoCompetenciaColaborador());
		assertEquals(1, action.getActionMessages().size());
	}
	
	public void testAnaliseDesempenhoCompetenciaColaborador() throws Exception
	{
		Colaborador avaliado = ColaboradorFactory.getEntity();
		action.setAvaliado(avaliado);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity(1L);
		action.setAvaliacaoDesempenho(avaliacaoDesempenho);

		Competencia BRL = new Competencia();
		
		Collection<Competencia> competencias = new ArrayList<Competencia>();
		competencias.add(BRL);
		
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(new AvaliacaoDesempenho()));
		ResultadoAvaliacaoDesempenho resultadoAvaliacaoDesempenho = new ResultadoAvaliacaoDesempenho();
		resultadoAvaliacaoDesempenho.setCompetencias(competencias);
		
		manager.expects(once()).method("getResultadoAvaliacaoDesempenho").withAnyArguments().will(returnValue(resultadoAvaliacaoDesempenho));
		
		assertEquals("success",action.analiseDesempenhoCompetenciaColaborador());
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
		action.setRespondida('R');
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
