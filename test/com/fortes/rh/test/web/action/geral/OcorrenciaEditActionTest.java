package com.fortes.rh.test.web.action.geral;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.dicionario.TipoRelatorio;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.geral.OcorrenciaEditAction;

public class OcorrenciaEditActionTest extends MockObjectTestCase
{
	private OcorrenciaEditAction action;
	private Mock manager;
	private Mock parametrosDoSistemaManager;
	private Mock empresaManager;
	private Mock colaboradorOcorrenciaManager;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new OcorrenciaEditAction();

		manager = new Mock(OcorrenciaManager.class);
		action.setOcorrenciaManager((OcorrenciaManager) manager.proxy());
		
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
		
		empresaManager = new Mock(EmpresaManager.class);
		action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		
		colaboradorOcorrenciaManager = new Mock(ColaboradorOcorrenciaManager.class);
		action.setColaboradorOcorrenciaManager((ColaboradorOcorrenciaManager) colaboradorOcorrenciaManager.proxy());
		
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}
	
	public void testPrepareInsert() throws Exception
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		action.setOcorrencia(ocorrencia);
		
		assertEquals("success", action.prepareInsert());
		assertNotNull(action.getOcorrencia());
		assertTrue(action.isEmpresaIntegradaComAC());
	}
	
	public void testPrepareUpdate() throws Exception
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity(1L);
		action.setOcorrencia(ocorrencia);
		
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(new Ocorrencia()));
		assertEquals("success", action.prepareUpdate());
		assertNotNull(action.getOcorrencia());
	}
	
	public void testInsertOrUpdate() throws Exception
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		action.setOcorrencia(ocorrencia);
		
		manager.expects(once()).method("saveOrUpdate").with(ANYTHING, ANYTHING);
		
		assertEquals("success", action.insertOrUpdate());
	}
	
	public void testInsertOrUpdateException() throws Exception
	{
		assertEquals("input", action.insertOrUpdate());
		assertEquals("Cadastro não pôde ser realizado.", ((String) action.getActionErrors().toArray()[0]));
	}
	
	public void testInsertOrUpdateExceptionAC() throws Exception
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		action.setOcorrencia(ocorrencia);
		
		manager.expects(once()).method("saveOrUpdate").with(ANYTHING, ANYTHING).will(throwException(new InvocationTargetException(new IntegraACException())));;
		
		assertEquals("input", action.insertOrUpdate());
		assertEquals("Cadastro não pôde ser realizado no Fortes Pessoal.", ((String) action.getActionErrors().toArray()[0]));
	}
	
	public void testPrepareRelatorioOcorrencia() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
		
		assertEquals("success", action.prepareRelatorioOcorrencia());
	}
	
	public void testBuscaOcorrencia() throws Exception
	{
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		
		Collection<ColaboradorOcorrencia> colaboradoresOcorrencia = new ArrayList<ColaboradorOcorrencia>();
		colaboradoresOcorrencia.add(colaboradorOcorrencia);
		
		MockSecurityUtil.verifyRole = false;
		
		colaboradorOcorrenciaManager.expects(once()).method("filtrarOcorrencias").withAnyArguments().will(returnValue(colaboradoresOcorrencia));
		areaOrganizacionalManager.expects(once()).method("findAreasByUsuarioResponsavel").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<Long>()));
		
		action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
		action.setDataFim(DateUtil.criarAnoMesDia(2012, 01, 01)); 
		action.setExibirProvidencia(false);
		action.setEmpresa(action.getEmpresaSistema());
		
		assertEquals("semProvidenciaPDF", action.buscaOcorrencia());
	}

	public void testBuscaOcorrenciaComVerTodasAreas() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		
		Collection<ColaboradorOcorrencia> colaboradoresOcorrencia = new ArrayList<ColaboradorOcorrencia>();
		colaboradoresOcorrencia.add(colaboradorOcorrencia);
		
		MockSecurityUtil.roles = new String[]{"ROLE_VER_AREAS"};
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
		colaboradorOcorrenciaManager.expects(once()).method("filtrarOcorrencias").withAnyArguments().will(returnValue(colaboradoresOcorrencia));
		
		action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
		action.setDataFim(DateUtil.criarAnoMesDia(2012, 01, 01));
		action.setExibirProvidencia(false);
		
		assertEquals("semProvidenciaPDF", action.buscaOcorrencia());
	}
	
	public void testBuscaOcorrenciaXLS() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		
		Collection<ColaboradorOcorrencia> colaboradoresOcorrencia = new ArrayList<ColaboradorOcorrencia>();
		colaboradoresOcorrencia.add(colaboradorOcorrencia);
		
		MockSecurityUtil.roles = new String[]{"ROLE_VER_AREAS"};
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
		colaboradorOcorrenciaManager.expects(once()).method("filtrarOcorrencias").withAnyArguments().will(returnValue(colaboradoresOcorrencia));
		
		action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
		action.setDataFim(DateUtil.criarAnoMesDia(2012, 01, 01));
		action.setExibirProvidencia(false);
		action.setTipo(TipoRelatorio.XLS);
		
		assertEquals("semProvidenciaXLS", action.buscaOcorrencia());
	}
	
	public void testBuscaOcorrenciaColecaoVaziaException() throws Exception
	{
		MockSecurityUtil.roles = new String[]{"ROLE_VER_AREAS"};
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
		colaboradorOcorrenciaManager.expects(once()).method("filtrarOcorrencias").withAnyArguments().will(returnValue(null));
		
		action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
		action.setDataFim(DateUtil.criarAnoMesDia(2012, 01, 01));
		action.setExibirProvidencia(false);
		action.setEmpresa(action.getEmpresaSistema());
		
		assertEquals("input", action.buscaOcorrencia());
	}
	
	public void testBuscaOcorrenciaException() throws Exception
	{
		MockSecurityUtil.roles = new String[]{"ROLE_VER_AREAS"};
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");
		
		parametrosDoSistemaManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Empresa>()));
		colaboradorOcorrenciaManager.expects(once()).method("filtrarOcorrencias").withAnyArguments().will(returnValue(null));
		
		action.setDataIni(DateUtil.criarAnoMesDia(2010, 01, 01));
		action.setDataFim(DateUtil.criarAnoMesDia(2012, 01, 01));
		action.setExibirProvidencia(false);
		action.setEmpresa(action.getEmpresaSistema());
		
		assertEquals("input", action.buscaOcorrencia());
	}
}