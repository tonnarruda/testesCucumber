package com.fortes.rh.test.web.action.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.avaliacao.PeriodoExperienciaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.FaixaPerformanceAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoDesempenhoFactory;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.avaliacao.PeriodoExperienciaEditAction;


public class PeriodoExperienciaEditActionTest extends MockObjectTestCase
{
	private PeriodoExperienciaEditAction action;
	private Mock manager;
	private Mock colaboradorManager;
	private Mock avaliacaoDesempenhoManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock avaliacaoManager;
	private Mock parametrosDoSistemaManager;
	private Mock empresaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(PeriodoExperienciaManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);
		avaliacaoDesempenhoManager = new Mock(AvaliacaoDesempenhoManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		avaliacaoManager = new Mock(AvaliacaoManager.class);
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		empresaManager = new Mock(EmpresaManager.class);

		action = new PeriodoExperienciaEditAction();
		
		action.setPeriodoExperienciaManager((PeriodoExperienciaManager) manager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setAvaliacaoDesempenhoManager((AvaliacaoDesempenhoManager) avaliacaoDesempenhoManager.proxy());
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		action.setAvaliacaoManager((AvaliacaoManager) avaliacaoManager.proxy());
		action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
		action.setPeriodoExperiencia(new PeriodoExperiencia());
		action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		manager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<PeriodoExperiencia>()));
		assertEquals(action.list(), "success");
		assertNotNull(action.getPeriodoExperiencias());
	}

	public void testDelete() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		action.setPeriodoExperiencia(periodoExperiencia);

		manager.expects(once()).method("remove");
		assertEquals(action.delete(), "success");
	}
	
	public void testInsert() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		periodoExperiencia.setDescricao("Noventa Dias");
		periodoExperiencia.setDias(90);
		action.setPeriodoExperiencia(periodoExperiencia);

		manager.expects(once()).method("save").with(eq(periodoExperiencia)).will(returnValue(periodoExperiencia));
		
		assertEquals("90 dias (Noventa Dias)", periodoExperiencia.getDiasDescricao());
		
		periodoExperiencia.setDescricao("");
		action.setPeriodoExperiencia(periodoExperiencia);
		
		assertEquals("90 dias", periodoExperiencia.getDiasDescricao());
		assertEquals("success", action.insert());
	}

	public void testUpdate() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		action.setPeriodoExperiencia(periodoExperiencia);

		manager.expects(once()).method("update").with(eq(periodoExperiencia)).isVoid();

		assertEquals("success", action.update());
	}
	
	public void testImpPerformAvDesempenho() throws Exception
	{
		Avaliacao avaliacao = AvaliacaoFactory.getEntity();
		avaliacao.setId(1L);
		avaliacao.setTitulo("titulo");
		action.setAvaliacao(avaliacao);
		
		AvaliacaoDesempenho avaliacaoDesempenho = AvaliacaoDesempenhoFactory.getEntity();
		avaliacaoDesempenho.setId(2L);
		avaliacaoDesempenho.setAvaliacao(avaliacao);
		
		Collection<AvaliacaoDesempenho> avaliacaoDesempenhoIds = new ArrayList<AvaliacaoDesempenho>();
		avaliacaoDesempenhoIds.add(avaliacaoDesempenho);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setId(10L);
		empresa.setNome("Fortes");
		action.setEmpresa(empresa);
		
		Date periodo = new Date();
		action.setPeriodoIni(periodo);
		action.setPeriodoFim(periodo);
		
		String[] percentualInicio = new String[]{"10"};
		action.setPercentualInicial(percentualInicio);
		String[] percentualFinal = new String[]{"30"};
		action.setPercentualFinal(percentualFinal);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);
		
		FaixaPerformanceAvaliacaoDesempenho faixaPerformanceAvaliacaoDesempenho = new FaixaPerformanceAvaliacaoDesempenho(10.0, 30.0, 2);
		
		Collection<FaixaPerformanceAvaliacaoDesempenho> faixaPerformanceAvaliacaoDesempenhos = new ArrayList<FaixaPerformanceAvaliacaoDesempenho>();
		faixaPerformanceAvaliacaoDesempenhos.add(faixaPerformanceAvaliacaoDesempenho);
		
		avaliacaoDesempenhoManager.expects(once()).method("findIdsAvaliacaoDesempenho").will(returnValue(avaliacaoDesempenhoIds));
		colaboradorManager.expects(once()).method("findColabPeriodoExperiencia").will(returnValue(colaboradors));
		manager.expects(once()).method("agrupaFaixaAvaliacao").will(returnValue(faixaPerformanceAvaliacaoDesempenhos));
		
		assertEquals("success",action.imprimeRelatorioPerformanceAvaliacaoDesempenho());
	}
	
    public void testImpRankPerformAvDesempenho() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setNome("Fortes");
    	action.setEmpresaSistema(empresa);
    	
    	Date periodo = new Date();
    	action.setPeriodoIni(periodo);
    	action.setPeriodoFim(periodo);
    	
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	action.setAvaliacao(avaliacao);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	
    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	colaboradors.add(colaborador);
    	
    	colaboradorManager.expects(once()).method("findColabPeriodoExperienciaAgrupadoPorModelo").will(returnValue(colaboradors));
    	
    	assertEquals("success",action.impRankPerformAvDesempenho());
    }
    
    public void testImpRankPerformAvDesempenhoException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setNome("Fortes");
    	action.setEmpresaSistema(empresa);
    	
    	Date periodo = new Date();
    	action.setPeriodoIni(periodo);
    	action.setPeriodoFim(periodo);
    	
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	action.setAvaliacao(avaliacao);
    	
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	colaboradors.add(colaborador);
    	
    	ParametrosDoSistema params = ParametrosDoSistemaFactory.getEntity(1L);
    	params.setCompartilharColaboradores(false);
    	
    	colaboradorManager.expects(once()).method("findColabPeriodoExperienciaAgrupadoPorModelo").will(throwException(new Exception()));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao");
    	estabelecimentoManager.expects(once()).method("populaCheckBox");
    	avaliacaoDesempenhoManager.expects(once()).method("populaCheckBox").will(returnValue(new ArrayList<AvaliacaoDesempenho>()));
    	avaliacaoManager.expects(once()).method("findAllSelectComAvaliacaoDesempenho").will(returnValue(new ArrayList<Avaliacao>()));
    	colaboradorManager.expects(once()).method("findByAvaliacoes").will(returnValue(new ArrayList<Colaborador>()));
    	parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(params));
    	empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
    	
    	assertEquals("input",action.impRankPerformAvDesempenho());
    }
	
	public void testPrepareInsert() throws Exception
	{
		assertEquals("success", action.prepareInsert());
	}
	
	public void testPrepareUpdate() throws Exception
	{
		PeriodoExperiencia periodoExperiencia = PeriodoExperienciaFactory.getEntity(1L);
		action.setPeriodoExperiencia(periodoExperiencia);
		manager.expects(once()).method("findById").will(returnValue(periodoExperiencia));
		assertEquals("success", action.prepareUpdate());
	}

	public void testGetSet() throws Exception
	{
		action.setPeriodoExperiencia(null);

		assertNotNull(action.getPeriodoExperiencia());
		assertTrue(action.getPeriodoExperiencia() instanceof PeriodoExperiencia);
	}
}
