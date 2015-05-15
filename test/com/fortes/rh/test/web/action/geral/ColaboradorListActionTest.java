package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.geral.ColaboradorListAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorListActionTest extends MockObjectTestCase
{
	private ColaboradorListAction action;
	private Mock colaboradorManager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock cargoManager;
	private Mock empresaManager;
	private Mock parametrosDoSistemaManager;

	protected void setUp () throws Exception
	{
		action = new ColaboradorListAction();

		colaboradorManager = new Mock(ColaboradorManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		cargoManager = new Mock(CargoManager.class);
		empresaManager = new Mock(EmpresaManager.class);
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		action.setCargoManager((CargoManager) cargoManager.proxy());
		action.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}

	protected void tearDown() throws Exception
    {
        colaboradorManager = null;
        action = null;
        Mockit.restoreAllOriginalDefinitions();
        super.tearDown();
    }

//	public void testList() throws Exception
//	{
//		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
//		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
//		
//		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
//		colaboradors.add(colaborador);
//		
//		action.setCpfBusca("360.5");
//		
//		areaOrganizacionalManager.expects(once()).method("findAllList").will(returnValue(new ArrayList<AreaOrganizacional>()));
//		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(new ArrayList<AreaOrganizacional>()));
//		
//		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
//		
//		cargoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Cargo>()));
//		
//		colaboradorManager.expects(once()).method("getCountComHistoricoFuturo").will(returnValue(new Integer(1)));
//		colaboradorManager.expects(once()).method("findListComHistoricoFuturo").will(returnValue(colaboradors));
//		
//		assertEquals("success", action.list());
//		assertEquals("3605", action.getCpfBusca());
//		assertEquals(1, action.getColaboradors().size());
////	}
//	public void testListVazio() throws Exception
//	{
//		areaOrganizacionalManager.expects(once()).method("findAllList").will(returnValue(new ArrayList<AreaOrganizacional>()));
//		areaOrganizacionalManager.expects(once()).method("montaFamilia").will(returnValue(new ArrayList<AreaOrganizacional>()));
//		estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Estabelecimento>()));
//		cargoManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Cargo>()));
//		colaboradorManager.expects(once()).method("getCountComHistoricoFuturo").will(returnValue(new Integer(0)));
//		colaboradorManager.expects(once()).method("findListComHistoricoFuturo").will(returnValue(new ArrayList<Colaborador>()));
//		
//		assertEquals("success", action.list());
//		assertTrue(action.getColaboradors().isEmpty());
//	}
	
	public void testDelete() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1000L);
		action.setColaborador(colaborador);
		colaboradorManager.expects(once()).method("removeComDependencias").with(eq(colaborador), ANYTHING).isVoid();
		
		assertEquals("success", action.delete());
	}
	
	public void testPrepareRelatorioAniversariantes()
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");

		assertEquals("success",action.prepareRelatorioAniversariantes());
	}
	public void testRelatorioAniversariantes()
	{
		empresaManager.expects(once()).method("selecionaEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Long[]{}));
		colaboradorManager.expects(once()).method("findAniversariantes").will(returnValue(new ArrayList<Colaborador>()));
		
		assertEquals("success",action.relatorioAniversariantes());
	}
	public void testRelatorioAniversariantesColecaoVazia()
	{
		empresaManager.expects(once()).method("selecionaEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Long[]{}));
		colaboradorManager.expects(once()).method("findAniversariantes").will(throwException(new ColecaoVaziaException("Não existem dados")));
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
		assertEquals("input",action.relatorioAniversariantes());
	}
	public void testRelatorioAniversariantesException()
	{
		empresaManager.expects(once()).method("selecionaEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Long[]{}));
		colaboradorManager.expects(once()).method("findAniversariantes").will(throwException(new Exception()));
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
		assertEquals("input",action.relatorioAniversariantes());
	}
	
	public void testPrepareRelatorioAdmitidos()
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
		assertEquals("success",action.prepareRelatorioAdmitidos());
	}
	
	public void testRelatorioAdmitidos()
	{
		action.setExibirSomenteAtivos(true);
		empresaManager.expects(once()).method("selecionaEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Long[]{}));
		colaboradorManager.expects(once()).method("findAdmitidos").will(returnValue(new ArrayList<Colaborador>()));
		assertEquals("success",action.relatorioAdmitidos());
	}
	public void testRelatorioAdmitidosColecaoVaziaException()
	{
		colaboradorManager.expects(once()).method("findAdmitidos").will(throwException(new ColecaoVaziaException("Não existem dados para o filtro informado.")));
		empresaManager.expects(once()).method("selecionaEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Long[]{}));
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
		assertEquals("input",action.relatorioAdmitidos());
	}
	public void testRelatorioAdmitidosException()
	{
		colaboradorManager.expects(once()).method("findAdmitidos").will(throwException(new Exception()));
		empresaManager.expects(once()).method("selecionaEmpresa").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new Long[]{}));

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		parametrosDoSistemaManager.expects(once()).method("findById").will(returnValue(parametrosDoSistema));
		empresaManager.expects(once()).method("findEmpresasPermitidas");
		
		assertEquals("input",action.relatorioAdmitidos());
		
	}
	
	public void testFormPrint() throws Exception
	{
		assertEquals("success",action.formPrint());
	}

	public void setEmpresaManager(Mock empresaManager)
	{
		this.empresaManager = empresaManager;
	}
}
