package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.RiscoManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.RiscoEditAction;
import com.fortes.web.tags.CheckBox;

public class RiscoEditActionTest extends MockObjectTestCase
{
	private RiscoEditAction action;
	private Mock manager;
	private Mock epiManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(RiscoManager.class);
		epiManager = new Mock(EpiManager.class);

		action = new RiscoEditAction();
		action.setRiscoManager((RiscoManager) manager.proxy());
		action.setEpiManager((EpiManager) epiManager.proxy());

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
		
		action.setEmpresaSistema(MockSecurityUtil.getEmpresaSession(null));
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		manager = null;
		epiManager = null;
		action = null;
        MockSecurityUtil.verifyRole = false;
		super.tearDown();
	}

	public void testPrepareUpdate() throws Exception
	{
		Risco risco = RiscoFactory.getEntity(1L);
		risco.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
		action.setRisco(risco);

		Collection<CheckBox> episCheckList = new ArrayList<CheckBox>();

		epiManager.expects(once()).method("populaCheckToEpi").with(eq(risco.getEmpresa().getId()),eq(null)).will(returnValue(episCheckList));

		episCheckList = MockCheckListBoxUtil.marcaCheckListBox(null, null, null);

		manager.expects(once()).method("findById").with(eq(risco.getId())).will(returnValue(risco));
		manager.expects(once()).method("findEpisByRisco").with(eq(risco.getId())).will(returnValue(new ArrayList<Risco>()));
		assertEquals(action.prepareUpdate(), "success");
	 }

	public void testPrepareUpdateEmpresaErrada() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1234L);

		Risco risco = RiscoFactory.getEntity(1L);
		risco.setEmpresa(empresa);
		action.setRisco(risco);

		manager.expects(once()).method("findById").with(eq(risco.getId())).will(returnValue(risco));
		assertEquals(action.prepareUpdate(), "error");
		assertTrue(action.getMsgAlert() != null && !action.getMsgAlert().equals(""));
	 }

	public void testUpdate() throws Exception
	{
		Risco risco = RiscoFactory.getEntity(1L);
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
		epiManager.expects(once()).method("populaEpi").with(eq(episCheck)).will(returnValue(new ArrayList<Epi>()));
		manager.expects(once()).method("update").with(eq(risco));

		assertEquals("success", action.update());

		manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));

		assertEquals("error", action.update());
	}
	
	public void testUpdateException() throws Exception
	{
		Risco risco = RiscoFactory.getEntity(1L);
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
		epiManager.expects(once()).method("populaEpi").with(eq(episCheck)).will(returnValue(new ArrayList<Epi>()));
		manager.expects(once()).method("update").with(eq(risco)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		epiManager.expects(once()).method("populaCheckToEpi");
		manager.expects(once()).method("findById").with(eq(risco.getId())).will(returnValue(risco));
		manager.expects(once()).method("findEpisByRisco").with(eq(risco.getId())).will(returnValue(new ArrayList<Risco>()));
		
		assertEquals("input", action.update());
		assertNotNull(action.getActionErrors());
	}

	public void testPrepareInsert() throws Exception
	{
		Risco risco = RiscoFactory.getEntity();
		action.setRisco(risco);

		epiManager.expects(once()).method("populaCheckToEpi").with(eq(1L),eq(true)).will(returnValue(new ArrayList<CheckBox>()));

		assertEquals(action.prepareInsert(), "success");
	}

	public void testInsert() throws Exception
	{
		Risco risco = RiscoFactory.getEntity();
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		MockSecurityUtil.getEmpresaSession(null);
		epiManager.expects(once()).method("populaEpi").with(eq(episCheck)).will(returnValue(new ArrayList<Epi>()));
		manager.expects(once()).method("save").with(eq(risco));

		assertEquals(action.insert(), "success");
		assertEquals(action.getRisco(), risco);
	}
	
	public void testInsertException() throws Exception
	{
		Risco risco = RiscoFactory.getEntity();
		action.setRisco(risco);

		String[] episCheck = new String[]{"3","33"};
		action.setEpisCheck(episCheck);

		MockSecurityUtil.getEmpresaSession(null);
		epiManager.expects(once()).method("populaEpi").with(eq(episCheck)).will(returnValue(new ArrayList<Epi>()));
		manager.expects(once()).method("save").with(eq(risco)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		epiManager.expects(once()).method("populaCheckToEpi").with(eq(1L),eq(true)).will(returnValue(new ArrayList<CheckBox>()));
		
		assertEquals(action.insert(), "input");
		assertNotNull(action.getActionErrors());
	}

	public void testDelete() throws Exception
    {
    	Risco risco = new Risco();
    	risco.setId(1L);
    	risco.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setRisco(risco);

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(1));
    	manager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Risco>()));
    	manager.expects(once()).method("remove").with(ANYTHING);
    	action.setMsgAlert("deletado");

    	assertEquals("success", action.delete());
    	assertNotNull(action.getActionMessages());
    }
	
	public void testDeleteEmpresaErrada() throws Exception
	{
		Risco risco = RiscoFactory.getEntity(1L);
		risco.setEmpresa(EmpresaFactory.getEmpresa(31231232L));
		action.setRisco(risco);
		
		manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
		manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(1));
    	manager.expects(once()).method("find").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(new ArrayList<Risco>()));
		
		assertEquals("success", action.delete());
    	assertNotNull(action.getActionErrors());
	}

    public void testList() throws Exception
    {
		Collection<Risco> riscos = new ArrayList<Risco>();

		Risco risco = new Risco();
		risco.setId(1L);
		risco.setEmpresa(MockSecurityUtil.getEmpresaSession(null));

		riscos.add(risco);

		manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(riscos.size()));
		manager.expects(once()).method("find").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(riscos));

    	assertEquals("success", action.list());
    	assertEquals(riscos, action.getRiscos());
    }
    
    public void testGetSet() throws Exception
	{
		action.setRisco(null);
		assertTrue(action.getRisco() instanceof Risco);

		Map<String, String> grupoRiscos = new HashMap<String, String>();
		action.setGrupoRiscos(grupoRiscos);
		assertEquals(action.getGrupoRiscos(), grupoRiscos);

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