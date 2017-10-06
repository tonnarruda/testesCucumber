package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraVisivelObrigadotorioManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.dicionario.TipoConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtraVisivelObrigadotorio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.ConfiguracaoCampoExtraVisivelObrigadotorioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.web.action.geral.ParametrosDoSistemaEditAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

public class ParametrosDoSistemaEditActionTest
{
	private ParametrosDoSistemaEditAction action;
	private ParametrosDoSistemaManager manager;
	private PerfilManager perfilManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private EmpresaManager empresaManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private FaixaSalarialManager faixaSalarialManager;
	private IndiceManager indiceManager;
	private FuncaoManager funcaoManager; 
	private OcorrenciaManager ocorrenciaManager;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private ConfiguracaoCampoExtraVisivelObrigadotorioManager configuracaoCampoExtraVisivelObrigadotorioManager;

	@Before
	public void setUp()
	{
		action = new ParametrosDoSistemaEditAction();
		manager = mock(ParametrosDoSistemaManager.class);
		perfilManager = mock(PerfilManager.class);
		configuracaoCampoExtraManager = mock(ConfiguracaoCampoExtraManager.class);
		empresaManager = mock(EmpresaManager.class);
		estabelecimentoManager = mock(EstabelecimentoManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		colaboradorManager = mock(ColaboradorManager.class);
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		indiceManager = mock(IndiceManager.class);
		funcaoManager = mock(FuncaoManager.class);
		ocorrenciaManager = mock(OcorrenciaManager.class);
		colaboradorOcorrenciaManager = mock(ColaboradorOcorrenciaManager.class);
		configuracaoCampoExtraVisivelObrigadotorioManager = mock(ConfiguracaoCampoExtraVisivelObrigadotorioManager.class);

		action.setParametrosDoSistemaManager(manager);
		action.setPerfilManager(perfilManager);
		action.setConfiguracaoCampoExtraManager(configuracaoCampoExtraManager);
		action.setEmpresaManager(empresaManager);
		action.setEstabelecimentoManager(estabelecimentoManager);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		action.setColaboradorManager(colaboradorManager);
		action.setFaixaSalarialManager(faixaSalarialManager);
		action.setIndiceManager(indiceManager);
		action.setFuncaoManager(funcaoManager);
		action.setOcorrenciaManager(ocorrenciaManager);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setColaboradorOcorrenciaManager(colaboradorOcorrenciaManager);
		action.setConfiguracaoCampoExtraVisivelObrigadotorioManager(configuracaoCampoExtraVisivelObrigadotorioManager);
		
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}

	@Test
    public void testPrepareUpdate() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setModulosPermitidosSomatorio(21);// número 21 representa 3 modulos.
    	
    	action.setParametrosDoSistema(parametrosDoSistema);
    	
    	when(manager.findById(1L)).thenReturn(parametrosDoSistema);
    	when(perfilManager.findAll()).thenReturn(new ArrayList<Perfil>());

    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(8, action.getModulosSistema().size());
    	assertEquals(3, action.getModulosSistemaCheck().length);
    }
  
	@Test
    public void testUpdate() throws Exception
    {
    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setSessionTimeout(90);
    	
    	action.setParametrosDoSistema(parametrosDoSistema);
    	
    	action.setParametrosDoSistema(parametrosDoSistema);
    	action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
    	
    	when(manager.findById(1L)).thenReturn(parametrosDoSistema);
    	when(perfilManager.findAll()).thenReturn(new ArrayList<Perfil>());

    	assertEquals("success", action.update());
    	assertEquals("Configurações do sistema atualizadas com sucesso.", action.getActionSuccess().toArray()[0]);
    }
    
	@Test
    public void testGetsSets() throws Exception
    {
    	action.getPerfils();
    	action.setParametrosDoSistema(null);
    	assertNotNull(action.getParametrosDoSistema());
    }
	
	@Test
	public void testListCampos() throws Exception{
		assertEquals(Action.SUCCESS, action.listCampos());
	}
	
	@Test
	public void testConfigCamposColaborador() throws Exception{
		MockSecurityUtil.roles = new String[]{"ROLE_CONFIG_CAMPOS_PADROES_DO_SISTEMA_PARA_COLABORADOR"};
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		
		action.setEntidade("colaborador");
		action.setEmpresaSistema(empresa);
		
		String tipoCampoExtra = "ativoColaborador";
		
		when(configuracaoCampoExtraManager.find(new String[]{tipoCampoExtra, "empresa.id"}, new Object[]{true, action.getEmpresaSistema().getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());
		when(manager.findByIdProjection(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity(1L));
		
		assertEquals(Action.SUCCESS + "_colaborador", action.configCampos());
	}
	
	@Test
	public void testConfigCamposCandidato() throws Exception{
		MockSecurityUtil.roles = new String[]{"ROLE_CONFIG_CAMPOS_PADROES_DO_SISTEMA_PARA_COLABORADOR"};
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setCampoExtraCandidato(true);
		
		action.setEntidade("candidato");
		action.setEmpresaSistema(empresa);
		
		String tipoCampoExtra = "ativoCandidato";
		
		when(configuracaoCampoExtraManager.find(new String[]{tipoCampoExtra, "empresa.id"}, new Object[]{true, action.getEmpresaSistema().getId()}, new String[]{"ordem"})).thenReturn(new ArrayList<ConfiguracaoCampoExtra>());
		when(manager.findByIdProjection(1L)).thenReturn(ParametrosDoSistemaFactory.getEntity(1L));
		
		assertEquals(Action.SUCCESS + "_candidato", action.configCampos());
	}
	
	@Test
	public void testPrepareDeleteSemCodigoAC() throws Exception{
		Empresa empresa = action.getEmpresaSistema(); 
		
		when(empresaManager.findComCodigoAC()).thenReturn(Arrays.asList(action.getEmpresaSistema()));
		when(estabelecimentoManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(EstabelecimentoFactory.getEntity()));
		when(areaOrganizacionalManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(AreaOrganizacionalFactory.getEntity()));
		when(colaboradorManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(ColaboradorFactory.getEntity()));
		when(faixaSalarialManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(FaixaSalarialFactory.getEntity()));
		when(indiceManager.findSemCodigoAC(empresa)).thenReturn(Arrays.asList(IndiceFactory.getEntity()));
		when(ocorrenciaManager.findSemCodigoAC(empresa.getId(), true)).thenReturn(Arrays.asList(OcorrenciaFactory.getEntity()));
		when(empresaManager.verificaIntegracaoAC(action.getEmpresaSistema())).thenReturn(Arrays.asList("msg"));
		when(funcaoManager.findByEmpresaAndCodigoACIsNull(empresa.getId())).thenReturn(Arrays.asList(FuncaoFactory.getEntity()));
		
		assertEquals(Action.SUCCESS, action.prepareDeleteSemCodigoAC());
		assertEquals("- Existem entidades sem código AC", ((String) action.getActionMessages().toArray()[0]));
	}
	
	@Test
	public void testDeleteSemCodigoAC() throws Exception{
		Empresa empresa = action.getEmpresaSistema(); 
		
		when(empresaManager.findComCodigoAC()).thenReturn(Arrays.asList(action.getEmpresaSistema()));
		when(estabelecimentoManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(EstabelecimentoFactory.getEntity()));
		when(areaOrganizacionalManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(AreaOrganizacionalFactory.getEntity()));
		when(colaboradorManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(ColaboradorFactory.getEntity()));
		when(faixaSalarialManager.findSemCodigoAC(empresa.getId())).thenReturn(Arrays.asList(FaixaSalarialFactory.getEntity()));
		when(indiceManager.findSemCodigoAC(empresa)).thenReturn(Arrays.asList(IndiceFactory.getEntity()));
		when(ocorrenciaManager.findSemCodigoAC(empresa.getId(), true)).thenReturn(Arrays.asList(OcorrenciaFactory.getEntity()));
		when(empresaManager.verificaIntegracaoAC(action.getEmpresaSistema())).thenReturn(Arrays.asList("msg"));
		when(funcaoManager.findByEmpresaAndCodigoACIsNull(empresa.getId())).thenReturn(Arrays.asList(FuncaoFactory.getEntity()));
		
		assertEquals(Action.SUCCESS, action.deleteSemCodigoAC());
		assertEquals("- Existem entidades sem código AC", ((String) action.getActionMessages().toArray()[0]));
	}
	
	@Test
	public void testListCamposExtras() throws Exception
	{
		assertEquals(Action.SUCCESS, action.listCamposExtras());
	}
	
	@Test
	public void testConfigCamposExtrasComConfiguracaoInexistenteNoBanco() throws Exception
	{
		inicializaConfiCamposExtras();
		when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(action.getEmpresa().getId(), action.getEntidade())).thenReturn(null);
		
		assertEquals(Action.SUCCESS + "_" + action.getEntidade(), action.configCamposExtras());
	}
	
	@Test
	public void testConfigCamposExtras() throws Exception
	{
		inicializaConfiCamposExtras();
		ConfiguracaoCampoExtraVisivelObrigadotorio configuracaoCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(action.getEmpresa().getId(), "texto1", TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
		when(configuracaoCampoExtraVisivelObrigadotorioManager.findByEmpresaId(action.getEmpresa().getId(), action.getEntidade())).thenReturn(configuracaoCampoExtraVisivelObrigadotorio);
		
		assertEquals(Action.SUCCESS + "_" + action.getEntidade(), action.configCamposExtras());
	}
	
	private void inicializaConfiCamposExtras(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		action.setParametrosDoSistema(parametrosDoSistema);
		action.setEmpresa(empresa);
		action.setEntidade(TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
				
		Collection<Empresa> empresas = Arrays.asList(empresa);
		Usuario usuarioLogado = UsuarioFactory.getEntity(2L);
		action.setUsuarioLogado(usuarioLogado);
		
		
		when(manager.findById(eq(1L))).thenReturn(parametrosDoSistema);
		when(empresaManager.findEmpresasPermitidas(eq(parametrosDoSistema.getCompartilharColaboradores()), eq(action.getEmpresa().getId()), eq(action.getUsuarioLogado().getId()))).thenReturn(empresas);
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = ConfiguracaoCampoExtraFactory.getCollection();					
		
		when(configuracaoCampoExtraManager.find(eq(new String[]{"ativoColaborador", "empresa.id"}), eq(new Object[]{true, action.getEmpresa().getId()}),eq( new String[]{"ordem"}))).thenReturn(configuracaoCampoExtras);
	}

	
	@Test
	public void testUpdateConfigCamposExtras() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEntidade(TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
		
		ConfiguracaoCampoExtraVisivelObrigadotorio configuracaoCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "texto1", TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
		action.setCamposVisivels(new String[]{"texto1"});
		action.setCampoExtraVisivelObrigadotorio(configuracaoCampoExtraVisivelObrigadotorio);
		action.updateConfigCamposExtras();
		
		verify(configuracaoCampoExtraVisivelObrigadotorioManager, times(1)).saveOrUpdate(configuracaoCampoExtraVisivelObrigadotorio);
		
		verify(configuracaoCampoExtraVisivelObrigadotorioManager, never())
		.removeByEmpresaAndTipoConfig(configuracaoCampoExtraVisivelObrigadotorio.getEmpresa().getId(), new String[]{configuracaoCampoExtraVisivelObrigadotorio.getTipoConfiguracaoCampoExtra()});
	}

	@Test
	public void testUpdateConfigCamposExtrasComCamposVisiveisVazio() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEntidade(TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
		
		ConfiguracaoCampoExtraVisivelObrigadotorio configuracaoCampoExtraVisivelObrigadotorio = ConfiguracaoCampoExtraVisivelObrigadotorioFactory.getEntity(empresa.getId(), "", TipoConfiguracaoCampoExtra.COLABORADOR.getTipo());
		action.setCamposVisivels(new String[]{});
		action.setCampoExtraVisivelObrigadotorio(configuracaoCampoExtraVisivelObrigadotorio);
		action.updateConfigCamposExtras();
		verify(configuracaoCampoExtraVisivelObrigadotorioManager, times(1))
		.removeByEmpresaAndTipoConfig(configuracaoCampoExtraVisivelObrigadotorio.getEmpresa().getId(), new String[]{configuracaoCampoExtraVisivelObrigadotorio.getTipoConfiguracaoCampoExtra()});
	}
	
	private void iniciaUpdateCampos() {
		action.setParametrosDoSistema(ParametrosDoSistemaFactory.getEntity(1L));
		
		when(manager.findById(1L)).thenReturn(action.getParametrosDoSistema());
		String[] camposObrigatorios =  new String[]{"camposObrigatorios"};
		String[] camposVisivels =  new String[]{"camposVisivels"};
		String camposTabs = "camposTabs";

		action.setCamposObrigatorios(camposObrigatorios);
		action.setCamposVisivels(camposVisivels);
		action.setCamposTabs(camposTabs);
	}
	
	@Test
	public void testUpdateCamposCandidato() throws Exception
	{
		iniciaUpdateCampos();
		assertEquals(Action.SUCCESS, action.updateCamposCandidato());
	}
	
	@Test
	public void testUpdateCamposColaborador() throws Exception
	{
		iniciaUpdateCampos();
		assertEquals(Action.SUCCESS, action.updateCamposColaborador());
	}

	@Test
	public void testUpdateCamposCandidatoExterno() throws Exception
	{
		iniciaUpdateCampos();
		assertEquals(Action.SUCCESS, action.updateCamposCandidatoExterno());
	}
	
}