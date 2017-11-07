package com.fortes.rh.test.web.action.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.UsuarioAjudaESocialManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.RiscoEditAction;
import com.fortes.web.tags.CheckBox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtil.class, CheckListBoxUtil.class})
public class RiscoEditActionTest
{
	private RiscoEditAction action;
	private RiscoManager manager;
	private EpiManager epiManager;
	private UsuarioAjudaESocialManager usuarioAjudaESocialManager;

	@Before
	public void setUp() throws Exception
	{
		manager = mock(RiscoManager.class);
		epiManager = mock(EpiManager.class);
		usuarioAjudaESocialManager = mock(UsuarioAjudaESocialManager.class);

		action = new RiscoEditAction();
		action.setRiscoManager(manager);
		action.setEpiManager(epiManager);
		action.setUsuarioAjudaESocialManager(usuarioAjudaESocialManager);

		PowerMockito.mockStatic(SecurityUtil.class);
		PowerMockito.mockStatic(CheckListBoxUtil.class);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		action.setUsuarioLogado(UsuarioFactory.getEntity(2L));
	}

	@Test
	public void testPrepareUpdate() throws Exception
	{
		Boolean epiAtivo = null;
		Risco risco = RiscoFactory.getEntity(1L);
		risco.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
		action.setRisco(risco);

		Collection<CheckBox> episCheckList = new ArrayList<CheckBox>();

		when(epiManager.populaCheckToEpi(eq(risco.getEmpresa().getId()),eq(epiAtivo))).thenReturn(episCheckList);

		episCheckList = MockCheckListBoxUtil.marcaCheckListBox(null, null, null);

		when(manager.findById(eq(risco.getId()))).thenReturn(risco);
		when(manager.findEpisByRisco(eq(risco.getId()))).thenReturn(new ArrayList<Epi>());
		assertEquals(action.prepareUpdate(), "success");
	}

	@Test
	public void testPrepareUpdateEmpresaErrada() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1234L);

		Risco risco = RiscoFactory.getEntity(1L);
		risco.setEmpresa(empresa);
		action.setRisco(risco);

		when(manager.findById(eq(risco.getId()))).thenReturn(risco);
		assertEquals(action.prepareUpdate(), "error");
		assertTrue(action.getMsgAlert() != null && !action.getMsgAlert().equals(""));
	}

	@Test
	public void testUpdate() throws Exception
	{
		Risco risco = RiscoFactory.getEntity(1L);
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		when(manager.verifyExists(any(String[].class), any(Object[].class))).thenReturn(true);
		when(epiManager.populaEpi(eq(episCheck))).thenReturn(new ArrayList<Epi>());

		assertEquals("success", action.update());

		when(manager.verifyExists(any(String[].class), any(Object[].class))).thenReturn(false);

		assertEquals("error", action.update());
	}
	
	@Test
	public void testUpdateComRiscoNulo() throws Exception
	{
		Risco risco = null;
		action.setRisco(risco);
		assertEquals("error", action.update());
	}
	
	@Test
	public void testUpdateRiscoComIdNulo() throws Exception
	{
		Risco risco = RiscoFactory.getEntity();
		action.setRisco(risco);
		assertEquals("error", action.update());
	}
	
	@Test
	public void testUpdateException() throws Exception
	{
		Risco risco = RiscoFactory.getEntity(1L);
		risco.setEmpresa(action.getEmpresaSistema());
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		when(manager.verifyExists(any(String[].class), any(Object[].class))).thenReturn(true);
		when(epiManager.populaEpi(eq(episCheck))).thenReturn(new ArrayList<Epi>());
		doThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))).when(manager).update(eq(risco));
		
		Boolean epiAtivo = null;
		when(epiManager.populaCheckToEpi(eq(risco.getEmpresa().getId()),eq(epiAtivo))).thenReturn(new ArrayList<CheckBox>());
		when(manager.findById(eq(risco.getId()))).thenReturn(risco);
		when(manager.findEpisByRisco(eq(risco.getId()))).thenReturn(new ArrayList<Epi>());
		
		assertEquals("input", action.update());
		assertNotNull(action.getActionErrors());
	}

	@Test
	public void testPrepareInsert() throws Exception
	{
		Risco risco = RiscoFactory.getEntity();
		action.setRisco(risco);

		when(epiManager.populaCheckToEpi(eq(1L),eq(true))).thenReturn(new ArrayList<CheckBox>());

		assertEquals(action.prepareInsert(), "success");
	}

	@Test
	public void testInsert() throws Exception
	{
		Risco risco = RiscoFactory.getEntity();
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		MockSecurityUtil.getEmpresaSession(null);
		when(epiManager.populaEpi(eq(episCheck))).thenReturn(new ArrayList<Epi>());

		assertEquals(action.insert(), "success");
		assertEquals(action.getRisco(), risco);
	}
	
	@Test
	public void testInsertException() throws Exception
	{
		Risco risco = RiscoFactory.getEntity();
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		MockSecurityUtil.getEmpresaSession(null);
		when(epiManager.populaEpi(eq(episCheck))).thenReturn(new ArrayList<Epi>());
		doThrow(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))).when(manager).save(eq(risco));
		when(epiManager.populaCheckToEpi(eq(1L),eq(true))).thenReturn(new ArrayList<CheckBox>());
		
		assertEquals(action.insert(), "input");
		assertNotNull(action.getActionErrors());
	}
	
	@Test
	public void testDeleteRiscoComIdNulo() throws Exception
    {
    	Risco risco = new Risco();
    	action.setRisco(risco);

    	when(manager.getCount(any(Risco.class))).thenReturn(0);
		when(manager.listRiscos(eq(action.getPage()), eq(action.getPagingSize()), any(Risco.class))).thenReturn(new ArrayList<Risco>());

    	assertEquals("success", action.delete());
    	String msgAlert = action.getMsgAlert().replaceAll("%20", " ");
    	assertTrue(msgAlert.contains("O Risco solicitado não existe na empresa"));
    }

	@Test
	public void testDelete() throws Exception
    {
    	Risco risco = new Risco();
    	risco.setId(1L);
    	risco.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setRisco(risco);

    	when(manager.verifyExists(any(String[].class), any(Object[].class))).thenReturn(true);
    	
    	when(manager.getCount(any(Risco.class))).thenReturn(0);
		when(manager.listRiscos(eq(action.getPage()), eq(action.getPagingSize()), any(Risco.class))).thenReturn(new ArrayList<Risco>());
    	
    	action.setMsgAlert("deletado");

    	assertEquals("success", action.delete());
    	assertNotNull(action.getActionMessages());
    }
	
	@Test
	public void testDeleteEmpresaErrada() throws Exception
	{
		Risco risco = RiscoFactory.getEntity(1L);
		risco.setEmpresa(EmpresaFactory.getEmpresa(31231232L));
		action.setRisco(risco);
		
		when(manager.verifyExists(any(String[].class), any(Object[].class))).thenReturn(false);
		when(manager.getCount(any(Risco.class))).thenReturn(0);
		when(manager.listRiscos(eq(action.getPage()), eq(action.getPagingSize()), any(Risco.class))).thenReturn(new ArrayList<Risco>());
    		
		assertEquals("success", action.delete());
    	assertNotNull(action.getActionErrors());
	}

	@Test
    public void testList() throws Exception
    {
		Collection<Risco> riscos = new ArrayList<Risco>();

		Risco risco = new Risco();
		risco.setId(1L);
		risco.setEmpresa(MockSecurityUtil.getEmpresaSession(null));

		riscos.add(risco);

		when(manager.getCount(any(Risco.class))).thenReturn(1);
		when(manager.listRiscos(eq(action.getPage()), eq(action.getPagingSize()), any(Risco.class))).thenReturn(riscos);
    
    	assertEquals("success", action.list());
    	assertEquals(riscos, action.getRiscos());
    }
    
	@Test
    public void testGetSet() throws Exception
	{
		action.setRisco(null);
		assertTrue(action.getRisco() instanceof Risco);

		assertEquals(action.getGrupoRiscos(), GrupoRisco.getInstance());

		String[] episCheck = new String[1];
		action.setEpisCheck(episCheck);
		assertEquals(action.getEpisCheck(), episCheck);

		Collection<CheckBox> episCheckList = new ArrayList<CheckBox>();
		action.setEpisCheckList(episCheckList);
		assertEquals(action.getEpisCheckList(), episCheckList);

		Collection<Epi> epis = new ArrayList<Epi>();
		action.setEpis(epis);
		assertEquals(action.getEpis(), epis);
	}
}