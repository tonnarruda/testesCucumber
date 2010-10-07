package com.fortes.rh.test.web.action.sesmt;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AnexoManager;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.sesmt.Anexo;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.web.action.sesmt.AnexoListAction;

public class AnexoListActionTest extends MockObjectTestCase
{
	private AnexoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new AnexoListAction();
        manager = new Mock(AnexoManager.class);

        action.setAnexoManager((AnexoManager) manager.proxy());
        Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testDelete() throws Exception
    {
    	Anexo anexo = new Anexo();
    	anexo.setId(1L);
    	anexo.setOrigem(OrigemAnexo.LTCAT);
    	action.setAnexo(anexo);

    	manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(anexo));
    	manager.expects(once()).method("remove").with(ANYTHING);
    	manager.expects(once()).method("getStringRetorno").with(eq(anexo.getOrigem())).will(returnValue("successLTCAT"));
    	assertEquals("successLTCAT", action.delete());
    }

    public void testGetSet() throws Exception
    {
    	Anexo anexo = null;
    	action.setAnexo(anexo);
    	assertTrue(action.getAnexo() instanceof Anexo);
    }
}