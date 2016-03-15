package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.PerguntaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.relatorio.ResultadoQuestionario;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.avaliacao.AvaliacaoExperienciaEditAction;
import com.fortes.web.tags.CheckBox;

public class AvaliacaoExperienciaEditActionTest extends MockObjectTestCase
{
	private AvaliacaoExperienciaEditAction action;
	private Mock manager;
	private Mock perguntaManager;
	private Mock areaOrganizacionalManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(AvaliacaoManager.class);
		action = new AvaliacaoExperienciaEditAction();
		action.setAvaliacaoManager((AvaliacaoManager) manager.proxy());
		
		perguntaManager = mock(PerguntaManager.class);
		action.setPerguntaManager((PerguntaManager) perguntaManager.proxy());
		
		empresaManager = mock(EmpresaManager.class);
		action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
		
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

		action.setAvaliacaoExperiencia(new Avaliacao());
		
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

	public void testImprimeResultado() throws Exception
    {
    	action.setAgruparPorAspectos(true);
    	Avaliacao avaliacaoExperiencia = AvaliacaoFactory.getEntity(1L);
    	action.setAvaliacaoExperiencia(avaliacaoExperiencia);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresa(empresa);
    	
    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);

    	manager.expects(once()).method("findById").with(eq(avaliacaoExperiencia.getId())).will(returnValue(avaliacaoExperiencia));
    	perguntaManager.expects(once()).method("findByQuestionarioAspectoPergunta").with(eq(avaliacaoExperiencia.getId()), ANYTHING, ANYTHING, eq(true)).will(returnValue(perguntas));
    	manager.expects(once()).method("montaResultado").will(returnValue(new ArrayList<ResultadoQuestionario>()));

    	assertEquals("success", action.imprimeResultado());
    	assertNotNull(action.getPerguntas());
    	assertNotNull(action.getTipoPergunta());
    }
	
	public void testImprimeResultadoSemPerguntas() throws Exception
    {
    	action.setAgruparPorAspectos(true);
    	action.setExibirCabecalho(false);
    	Avaliacao avaliacaoExperiencia = AvaliacaoFactory.getEntity(1L);
    	action.setAvaliacaoExperiencia(avaliacaoExperiencia);

    	manager.expects(once()).method("findById").with(eq(avaliacaoExperiencia.getId())).will(returnValue(avaliacaoExperiencia));
    	perguntaManager.expects(once()).method("findByQuestionarioAspectoPergunta").with(eq(avaliacaoExperiencia.getId()), ANYTHING, ANYTHING, eq(true)).will(returnValue(new ArrayList<Pergunta>()));
    	
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	
    	//prepareResultado
    	manager.expects(once()).method("findAllSelect").with(new Constraint[] { eq(null), eq(null), eq(1L), eq(true), eq(TipoModeloAvaliacao.DESEMPENHO), ANYTHING }).will(returnValue(new ArrayList<Avaliacao>()));
    	manager.expects(once()).method("findAllSelect").with(new Constraint[] { eq(null), eq(null), eq(1L), eq(false), eq(TipoModeloAvaliacao.DESEMPENHO), ANYTHING }).will(returnValue(new ArrayList<Avaliacao>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(new ArrayList<CheckBox>()));
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
    	
    	assertEquals("input", action.imprimeResultado());
    }
	public void testImprimeResultadoException() throws Exception
    {
    	action.setAgruparPorAspectos(true);
    	Avaliacao avaliacaoExperiencia = AvaliacaoFactory.getEntity(1L);
    	action.setAvaliacaoExperiencia(avaliacaoExperiencia);

    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	action.setEmpresa(empresa);
    	
    	Collection<Pergunta> perguntas = PerguntaFactory.getCollection(1L);

    	manager.expects(once()).method("findById").with(eq(avaliacaoExperiencia.getId())).will(returnValue(avaliacaoExperiencia));
    	perguntaManager.expects(once()).method("findByQuestionarioAspectoPergunta").with(eq(avaliacaoExperiencia.getId()), ANYTHING, ANYTHING, eq(true)).will(returnValue(perguntas));
    	manager.expects(once()).method("montaResultado").will(throwException(new Exception()));
    	
    	ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharColaboradores(true);
    	
    	//prepareResultado
    	manager.expects(once()).method("findAllSelect").with(new Constraint[] { eq(null), eq(null), eq(1L), eq(true), eq(TipoModeloAvaliacao.DESEMPENHO), ANYTHING }).will(returnValue(new ArrayList<Avaliacao>()));
    	manager.expects(once()).method("findAllSelect").with(new Constraint[] { eq(null), eq(null), eq(1L), eq(false), eq(TipoModeloAvaliacao.DESEMPENHO), ANYTHING }).will(returnValue(new ArrayList<Avaliacao>()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(new ArrayList<CheckBox>()));
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));

    	assertEquals("input", action.imprimeResultado());
    }
	
	public void testGetSet() throws Exception
	{
		action.setAvaliacaoExperiencia(null);

		assertNotNull(action.getAvaliacaoExperiencia());
		assertTrue(action.getAvaliacaoExperiencia() instanceof Avaliacao);
		
		action.isPreview();
		action.getPerguntas();
		action.getTipoPergunta();
		action.getAreasCheck();
		action.getAreasCheckList();
		action.setAreasCheck(null);
		action.getAspectosCheck();
		action.setAspectosCheck(null);
		action.getPerguntasCheck();
		action.setPerguntasCheck(null);
		action.setPeriodoIni(null);
		action.setPeriodoFim(null);
		action.getAspectosCheckList();
		action.getPerguntasCheckList();
		action.isAgruparPorAspectos();
		action.isExibirCabecalho();
		action.getPeriodoIni();
		action.getPeriodoFim();
		action.getResultados();
		action.getDataSource();
		action.getParametros();
		action.getUrlVoltar();
		action.getAvaliacaoExperienciasAtivas();
		action.getAvaliacaoExperienciasInativas();
	}

	public void setEmpresaManager(Mock empresaManager) {
		this.empresaManager = empresaManager;
	}
}
