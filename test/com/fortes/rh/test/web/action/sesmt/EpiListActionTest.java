package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.TipoEPIManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EpiFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.sesmt.EpiListAction;
import com.fortes.web.tags.CheckBox;

public class EpiListActionTest extends MockObjectTestCase
{
	private EpiListAction action;
	private Mock manager;
	private Mock tipoEPIManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new EpiListAction();
        manager = new Mock(EpiManager.class);
        action.setEpiManager((EpiManager) manager.proxy());
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
        
        tipoEPIManager = new Mock(TipoEPIManager.class);
        action.setTipoEPIManager((TipoEPIManager) tipoEPIManager.proxy());
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    	manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testDelete() throws Exception
    {
    	Epi epi = new Epi();
    	epi.setId(1L);
    	action.setEpi(epi);

    	action.setMsgAlert("deletando");
    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(1));
		manager.expects(once()).method("findEpis").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<Epi>()));
    	manager.expects(once()).method("removeEpi").with(ANYTHING);

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionSuccess().isEmpty());

    	manager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(1));
    	manager.expects(once()).method("findEpis").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<Epi>()));

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionMessages().isEmpty());
    }

    public void testList() throws Exception
    {
		Collection<Epi> epis = new ArrayList<Epi>();

		Epi epi = new Epi();
		epi.setId(1L);
		epi.setEmpresa(MockSecurityUtil.getEmpresaSession(null));

		epis.add(epi);

		manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(epis.size()));
		manager.expects(once()).method("findEpis").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(epis));

    	assertEquals("success", action.list());
    	assertEquals(epis, action.getEpis());
    }

    public void testPrepareImprimirVencimentoCa() throws Exception
    {
    	Collection<CheckBox> tipoEPICheckList = new ArrayList<CheckBox>();
    	tipoEPIManager.expects(once()).method("getByEmpresa").with(ANYTHING).will(returnValue(tipoEPICheckList));

    	assertEquals("success",action.prepareImprimirVencimentoCa());
    }

    public void testImprimirVencimentoCa()
    {
    	Date venc = new Date();
    	action.setVenc(venc);
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaSistema );
		Collection<Epi> dataSource = new ArrayList<Epi>();
		dataSource.add(EpiFactory.getEntity(1L));

		manager.expects(once()).method("findByVencimentoCa").with(eq(venc),eq(1L), ANYTHING).will(returnValue(dataSource));
    	String ret = "";
    	try {
    		ret = action.imprimirVencimentoCa();
    	}catch (Exception e)
    	{
    	}
    	assertEquals("success", ret);

    	//Colecao vazia
    	dataSource.clear();
    	manager.expects(once()).method("findByVencimentoCa").with(eq(venc),eq(1L), ANYTHING).will(returnValue(dataSource));
    	tipoEPIManager.expects(once()).method("getByEmpresa").with(eq(1L)).will(returnValue(null));

    	try {
    		ret = action.imprimirVencimentoCa();
    	}catch (Exception e)
    	{
    	}
    	assertEquals("input", ret);
    }

    public void testGetSet() throws Exception
    {
    	Epi epi = new Epi();
    	epi.setId(1L);

    	action.setEpi(epi);

    	assertEquals(action.getEpi(), epi);

    	action.setMsgAlert("fortes");
    	assertEquals(action.getMsgAlert(), "fortes");

    	epi = null;
    	action.setEpi(epi);
    	assertTrue(action.getEpi() instanceof Epi);
//    	action.setVenc(new Date());
//    	action.getVenc();
//    	action.getDataSource();
//    	action.getParametros();
    }
}