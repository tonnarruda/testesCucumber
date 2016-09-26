package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import mockit.Mockit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.model.type.File;
import com.fortes.rh.business.geral.CartaoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.dicionario.FiltroControleVencimentoCertificacao;
import com.fortes.rh.model.dicionario.TipoCartao;
import com.fortes.rh.model.geral.Cartao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.CartaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.EmpresaEditAction;
import com.fortes.web.tags.CheckBox;

public class EmpresaEditActionTest
{
	private EmpresaEditAction action = new EmpresaEditAction();
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EmpresaManager empresaManager;
	private GrupoACManager grupoACManager;
	private CartaoManager cartaoManager;
	private EstadoManager estadoManager;
	
	@Before
    public void setUp() throws Exception
    {
		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        empresaManager = mock(EmpresaManager.class);
        grupoACManager = mock(GrupoACManager.class);
        cartaoManager = mock(CartaoManager.class);
        estadoManager = mock(EstadoManager.class);
        
        action.setParametrosDoSistemaManager(parametrosDoSistemaManager);
        action.setEmpresaManager(empresaManager);
        action.setGrupoACManager(grupoACManager);
        action.setCartaoManager(cartaoManager);
        action.setEstadoManager(estadoManager);
        action.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }
	
	@After
	public void tearDown() throws Exception
    {
        action = null;
        MockSecurityUtil.verifyRole = false;
        Mockit.restoreAllOriginalDefinitions();
    }

	@Test
    public void testExecute() throws Exception
    {
    	assertEquals("success",action.execute() );
    }
    
	@Test
    public void testSobre() throws Exception
    {
    	when(parametrosDoSistemaManager.findByIdProjection(eq(1L))).thenReturn(new ParametrosDoSistema());
    	assertEquals("success",action.sobre());
    }
    
	@Test
    public void testPrepareImportarCadastros()
    {
    	when(empresaManager.findAll()).thenReturn(new ArrayList<Empresa>());
    	when(empresaManager.populaCadastrosCheckBox()).thenReturn(new ArrayList<CheckBox>());
    	assertEquals("success", action.prepareImportarCadastros());
    }
    
	@Test
	public void testPreviewCartao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Cartao cartao = CartaoFactory.getEntity(2L, empresa);
		action.setCartao(cartao);
		
		when(cartaoManager.findEntidadeComAtributosSimplesById(cartao.getId())).thenReturn(cartao);
		assertEquals("success",action.previewCartao());
	}
	
	@Test
	public void testShowImgCartao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Cartao cartao = CartaoFactory.getEntity(2L, empresa);
		action.setCartao(cartao);
		
		assertEquals("success",action.showImgCartao());
	}
	
	@Test
	public void testPrepareInsert() throws Exception
	{
		action.setEmpresa(new Empresa());
		when(estadoManager.findAll((new String[]{"sigla"}))).thenReturn(new ArrayList<Estado>());
		when(grupoACManager.findAll(eq(new String[]{"codigo"}))).thenReturn(new ArrayList<GrupoAC>());
		assertEquals("success",action.prepareInsert());
	}
	
	@Test
	public void testPrepareUpdate() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		when(empresaManager.findById(empresa.getId())).thenReturn(empresa);
		when(cartaoManager.findByEmpresaId(empresa.getId())).thenReturn(new ArrayList<Cartao>());
		
		when(estadoManager.findAll((new String[]{"sigla"}))).thenReturn(new ArrayList<Estado>());
		when(grupoACManager.findAll(eq(new String[]{"codigo"}))).thenReturn(new ArrayList<GrupoAC>());
		assertEquals("success",action.prepareUpdate());
	}
	
	@Test
	public void testInsert() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		Cartao cartaoDeAniversario = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		Cartao cartaoAnoDeEmpresa = CartaoFactory.getEntity(empresa, TipoCartao.ANO_DE_EMPRESA);
		
		action.setCartaoAniversario(cartaoDeAniversario);
		action.setCartaoAnoDeEmpresa(cartaoAnoDeEmpresa);
		
		when(empresaManager.setLogo(eq(empresa), any(File.class), eq("logoEmpresas"), any(File.class))).thenReturn(empresa);
		assertEquals("success",action.insert());
	}
	
	@Test
	public void testInsertNaoCriaGerenciadoresDefault() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		Cartao cartaoDeAniversario = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		Cartao cartaoAnoDeEmpresa = CartaoFactory.getEntity(empresa, TipoCartao.ANO_DE_EMPRESA);
		
		action.setCartaoAniversario(cartaoDeAniversario);
		action.setCartaoAnoDeEmpresa(cartaoAnoDeEmpresa);
		
		when(empresaManager.setLogo(eq(empresa), any(File.class), eq("logoEmpresas"), any(File.class))).thenReturn(empresa);

		doThrow(Exception.class).when(gerenciadorComunicacaoManager).insereGerenciadorComunicacaoDefault(empresa);
		
		assertEquals("success",action.insert());
		assertEquals("Não foi possível inserir as configurações do gerenciador de comunicação. Entre em contato com o suporte.",action.getActionMessages().iterator().next());
	}
	

	@Test(expected=Exception.class)
	public void testupdateExceptionAoChecarGrupoAC() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		Cartao cartaoDeAniversario = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		Cartao cartaoAnoDeEmpresa = CartaoFactory.getEntity(empresa, TipoCartao.ANO_DE_EMPRESA);
		
		action.setCartaoAniversario(cartaoDeAniversario);
		action.setCartaoAnoDeEmpresa(cartaoAnoDeEmpresa);
		
		when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
		when(empresaManager.setLogo(eq(empresa), any(File.class), eq("logoEmpresas"), any(File.class))).thenReturn(empresa);

		when(empresaManager.checkEmpresaCodACGrupoAC(empresa)).thenReturn(true);
		action.update();
	}
	
	@Test
	public void testupdateNaoHabilitaIntegracao() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresa(empresa);
		
		Cartao cartaoDeAniversario = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		Cartao cartaoAnoDeEmpresa = CartaoFactory.getEntity(empresa, TipoCartao.ANO_DE_EMPRESA);
		
		action.setCartaoAniversario(cartaoDeAniversario);
		action.setCartaoAnoDeEmpresa(cartaoAnoDeEmpresa);
		
		when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
		when(empresaManager.setLogo(eq(empresa), any(File.class), eq("logoEmpresas"), any(File.class))).thenReturn(empresa);

		when(empresaManager.checkEmpresaCodACGrupoAC(empresa)).thenReturn(false);
		when(empresaManager.verificaInconcistenciaIntegracaoAC(empresa)).thenReturn(true);
		assertEquals("input", action.update());
		assertEquals("Não foi possível habilitar a integração com o Fortes Pessoal devido a cadastros realizados no período desintegrado.<br />Entre em contato com o suporte técnico.", action.getActionMsg());
	}
	
	@Test
	public void testupdate() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setControlarVencimentoCertificacaoPor(FiltroControleVencimentoCertificacao.CURSO.getOpcao());
		action.setEmpresa(empresa);
		
		Cartao cartaoDeAniversario = CartaoFactory.getEntity(empresa, TipoCartao.ANIVERSARIO);
		Cartao cartaoAnoDeEmpresa = CartaoFactory.getEntity(empresa, TipoCartao.ANO_DE_EMPRESA);
		
		action.setCartaoAniversario(cartaoDeAniversario);
		action.setCartaoAnoDeEmpresa(cartaoAnoDeEmpresa);
		
		when(empresaManager.findByIdProjection(empresa.getId())).thenReturn(empresa);
		when(empresaManager.setLogo(eq(empresa), any(File.class), eq("logoEmpresas"), any(File.class))).thenReturn(empresa);

		when(empresaManager.checkEmpresaCodACGrupoAC(empresa)).thenReturn(false);
		when(empresaManager.verificaInconcistenciaIntegracaoAC(empresa)).thenReturn(false);
		when(empresaManager.findById(empresa.getId())).thenReturn(empresa);
		assertEquals("success", action.update());
	}
}
