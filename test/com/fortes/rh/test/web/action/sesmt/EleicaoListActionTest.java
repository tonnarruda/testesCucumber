package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.web.action.sesmt.EleicaoListAction;

public class EleicaoListActionTest extends MockObjectTestCase
{
	private EleicaoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new Mock(EleicaoManager.class);
        action = new EleicaoListAction();
        action.setEleicaoManager((EleicaoManager) manager.proxy());
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
    	manager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Eleicao>()));
    	assertEquals(action.list(), "success");
    	assertNotNull(action.getEleicaos());
    }
    
    public void testDelete() throws Exception
    {
    	action.setEleicao(EleicaoFactory.getEntity(1L));
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));

    	manager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Eleicao>()));
    	manager.expects(once()).method("removeCascade").with(ANYTHING).isVoid();
    	assertEquals(action.delete(), "success");
    }
    
    public void testDeleteException() throws Exception
    {
    	action.setEleicao(EleicaoFactory.getEntity(1L));
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	
    	manager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(new ArrayList<Eleicao>()));
    	manager.expects(once()).method("removeCascade").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(null,""))));;
    	assertEquals(action.delete(), "success");
    }

    public void testGetSet() throws Exception
    {
    	assertNotNull(action.getEleicao());

    	Eleicao eleicaol = new Eleicao();
    	action.setEleicao(eleicaol);
    	assertTrue(action.getEleicao() instanceof Eleicao);

    }
}