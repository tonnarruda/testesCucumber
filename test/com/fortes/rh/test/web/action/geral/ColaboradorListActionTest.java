package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

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
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
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
		colaboradorManager.expects(once()).method("removeComDependencias").with(eq(colaborador.getId())).isVoid();
		
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
	
	public void testReciboPagamentoComplementar(){
		String retorno = "JVBERi0xLjINCjEgMCBvYmogPDwNCi9DcmVhdGlvbkRhdGUoRDoyMDE2MDEyNzA5MzQyOSkNCi9DcmVhdG9yKEZvcnRlc1JlcG9ydCB2My4xMDF4IFwyNTEgQ29weXJpZ2h0IKkgMTk5OS0yMDE1IEZvcnRlcyBJbmZvcm3hdGljYSkNCj4";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		colaboradorManager.expects(once()).method("getReciboDePagamentoComplementar").with(ANYTHING, ANYTHING).will(returnValue(retorno));
		
		Exception expException = null;
		try {
			assertEquals("success",action.reciboPagamentoComplementar());
		} catch (Exception e) {
			expException = e;
		}
		
		assertNull(expException);
	}
	
	public void testReciboPagamentoComplementarException() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		colaboradorManager.expects(once()).method("getReciboDePagamentoComplementar").with(ANYTHING, ANYTHING).will(throwException(new Exception()));
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		assertEquals("input",action.reciboPagamentoComplementar());
	}
	
	public void testReciboPagamentoAdiantamentoDeFolha(){
		String retorno = "JVBERi0xLjINCjEgMCBvYmogPDwNCi9DcmVhdGlvbkRhdGUoRDoyMDE2MDEyNzA5MzQyOSkNCi9DcmVhdG9yKEZvcnRlc1JlcG9ydCB2My4xMDF4IFwyNTEgQ29weXJpZ2h0IKkgMTk5OS0yMDE1IEZvcnRlcyBJbmZvcm3hdGljYSkNCj4";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		colaboradorManager.expects(once()).method("getReciboPagamentoAdiantamentoDeFolha").with(ANYTHING, ANYTHING).will(returnValue(retorno));
		
		Exception expException = null;
		try {
			assertEquals("success",action.reciboPagamentoAdiantamentoDeFolha());
		} catch (Exception e) {
			expException = e;
		}
		
		assertNull(expException);
	}
	
	public void testReciboPagamentoAdiantamentoDeFolhaException() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		colaboradorManager.expects(once()).method("getReciboPagamentoAdiantamentoDeFolha").with(ANYTHING, ANYTHING).will(throwException(new Exception()));
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		assertEquals("input",action.reciboPagamentoAdiantamentoDeFolha());
	}
	
	public void testPrepareReciboPagamentoAdiantamentoDeFolha()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		assertEquals("success",action.prepareReciboPagamentoAdiantamentoDeFolha());
	}
	
	public void testPrepareReciboPagamentoAdiantamentoDeFolhaEmpresaDesintegrada()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(false);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		action.prepareReciboPagamentoAdiantamentoDeFolha();
		
		assertEquals("Esta empresa não está integrada com Fortes Pessoal.", action.getActionWarnings().iterator().next());
	}
	
	public void testPrepareReciboPagamentoAdiantamentoDeFolhaColaboradorLogadoEmEmpresaDiferenteDaQueFoiContratado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa2);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		action.prepareReciboPagamentoAdiantamentoDeFolha();
		
		assertEquals(action.getActionWarnings().iterator().next(),"Só é possível solicitar seu recibo de adiantamento de folha pela empresa a qual você foi contratado(a). Acesse a empresa <strong>" + colaborador.getEmpresaNome() + "</strong> para solicitar seu recibo.");
	}
	
	public void testPrepareReciboPagamentoAdiantamentoDeFolhaColaboradorSemUsuario()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa2);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(null));
		action.prepareReciboPagamentoAdiantamentoDeFolha();
		
		assertEquals(action.getActionWarnings().iterator().next(),"Sua conta de usuário não está vinculada à nenhum colaborador");
	}
	
	public void testPrepareReciboPagamentoComplementar()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		
		assertEquals("success", action.prepareReciboPagamentoComplementar());
	}
	
	public void testPrepareReciboPagamentoComplementarLogadoEmEmpresaDiferenteDaQueFoiContratado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa2);
		
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
		action.prepareReciboPagamentoComplementar();
		
		assertEquals(action.getActionWarnings().iterator().next(),"Só é possível solicitar seu recibo de complemento da folha com encargos pela empresa a qual você foi contratado(a). Acesse a empresa <strong>" + colaborador.getEmpresaNome() + "</strong> para solicitar seu recibo.");
	}
	
	public void setEmpresaManager(Mock empresaManager)
	{
		this.empresaManager = empresaManager;
	}
}
