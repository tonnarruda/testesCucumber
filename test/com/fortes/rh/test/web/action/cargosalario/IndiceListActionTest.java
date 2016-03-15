package com.fortes.rh.test.web.action.cargosalario;

import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.cargosalario.IndiceListAction;

public class IndiceListActionTest extends MockObjectTestCase
{
	private IndiceListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new IndiceListAction();

        manager = new Mock(IndiceManager.class);
        action.setIndiceManager((IndiceManager) manager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

   public void testList() throws Exception
   {
	   Collection<Indice> indices = IndiceFactory.getCollection(1L);

	   manager.expects(once()).method("getCount").with(ANYTHING).will(returnValue(1));
	   manager.expects(once()).method("findIndices").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(indices));

	   assertEquals("success", action.list());
	   assertEquals(indices, action.getIndices());
   }

    public void testDelete() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setAcIntegra(false);
    	action.setEmpresaSistema(empresa);

    	Indice indice = IndiceFactory.getEntity(10000L);
    	action.setIndiceAux(indice);

    	manager.expects(once()).method("removeIndice").with(eq(indice.getId()));
    	 
    	assertEquals("success", action.delete());
    	assertFalse(action.getActionMessages().isEmpty());
    }

    public void testGet() throws Exception
    {
    	assertNotNull(action.getIndiceAux());
    }
}