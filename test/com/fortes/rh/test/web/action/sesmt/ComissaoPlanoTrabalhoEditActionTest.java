package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComissaoMembroManager;
import com.fortes.rh.business.sesmt.ComissaoPlanoTrabalhoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPlanoTrabalhoFactory;
import com.fortes.rh.web.action.sesmt.ComissaoPlanoTrabalhoEditAction;

public class ComissaoPlanoTrabalhoEditActionTest extends MockObjectTestCase
{
	private ComissaoPlanoTrabalhoEditAction action;
	private Mock manager;
	private Mock comissaoMembroManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(ComissaoPlanoTrabalhoManager.class);
        action = new ComissaoPlanoTrabalhoEditAction();
        action.setComissaoPlanoTrabalhoManager((ComissaoPlanoTrabalhoManager) manager.proxy());

        comissaoMembroManager = new Mock(ComissaoMembroManager.class);
        action.setComissaoMembroManager((ComissaoMembroManager)comissaoMembroManager.proxy());
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
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	manager.expects(once()).method("findByComissao").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ComissaoPlanoTrabalho>()));
    	comissaoMembroManager.expects(once()).method("findColaboradorByComissao").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
    	assertEquals(action.list(), "success");
    	assertNotNull(action.getComissaoPlanoTrabalhos());
    }

    public void testDelete() throws Exception
    {
    	action.setComissaoPlanoTrabalho(ComissaoPlanoTrabalhoFactory.getEntity(1L));

    	manager.expects(once()).method("remove").with(ANYTHING).isVoid();
    	assertEquals(action.delete(), "success");
    }

    public void testPrepare()
    {
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	action.setComissaoPlanoTrabalho(comissaoPlanoTrabalho);
    	manager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(comissaoPlanoTrabalho));

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
    	ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
    	Colaborador responsavel = ColaboradorFactory.getEntity();
    	comissaoPlanoTrabalho.setResponsavel(responsavel);
    	action.setComissaoPlanoTrabalho(comissaoPlanoTrabalho);
    	manager.expects(once()).method("save").with(eq(comissaoPlanoTrabalho)).will(returnValue(comissaoPlanoTrabalho));

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
    	ComissaoPlanoTrabalho comissaoPlanoTrabalho = ComissaoPlanoTrabalhoFactory.getEntity(1L);
    	Colaborador responsavel = ColaboradorFactory.getEntity();
    	comissaoPlanoTrabalho.setResponsavel(responsavel);
    	action.setComissaoPlanoTrabalho(comissaoPlanoTrabalho);
    	manager.expects(once()).method("update").with(eq(comissaoPlanoTrabalho)).isVoid();

    	String ret = "";
    	try {
    		ret = action.update();
    	}catch (Exception e)
    	{
    	}
    	assertEquals("success", ret);
    }

    public void testImprimirPlanoTrabalho()
    {
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	Comissao comissao = ComissaoFactory.getEntity(1L);
    	action.setComissao(comissao);
    	manager.expects(once()).method("findImprimirPlanoTrabalho").with(eq(comissao.getId()), ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ComissaoPlanoTrabalho>()));

    	String ret = "";
    	try {
    		ret = action.imprimirPlanoTrabalho();
    	}catch (Exception e)
    	{
    	}
    	assertEquals(ret,"success");

    	// Exception

    	manager.expects(once()).method("findImprimirPlanoTrabalho").with(eq(comissao.getId()), ANYTHING, ANYTHING, ANYTHING).will(throwException(new ColecaoVaziaException("")));
    	manager.expects(once()).method("findByComissao").with(ANYTHING, ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<ComissaoPlanoTrabalho>()));
    	comissaoMembroManager.expects(once()).method("findColaboradorByComissao").with(ANYTHING).will(returnValue(new ArrayList<Colaborador>()));
    	ret = "";
    	try {
    		ret = action.imprimirPlanoTrabalho();
    	}catch (Exception e)
    	{
    	}
    	assertEquals(ret,"input");

    }

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getComissaoPlanoTrabalho());
    	ComissaoPlanoTrabalho eleicaol = new ComissaoPlanoTrabalho();
    	action.setComissaoPlanoTrabalho(eleicaol);
    	assertTrue(action.getComissaoPlanoTrabalho() instanceof ComissaoPlanoTrabalho);

    	action.getComissao();
    	Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
    	action.setColaboradors(colaboradors);
    	assertEquals(colaboradors,action.getColaboradors());
    }
}