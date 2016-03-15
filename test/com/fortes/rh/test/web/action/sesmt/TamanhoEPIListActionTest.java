package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.sesmt.TamanhoEPIManager;
import com.fortes.rh.model.sesmt.TamanhoEPI;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.TamanhoEPIListAction;

public class TamanhoEPIListActionTest extends MockObjectTestCase
{
	private TamanhoEPIListAction action;
	private Mock manager;

	protected void setUp() throws Exception
    {
        super.setUp();
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        action = new TamanhoEPIListAction();
        manager = new Mock(TamanhoEPIManager.class);

        action.setTamanhoEPIManager((TamanhoEPIManager) manager.proxy());
    }
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }
	
	public void testList() throws Exception
    {
    	prepareListTamanhosEpi();
    	assertEquals("success", action.list());
    }
	
	public void testDelete() throws Exception
    {
    	TamanhoEPI tamanhoEpi = new TamanhoEPI();
    	tamanhoEpi.setId(1L);
    	action.setTamanhoEPI(tamanhoEpi);

    	manager.expects(once()).method("remove").with(eq(tamanhoEpi));
    	prepareListTamanhosEpi();

    	assertEquals("success", action.delete());
    }
	 
	public void testDeleteDataIntegrityViolationException() throws Exception
	{
		TamanhoEPI tamanhoEpi = new TamanhoEPI();
    	tamanhoEpi.setId(1L);
    	action.setTamanhoEPI(tamanhoEpi);
    	
    	manager.expects(once()).method("remove").with(eq(tamanhoEpi)).will(throwException(new DataIntegrityViolationException("")));

    	prepareListTamanhosEpi();
    	
		assertEquals("success", action.delete());
		assertEquals(1, action.getActionWarnings().size()); 
	}
	
	private void prepareListTamanhosEpi() {
		Collection<TamanhoEPI> tamanhosEPI = new ArrayList<TamanhoEPI>();
    	manager.expects(once()).method("findAll").with(eq(new String[]{"descricao"})).will(returnValue(tamanhosEPI));
	}
}