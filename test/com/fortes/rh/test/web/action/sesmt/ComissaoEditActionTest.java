package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.web.action.sesmt.ComissaoEditAction;

public class ComissaoEditActionTest extends MockObjectTestCase
{
	private ComissaoEditAction action;
	private Mock manager;
	private Mock eleicaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(ComissaoManager.class);
        action = new ComissaoEditAction();
        action.setComissaoManager((ComissaoManager) manager.proxy());

        eleicaoManager = new Mock(EleicaoManager.class);
        action.setEleicaoManager((EleicaoManager)eleicaoManager.proxy());
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

    public void testList() throws Exception
    {
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	manager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Comissao>()));
    	assertEquals(action.list(), "success");
    	assertNotNull(action.getComissaos());
    }

    public void testDelete() throws Exception
    {
    	action.setComissao(ComissaoFactory.getEntity(1L));

    	manager.expects(once()).method("remove").with(ANYTHING).isVoid();
    	assertEquals(action.delete(), "success");
    }

    public void testPrepare()
    {
    	Collection<Eleicao> eleicaos = new ArrayList<Eleicao>();
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	manager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(comissao));
    	eleicaoManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(eleicaos));
		String ret = "";
    	try {
    		ret = action.prepare();
    	}catch (Exception e)
    	{
    	}
    	assertEquals(ret,"success");
    }

    public void testInsert()
    {
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	manager.expects(once()).method("save").with(eq(comissao)).will(returnValue(comissao));

    	String ret = "";
    	try {
    		ret = action.insert();
    	}catch (Exception e)
    	{
    	}
    	assertEquals("success", ret);
    }
    public void testUpdate()
    {
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

    	manager.expects(once()).method("update").with(eq(comissao)).isVoid();
    	manager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Comissao>()));

    	String ret = "";
    	try {
    		ret = action.update();
    	}catch (Exception e)
    	{
    	}
    	assertEquals("success", ret);
    }

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getComissao());

    	Comissao eleicaol = new Comissao();
    	action.setComissao(eleicaol);
    	assertTrue(action.getComissao() instanceof Comissao);
    	action.setEleicao(new Eleicao());
    	assertTrue(action.getEleicao() instanceof Eleicao);
    	action.getEleicaos();
    }
}