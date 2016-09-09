package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import mockit.Mockit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.AreaOrganizacionalListAction;

public class AreaOrganizacionalListActionTest
{
	private AreaOrganizacionalListAction action;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	@Before
	public void setUp() throws Exception
	{
		action = new AreaOrganizacionalListAction();

		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	@After
	public void tearDown() throws Exception
    {
        action = null;
        MockSecurityUtil.verifyRole = false;
        Mockit.restoreAllOriginalDefinitions();
    }

	@Test
	public void testDeleteComUsuarioFortes() throws Exception
	{
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Empresa empresa = action.getEmpresaSistema();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, "Área 1", true, empresa);
		action.setAreaOrganizacional(areaOrganizacional);
		
		assertEquals("success", action.delete());
		assertTrue(action.getActionSuccess().iterator().next().equals("Área organizacional excluída com sucesso."));
	}
	
	@Test
	public void testDeleteComUsuarioFortesException() throws Exception
	{
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Empresa empresa = action.getEmpresaSistema();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, "Área 1", true, empresa);
		action.setAreaOrganizacional(areaOrganizacional);
		
		Exception e = new Exception(null, new FortesException("Área organizacinal possui dependência."));
		doThrow(e).when(areaOrganizacionalManager).removeComDependencias(areaOrganizacional.getId());
		
		assertEquals("success", action.delete());
		assertTrue(action.getActionWarnings().iterator().next().equals("Área organizacinal possui dependência."));
		
	}
	
	@Test
	public void testDeleteComUsuarioComum() throws Exception
	{
		action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
		
		Empresa empresa = action.getEmpresaSistema();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, "Área 1", true, empresa);
		action.setAreaOrganizacional(areaOrganizacional);
		
		assertEquals("success", action.delete());
		assertTrue(action.getActionSuccess().iterator().next().equals("Área organizacional excluída com sucesso."));
	}
	
	@Test
	public void testDeleteComUsuarioComumException() throws Exception
	{
		action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
		
		Empresa empresa = action.getEmpresaSistema();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, "Área 1", true, empresa);
		action.setAreaOrganizacional(areaOrganizacional);
		
		Exception e = new Exception();
		doThrow(e).when(areaOrganizacionalManager).deleteLotacaoAC(areaOrganizacional, empresa);
		
		assertEquals("success", action.delete());
		assertEquals("Não foi possível excluir a Área Organizacional.", action.getActionErrors().iterator().next());
		
	}
	
	@Test
	public void testDeleteComUsuarioComumFortesException() throws Exception
	{
		action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
		
		Empresa empresa = action.getEmpresaSistema();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, "Área 1", true, empresa);
		action.setAreaOrganizacional(areaOrganizacional);
		
		Exception e = new Exception(null, new IntegraACException("Não foi possível remover esta área no AC."));
		doThrow(e).when(areaOrganizacionalManager).deleteLotacaoAC(areaOrganizacional, empresa);
		
		assertEquals("success", action.delete());
		assertTrue(action.getActionErrors().iterator().next().equals("Não foi possível remover esta área no AC."));
		
	}
	
	@Test
	public void testDeleteComEmpresaSistemaDiferenteEmpresaDaArea() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L, "Área 1", true, empresa);
		action.setAreaOrganizacional(areaOrganizacional);
		
		assertEquals("success", action.delete());
		assertTrue(action.getActionWarnings().iterator().next().startsWith("A área organizacional solicitada não existe na empresa"));
		
	}
}
