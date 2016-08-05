package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import ar.com.fdvs.dj.core.DynamicJasperHelper;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.AreaColaboradorException;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.AutoCompleteVO;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorJsonVO;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoRelatorioDinamico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.ws.TPeriodoGozo;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoRelatorioDinamicoFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockDynamicJasperHelpe;
import com.fortes.rh.test.util.mockObjects.MockJasperExportManager;
import com.fortes.rh.test.util.mockObjects.MockJasperFillManager;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.geral.ColaboradorListAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorListActionTest
{
	private ColaboradorListAction action;
	private ColaboradorManager colaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private CargoManager cargoManager;
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private ConfiguracaoRelatorioDinamicoManager configuracaoRelatorioDinamicoManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private String retorno;

	@Before
	public void setUp() throws Exception
	{
		action = new ColaboradorListAction();

		colaboradorManager = mock(ColaboradorManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		cargoManager = mock(CargoManager.class);
		empresaManager = mock(EmpresaManager.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		configuracaoRelatorioDinamicoManager = mock(ConfiguracaoRelatorioDinamicoManager.class);
		configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
		
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		action.setColaboradorManager(colaboradorManager);
		action.setEstabelecimentoManager(estabelecimentoManager);
		action.setCargoManager(cargoManager);
		action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
		action.setEmpresaManager(empresaManager);
		action.setConfiguracaoRelatorioDinamicoManager(configuracaoRelatorioDinamicoManager);
		action.setConfiguracaoCampoExtraManager(configuracaoCampoExtraManager);
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(DynamicJasperHelper.class, MockDynamicJasperHelpe.class);
		Mockit.redefineMethods(JasperFillManager.class, MockJasperFillManager.class);
		Mockit.redefineMethods(JasperExportManager.class, MockJasperExportManager.class);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		retorno = "JVBERi0xLjINCjEgMCBvYmogPDwNCi9DcmVhdGlvbkRhdGUoRDoyMDE2MDEyNzA5MzQyOSkNCi9DcmVhdG9yKEZvcnRlc1JlcG9ydCB2My4xMDF4IFwyNTEgQ29weXJpZ2h0IKkgMTk5OS0yMDE1IEZvcnRlcyBJbmZvcm3hdGljYSkNCj4";
	}

	@After
	public void tearDown() throws Exception
    {
        colaboradorManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        Mockit.restoreAllOriginalDefinitions();
    }

	@Test
	public void testList() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(5L);
		colaborador.getEndereco().setUf(EstadoFactory.getEntity(1L));
		
		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador);
		
		action.setCpfBusca("360.5");
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		when(colaboradorManager.findByUsuario(action.getUsuarioLogado().getId())).thenReturn(colaborador.getId());
		when(areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<AreaOrganizacional>());
		when(areaOrganizacionalManager.montaFamilia(new ArrayList<AreaOrganizacional>())).thenReturn(new ArrayList<AreaOrganizacional>());
		when(estabelecimentoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Estabelecimento>());
		when(cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, action.getEmpresaSistema().getId())).thenReturn(new ArrayList<Cargo>());
		when(areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(action.getUsuarioLogado(), action.getEmpresaSistema().getId())).thenReturn(new Long[]{});
		when(colaboradorManager.getCountComHistoricoFuturoSQL(parametros, action.getUsuarioLogado().getId())).thenReturn(5);
		when(colaboradorManager.findComHistoricoFuturoSQL(0, 15, parametros, action.getUsuarioLogado().getId())).thenReturn(colaboradors);
		
		assertEquals("success", action.list());
		assertEquals("3605", action.getCpfBusca());
	}
	
	@Test
	public void testColaboradoresPorArea()
	{
		ColaboradorJsonVO colaboradorJsonVO = new ColaboradorJsonVO();
		colaboradorJsonVO.setId("1");
		colaboradorJsonVO.setAddress("address");
		colaboradorJsonVO.setBurgh("burgh");
		Collection<ColaboradorJsonVO> colaboradores = Arrays.asList(colaboradorJsonVO);
		
		String json = StringUtil.toJSON(colaboradores, new String[]{"id"});
		
		when(colaboradorManager.getColaboradoresJsonVO(new Long[]{ 59L })).thenReturn(colaboradores);
		
		assertEquals(Action.SUCCESS, action.colaboradoresPorArea());
		assertEquals(json, action.getJson());
	}

	@Test
	public void testColaboradoresPorAreaException()
	{
		when(colaboradorManager.getColaboradoresJsonVO(new Long[]{ 59L })).thenThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("","")));
		
		assertEquals(Action.SUCCESS, action.colaboradoresPorArea());
		assertEquals("error", action.getJson());
		
	}
	
	@Test
	public void testDelete() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1000L);
		action.setColaborador(colaborador);
		
		assertEquals("success", action.delete());
	}
	
	@Test
	public void testPrepareRelatorioAniversariantes()
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);

		assertEquals("success",action.prepareRelatorioAniversariantes());
	}
	
	@Test
	public void testRelatorioAniversariantes() throws Exception
	{
		when(empresaManager.selecionaEmpresa(null, null, null)).thenReturn(new Long[]{});
		when(colaboradorManager.findAniversariantes(null, 0, null, null)).thenReturn(new ArrayList<Colaborador>());
		
		assertEquals("success",action.relatorioAniversariantes());
	}
	
	@Test
	public void testRelatorioAniversariantesColecaoVazia() throws Exception
	{
		when(empresaManager.selecionaEmpresa(null, null, null)).thenReturn(new Long[]{});
		when(colaboradorManager.findAniversariantes(null, 0, new Long[]{}, new Long[]{})).thenThrow(new ColecaoVaziaException("Não existem dados"));
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		
		assertEquals("input",action.relatorioAniversariantes());
	}
	
	@Test
	public void testRelatorioAniversariantesException() throws Exception
	{
		when(empresaManager.selecionaEmpresa(null, null, null)).thenReturn(new Long[]{});
		when(colaboradorManager.findAniversariantes(null, 0, new Long[]{}, new Long[]{})).thenThrow(new Exception());
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		
		assertEquals("input",action.relatorioAniversariantes());
	}
	
	@Test
	public void testPrepareRelatorioAdmitidos()
	{
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		
		assertEquals("success",action.prepareRelatorioAdmitidos());
	}
	
	@Test
	public void testRelatorioAdmitidos() throws ColecaoVaziaException, Exception
	{
		action.setExibirSomenteAtivos(true);
		when(empresaManager.selecionaEmpresa(null, null, null)).thenReturn(new Long[]{});
		when(colaboradorManager.findAdmitidos(null, null, null, null, null, null, false)).thenReturn(new ArrayList<Colaborador>());
				
		assertEquals("success",action.relatorioAdmitidos());
	}
	
	@Test
	public void testRelatorioAdmitidosColecaoVaziaException() throws ColecaoVaziaException, Exception
	{
		when(empresaManager.selecionaEmpresa(null, null, null)).thenReturn(new Long[]{});
		when(colaboradorManager.findAdmitidos(null, null, null, null, new Long[]{}, new Long[]{}, false)).thenThrow(new ColecaoVaziaException("Não existem dados para o filtro informado."));
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		
		assertEquals("input",action.relatorioAdmitidos());
	}
	
	@Test
	public void testRelatorioAdmitidosException() throws ColecaoVaziaException, Exception
	{
		when(empresaManager.selecionaEmpresa(null, null, null)).thenReturn(new Long[]{});
		when(colaboradorManager.findAdmitidos(null, null, null, null, new Long[]{}, new Long[]{}, false)).thenThrow(new Exception());

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		
		assertEquals("input",action.relatorioAdmitidos());
	}
	
	@Test
	public void testFormPrint() throws Exception
	{
		assertEquals("success",action.formPrint());
	}
	
	@Test
	public void testReciboPagamentoComplementar() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getReciboDePagamentoComplementar(colaborador, action.getMesAnoDate())).thenReturn(retorno);
		
		Exception expException = null;
		try {
			assertEquals("success",action.reciboPagamentoComplementar());
		} catch (Exception e) {
			expException = e;
		}
		
		assertNull(expException);
	}
	
	@Test
	public void testReciboPagamentoComplementarException() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getReciboDePagamentoComplementar(colaborador, action.getMesAnoDate())).thenThrow(new Exception());
		
		assertEquals("input",action.reciboPagamentoComplementar());
	}
	
	@Test
	public void testReciboPagamentoAdiantamentoDeFolha() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getReciboPagamentoAdiantamentoDeFolha(colaborador, action.getMesAnoDate())).thenReturn(retorno);
		
		Exception expException = null;
		try {
			assertEquals("success",action.reciboPagamentoAdiantamentoDeFolha());
		} catch (Exception e) {
			expException = e;
		}
		
		assertNull(expException);
	}
	
	@Test
	public void testReciboPagamentoAdiantamentoDeFolhaException() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getReciboPagamentoAdiantamentoDeFolha(colaborador, action.getMesAnoDate())).thenThrow(new Exception());
		
		assertEquals("input",action.reciboPagamentoAdiantamentoDeFolha());
	}
	
	@Test
	public void testPrepareReciboPagamentoAdiantamentoDeFolha() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		
		assertEquals("success",action.prepareReciboPagamentoAdiantamentoDeFolha());
	}
	
	@Test
	public void testPrepareReciboPagamentoAdiantamentoDeFolhaEmpresaDesintegrada() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(false);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		action.prepareReciboPagamentoAdiantamentoDeFolha();
		
		assertEquals("Esta empresa não está integrada com o Fortes Pessoal.", action.getActionWarnings().iterator().next());
	}
	
	@Test
	public void testPrepareReciboPagamentoAdiantamentoDeFolhaColaboradorLogadoEmEmpresaDiferenteDaQueFoiContratado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa2);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		action.prepareReciboPagamentoAdiantamentoDeFolha();
		
		assertEquals(action.getActionWarnings().iterator().next(),"Só é possível solicitar seu recibo de adiantamento de folha pela empresa a qual você foi contratado(a). Acesse a empresa <strong>" + colaborador.getEmpresaNome() + "</strong> para solicitar seu recibo.");
	}
	
	@Test
	public void testPrepareReciboPagamentoAdiantamentoDeFolhaColaboradorSemUsuario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa2);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(null);
		action.prepareReciboPagamentoAdiantamentoDeFolha();
		
		assertEquals(action.getActionWarnings().iterator().next(),"Sua conta de usuário não está vinculada à um colaborador.");
	}
	
	@Test
	public void testPrepareReciboPagamentoComplementar() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		
		assertEquals("success", action.prepareReciboPagamentoComplementar());
	}
	
	@Test
	public void testPrepareReciboPagamentoComplementarLogadoEmEmpresaDiferenteDaQueFoiContratado() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa2);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		action.prepareReciboPagamentoComplementar();
		
		assertEquals(action.getActionWarnings().iterator().next(),"Só é possível solicitar seu recibo de complemento da folha com encargos pela empresa a qual você foi contratado(a). Acesse a empresa <strong>" + colaborador.getEmpresaNome() + "</strong> para solicitar seu recibo.");
	}
	
	@Test
	public void testPrepareReciboFerias() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getDatasPeriodoDeGozoPorEmpregado(colaborador)).thenReturn(new String[]{});
		
		assertEquals(Action.SUCCESS,action.prepareReciboDeFerias());
	}

	@Test
	public void testPrepareReciboFeriasException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenThrow(new Exception());
		
		assertEquals(Action.SUCCESS,action.prepareReciboDeFerias());
		assertEquals("Houve um erro inesperado: null", action.getActionErrors().iterator().next());
	}
	
	@Test
	public void testPrepareReciboFeriasEmpresaDesintegrada() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(false);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		action.prepareReciboDeFerias();
		
		assertEquals("Esta empresa não está integrada com o Fortes Pessoal.", action.getActionWarnings().iterator().next());
	}
	
	@Test
	public void testPrepareReciboFeriasContaDoUsuarioDesvinculada() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaboradorDoUsuario = ColaboradorFactory.getEntity(1L);
		
		when(colaboradorManager.findColaboradorById(colaboradorDoUsuario.getId())).thenReturn(null);
		action.prepareReciboDeFerias();
		
		assertEquals("Sua conta de usuário não está vinculada à um colaborador.", action.getActionWarnings().iterator().next());
	}
	
	@Test
	public void testPrepareReciboFeriasNaoEhEmpresaDaContratacao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa(2L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa2);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		action.prepareReciboDeFerias();
		
		assertEquals("Só é possível solicitar seu recibo de férias pela empresa a qual você foi contratado(a). Acesse a empresa <strong>" + colaborador.getEmpresaNome() + "</strong> para solicitar seu recibo.", action.getActionWarnings().iterator().next());
	}
	
	@Test
	public void testReciboDeFerias() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String arquivo = "JVBERi0xLjINCjEgMCBvYmogPDwNCi9DcmVhdGlvbkRhdGUoRDoyMDE2MDEyNzA5MzQyOSkNCi9DcmVhdG9yKEZvcnRlc1JlcG9ydCB2My4xMDF4IFwyNTEgQ29weXJpZ2h0IKkgMTk5OS0yMDE1IEZvcnRlcyBJbmZvcm3hdGljYSkNCj4";
		String dataInicioDoGozo = "01/01/2016";
		String dataFimDoGozo = "30/01/2016";
		action.setDataInicioGozo(dataInicioDoGozo);
		action.setDataFimGozo(dataFimDoGozo);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getAvisoReciboDeFerias(colaborador, dataInicioDoGozo, dataFimDoGozo)).thenReturn(arquivo);
		
		assertEquals("success",action.reciboDeFerias());
	}

	@Test
	public void testReciboDeFeriasException() throws Exception 
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String dataInicioDoGozo = "01/01/2016";
		String dataFimDoGozo = "30/01/2016";
		action.setDataInicioGozo(dataInicioDoGozo);
		action.setDataFimGozo(dataFimDoGozo);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getAvisoReciboDeFerias(colaborador, dataInicioDoGozo, dataFimDoGozo)).thenThrow(new Exception());
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getDatasPeriodoDeGozoPorEmpregado(colaborador)).thenReturn(new String[]{});
		
		assertEquals("input",action.reciboDeFerias());
	}
	
	@Test
	public void testPrepareReciboPagamento() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		
		assertEquals(Action.SUCCESS,action.prepareReciboPagamento());
	}
	
	@Test
	public void testPrepareReciboPagamentoSemCodigoAC() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		action.prepareReciboPagamento();
		
		assertEquals("Este colaborador não está integrado com o Fortes Pessoal ou não possui código Fortes Pessoal.", action.getActionWarnings().iterator().next());
	}
	
	@Test
	public void testFind() throws Exception{
		String descricao = "descroção";
		action.setDescricao(descricao);
		
		AutoCompleteVO autoCompleteVO = new AutoCompleteVO();
		Collection<AutoCompleteVO> autoCompleteVOs = new ArrayList<AutoCompleteVO>();
		autoCompleteVOs.add(autoCompleteVO);
		
		when(colaboradorManager.getAutoComplete(descricao, 1L)).thenReturn(autoCompleteVOs);
		
		assertEquals(Action.SUCCESS, action.find());
		assertEquals("[{\"id\":\"\",\"value\":\"\"}]", action.getJson());
	}
	
	@Test
	public void testPrepareRelatorioDinamico(){
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);
		
		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
    	parametrosDoSistema.setCompartilharCandidatos(true);
    	
    	ConfiguracaoCampoExtra configuracaoCampoExtra = ConfiguracaoCampoExtraFactory.getEntity("campo extra");
    	configuracaoCampoExtra.setTipo("");
    	Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
    	configuracaoCampoExtras.add(configuracaoCampoExtra);
    	
    	action.getEmpresaSistema().setCampoExtraColaborador(true);
    	
		when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(areas);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(configuracaoRelatorioDinamicoManager.findByUsuario(1L)).thenReturn(ConfiguracaoRelatorioDinamicoFactory.getEntity(1L));
		when(empresaManager.checkEmpresaIntegradaAc()).thenReturn(true);
		when(configuracaoCampoExtraManager.find(new String[]{"ativoColaborador", "empresa.id"}, new Object[]{true, action.getEmpresaSistema().getId()}, new String[]{"ordem"})).thenReturn(configuracaoCampoExtras);
		
		assertEquals(Action.SUCCESS, action.prepareRelatorioDinamico());
	}
	
	@Test
	public void testPrepareRelatorioDinamicoSemPermicao(){
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		when(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(action.getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), AreaOrganizacional.TODAS, null)).thenReturn(areas);
		
		assertEquals("semPermissaoDeVerAreaOrganizacional", action.prepareRelatorioDinamico());
	}
	
	@Test
	public void testRelatorioDinamicoSemDados() throws Exception{
		action.setEmpresa(EmpresaFactory.getEmpresa(1L));
		when(areaOrganizacionalManager.filtraPermitidas(new String[]{}, action.getEmpresa().getId())).thenReturn(new String[]{});
		
		assertEquals(Action.INPUT, action.relatorioDinamico());
	}
	
	@Test
	public void testRelatorioDinamico() throws Exception{
		action.setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		CamposExtras camposExtras = new CamposExtras();
		camposExtras.setId(1l);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		Collection<String> colunasMarcadas = new ArrayList<String>();
		colunasMarcadas.add("nome");
		colunasMarcadas.add("co.nomeComercial");
		action.setColunasMarcadas(colunasMarcadas);
		
		when(areaOrganizacionalManager.filtraPermitidas(new String[]{}, action.getEmpresa().getId())).thenReturn(new String[]{});
		when(colaboradorManager.findAreaOrganizacionalByAreas(false, new ArrayList<Long>(), new ArrayList<Long>(), new ArrayList<Long>(), camposExtras, null, null, null, null, null, null, null, null, null, new Long[]{1L})).thenReturn(colaboradores);
		
		assertEquals(Action.SUCCESS, action.relatorioDinamico());
	}
	
	@Test
	public void testRelatorioDinamicoAgruparPorTempoServico() throws Exception{
		action.setEmpresa(EmpresaFactory.getEmpresa(1L));
		
		CamposExtras camposExtras = new CamposExtras();
		camposExtras.setId(1l);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		Collection<String> colunasMarcadas = new ArrayList<String>();
		colunasMarcadas.add("nome");
		colunasMarcadas.add("co.nomeComercial");
		action.setColunasMarcadas(colunasMarcadas);
		action.setAgruparPorTempoServico(true);
		
		when(areaOrganizacionalManager.filtraPermitidas(new String[]{}, action.getEmpresa().getId())).thenReturn(new String[]{});
		when(colaboradorManager.findAreaOrganizacionalByAreas(false, new ArrayList<Long>(), new ArrayList<Long>(), new ArrayList<Long>(), camposExtras, " co.dataAdmissao desc, null", null, null, null, null, null, null, null, null, new Long[]{1L})).thenReturn(colaboradores);
		when(colaboradorManager.montaTempoServico(colaboradores, null, null, "")).thenReturn(colaboradores);
		
		assertEquals(Action.SUCCESS, action.relatorioDinamico());
	}

	@Test
	public void testPrepareRelatorioFerias() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);

		Collection<AreaOrganizacional> areasList = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		action.setEmpresaSistema(empresa);
		action.setUsuarioLogado(usuario);
		
		when(colaboradorManager.defineAreasPermitidasParaUsuario(empresa.getId(), usuario.getId(), SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}))).thenReturn(areasList);

		assertEquals(Action.SUCCESS, action.prepareRelatorioFerias());
	}

	@Test
	public void testPrepareRelatorioFeriasAreaColaboradorException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
		String mensagem = "Erro I";
		
		action.setEmpresaSistema(empresa);
		action.setUsuarioLogado(usuario);
		
		when(colaboradorManager.defineAreasPermitidasParaUsuario(empresa.getId(), usuario.getId(), SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}))).thenThrow(new AreaColaboradorException(mensagem));
		
		String retorno = action.prepareRelatorioFerias();
		
		assertEquals(Action.INPUT, retorno);
		assertEquals(action.getActionWarnings().iterator().next(), mensagem);
	}
	
	@Test
	public void testPrepareRelatorioFeriasException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Usuario usuario = UsuarioFactory.getEntity(1L);
		
		action.setEmpresaSistema(empresa);
		action.setUsuarioLogado(usuario);
		
		when(colaboradorManager.defineAreasPermitidasParaUsuario(empresa.getId(), usuario.getId(), SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}))).thenThrow(new Exception());
		
		String retorno = action.prepareRelatorioFerias();
		
		assertEquals(Action.INPUT, retorno);
		assertEquals(action.getActionErrors().iterator().next(), "Não foi possível visualizar esta tela.");
	}
	
	@Test
	public void testImprimeRelatorioFerias() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		action.setEmpresaSistema(empresa);
		
		Long[] colaboradoresCheck = new Long[]{1L};
		String[] colaboradoresCodigosACs = new String[]{"000001"};
		Collection<TPeriodoGozo> periodosGozo = Arrays.asList(new TPeriodoGozo());
		
		when(colaboradorManager.findCodigosACByIds(colaboradoresCheck)).thenReturn(colaboradoresCodigosACs);
		when(colaboradorManager.getFerias(empresa, colaboradoresCodigosACs, null, null)).thenReturn(periodosGozo);

		assertEquals(Action.SUCCESS, action.imprimeRelatorioFerias());
	}
	
	@Test
	public void testImprimeRelatorioFeriasColecaoVaziaException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);

		Long[] colaboradoresCheck = new Long[]{1L};
		action.setColaboradoresCheck(colaboradoresCheck);
		
		String[] colaboradoresCodigosACs = new String[]{"000001"};
		
		when(colaboradorManager.findCodigosACByIds(colaboradoresCheck)).thenReturn(colaboradoresCodigosACs);
		when(colaboradorManager.getFerias(empresa, colaboradoresCodigosACs, null, null)).thenThrow(new ColecaoVaziaException());
		
		String retorno = action.imprimeRelatorioFerias();

		assertEquals(Action.INPUT, retorno);
		assertEquals(action.getActionMessages().iterator().next(), "Não existem dados para o filtro informado.");

	}

	@Test
	public void testImprimeRelatorioException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		
		Long[] colaboradoresCheck = new Long[]{1L};
		action.setColaboradoresCheck(colaboradoresCheck);
		
		String[] colaboradoresCodigosACs = new String[]{"000001"};
		
		when(colaboradorManager.findCodigosACByIds(colaboradoresCheck)).thenReturn(colaboradoresCodigosACs);
		when(colaboradorManager.getFerias(empresa, colaboradoresCodigosACs, null, null)).thenThrow(new Exception());
		
		String retorno = action.imprimeRelatorioFerias();
		
		assertEquals(Action.INPUT, retorno);
		assertEquals(action.getActionErrors().iterator().next(), "Não foi possível gerar o relatório.");
		
	}
	
	@Test
	public void testRelatorioDinamicoXLS() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		Long[] colaboradoresCheck = new Long[]{1L};
		action.setColaboradoresCheck(colaboradoresCheck);
		
		String[] areasPermitidasId = new String[]{"000001"};
		
		CamposExtras camposExtras = new CamposExtras();
		camposExtras.setId(1l);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		Collection<String> colunasMarcadas = new ArrayList<String>();
		colunasMarcadas.add("nome");
		colunasMarcadas.add("co.nomeComercial");
		action.setColunasMarcadas(colunasMarcadas);
		
		action.setConfiguracaoRelatorioDinamico(new ConfiguracaoRelatorioDinamico());
		action.getConfiguracaoRelatorioDinamico().setTitulo("titulo");
		
		when(areaOrganizacionalManager.filtraPermitidas(new String[]{}, empresa.getId())).thenReturn(areasPermitidasId);
		when(empresaManager.checkEmpresaIntegradaAc()).thenReturn(true);
		when(colaboradorManager.findAreaOrganizacionalByAreas(false, new ArrayList<Long>(), new ArrayList<Long>(), new ArrayList<Long>(), camposExtras, null, null, null, null, null, null, null, null, null, new Long[]{1L})).thenReturn(colaboradores);
		
		String retorno = action.relatorioDinamicoXLS();
		assertEquals(Action.SUCCESS, retorno);
	}
	
	@Test
	public void testRelatorioDinamicoXLSAgrupadoPorTempoDeServico() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		Long[] colaboradoresCheck = new Long[]{1L};
		action.setColaboradoresCheck(colaboradoresCheck);
		
		String[] areasPermitidasId = new String[]{"000001"};
		
		CamposExtras camposExtras = new CamposExtras();
		camposExtras.setId(1l);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
		colaboradores.add(colaborador);
		Collection<String> colunasMarcadas = new ArrayList<String>();
		colunasMarcadas.add("nome");
		colunasMarcadas.add("co.nomeComercial");
		action.setColunasMarcadas(colunasMarcadas);
		
		action.setConfiguracaoRelatorioDinamico(new ConfiguracaoRelatorioDinamico());
		action.getConfiguracaoRelatorioDinamico().setTitulo("titulo");
		
		action.setAgruparPorTempoServico(true);
		
		when(areaOrganizacionalManager.filtraPermitidas(new String[]{}, empresa.getId())).thenReturn(areasPermitidasId);
		when(empresaManager.checkEmpresaIntegradaAc()).thenReturn(true);
		when(colaboradorManager.montaTempoServico(colaboradores, null, null, "")).thenReturn(colaboradores);
		when(colaboradorManager.findAreaOrganizacionalByAreas(false, new ArrayList<Long>(), new ArrayList<Long>(), new ArrayList<Long>(), camposExtras, " co.dataAdmissao desc, null", null, null, null, null, null, null, null, null, new Long[]{1L})).thenReturn(colaboradores);
		
		String retorno = action.relatorioDinamicoXLS();
		assertEquals(Action.SUCCESS, retorno);
	}
	
	@Test
	public void testRelatorioDinamicoXLSException() throws Exception
	{
		String retorno = action.relatorioDinamicoXLS();
		
		assertEquals(Action.INPUT, retorno);
		assertEquals(action.getActionMessages().toArray()[0], "Não foi possível gerar o relatório.");
		
	}
	
	@Test
	public void testPrepareRelatorioFormacaoEscolar() throws Exception
	{
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity());
		String retorno = action.prepareRelatorioFormacaoEscolar();
		
		assertEquals(Action.SUCCESS, retorno);
	}
	
	@Test
	public void testImprimeRelatorioFormacaoEscolar() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		when(empresaManager.findById(empresa.getId())).thenReturn(empresa);
		when(colaboradorManager.findFormacaoEscolar(empresa.getId(), new ArrayList<Long>(), new ArrayList<Long>(), new ArrayList<Long>())).thenReturn(new ArrayList<Colaborador>());
		when(colaboradorManager.ordenaPorEstabelecimentoArea(new ArrayList<Colaborador>(), empresa.getId())).thenReturn(new ArrayList<Colaborador>());
		when(areaOrganizacionalManager.getParametrosRelatorio("Relatório de Formação Escolar", empresa, null)).thenReturn(null);
		
		String retorno = action.imprimeRelatorioFormacaoEscolar();
		
		assertEquals(Action.SUCCESS, retorno);
	}
	
	@Test
	public void testImprimeRelatorioFormacaoEscolarException() throws Exception
	{
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity());		
		String retorno = action.imprimeRelatorioFormacaoEscolar();
		
		assertEquals(Action.INPUT, retorno);
		assertEquals(action.getActionErr(), "Não foi possível gerar o relatório");
	}
	
	
	@Test
	public void testRelatorioAniversariantesXls() throws Exception
	{
		when(empresaManager.selecionaEmpresa(null, null, null)).thenReturn(new Long[]{});
		when(colaboradorManager.findAniversariantes(null, 0, null, null)).thenReturn(new ArrayList<Colaborador>());
		
		assertEquals("sucessoCargoTodosMeses", action.relatorioAniversariantesXls());
		action.setExibir('A');
		assertEquals("sucessoAreaTodosMeses", action.relatorioAniversariantesXls());
		action.setMes(2);
		assertEquals("sucessoArea", action.relatorioAniversariantesXls());
		action.setExibir('B');
		assertEquals("sucessoCargo", action.relatorioAniversariantesXls());
	}
	
	@Test
	public void testReciboPagamento() throws Exception
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("codigoAc");
		
		String dataInicioDoGozo = "01/01/2016";
		String dataFimDoGozo = "30/01/2016";
		action.setDataInicioGozo(dataInicioDoGozo);
		action.setDataFimGozo(dataFimDoGozo);
		String mesAno = "01/2016";
		action.setMesAno(mesAno);
		
		when(colaboradorManager.getReciboPagamento(colaborador, action.getMesAnoDate())).thenReturn(retorno);		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		
		Exception expException = null;
		try {
			assertEquals("success",action.reciboPagamento());
		} catch (Exception e) {
			expException = e;
		}
		
		assertNull(expException);
	}
	
	@Test
	public void testReciboPagamentoException() throws Exception
	{
		assertEquals(Action.INPUT ,action.reciboPagamento());
	}
	
	@Test
	public void testPrepareReciboDeDecimoTerceiro() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getDatasDecimoTerceiroPorEmpregado(colaborador)).thenReturn(new String[]{"01/01/2016"});

		assertEquals("success",action.prepareReciboDeDecimoTerceiro());
	}
	
	@Test
	public void testPrepareReciboDeDecimoTerceiroException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getDatasDecimoTerceiroPorEmpregado(colaborador)).thenThrow(new Exception());

		assertEquals(Action.SUCCESS ,action.prepareReciboDeDecimoTerceiro());
		assertEquals(action.getActionErrors().toArray()[0], "Houve um erro inesperado: null");
	}
	
	@Test
	public void testReciboDeDecimoTerceiro() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String dataCalculo = "01/01/2016";
		action.setDataCalculo(dataCalculo);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getReciboDeDecimoTerceiro(colaborador, dataCalculo)).thenReturn(retorno);

		assertEquals("success",action.reciboDeDecimoTerceiro());
	}
	
	@Test
	public void testReciboDeDecimoTerceiroException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String dataCalculo = "01/01/2016";
		action.setDataCalculo(dataCalculo);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getReciboDeDecimoTerceiro(colaborador, dataCalculo)).thenThrow(new Exception());

		assertEquals(Action.INPUT ,action.reciboDeDecimoTerceiro());
		assertEquals(action.getActionErrors().toArray()[1], "Houve um erro inesperado: null");
	}
	
	@Test
	public void testDeclaracaoRendimentos() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String ano = "2016";
		action.setAnoDosRendimentos(ano);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getDeclaracaoRendimentos(colaborador, ano)).thenReturn(retorno);

		assertEquals("success",action.declaracaoRendimentos());
	}
	
	@Test
	public void testDeclaracaoRendimentosException() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity("001", 1L);
		colaborador.setEmpresa(empresa);
		
		String ano = "01/01/2016";
		action.setDataCalculo(ano);
		
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
		when(colaboradorManager.getDeclaracaoRendimentos(colaborador, ano)).thenThrow(new Exception());

		assertEquals(Action.INPUT ,action.declaracaoRendimentos());
		assertEquals(action.getActionErrors().toArray()[1], "Houve um erro inesperado: null");
	}
}
