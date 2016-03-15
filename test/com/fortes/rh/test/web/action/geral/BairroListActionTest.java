package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.BairroListAction;

public class BairroListActionTest extends MockObjectTestCase
{
	private BairroListAction action;
	private Mock manager;
	private Mock estadoManager;
	private Mock cidadeManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new BairroListAction();
        manager = new Mock(BairroManager.class);
        estadoManager = new Mock(EstadoManager.class);
        cidadeManager = new Mock(CidadeManager.class);

        action.setBairroManager((BairroManager) manager.proxy());
        action.setEstadoManager((EstadoManager) estadoManager.proxy());
        action.setCidadeManager((CidadeManager) cidadeManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testList() throws Exception
    {
    	Estado estado = new Estado();
    	estado.setId(1L);
    	action.setEstado(estado);

    	manager.expects(once()).method("getCount").will(returnValue(1));
    	manager.expects(once()).method("list").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Bairro>()));
    	estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Estado>()));
    	cidadeManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cidade>()));

    	assertEquals(action.list(), "success");

    	manager.verify();
    }

    public void testDelete() throws Exception
    {
    	Bairro bairro = new Bairro();
    	bairro.setId(1L);
    	action.setBairro(bairro);

    	Estado estado = new Estado();
    	estado.setId(1L);
    	action.setEstado(estado);

    	manager.expects(once()).method("getCount").will(returnValue(1));
    	manager.expects(once()).method("list").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Bairro>()));
    	estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Estado>()));
    	cidadeManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cidade>()));
    	manager.expects(once()).method("remove").with(ANYTHING);

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionMessages().isEmpty());

    	manager.expects(once()).method("getCount").will(returnValue(1));
    	manager.expects(once()).method("list").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Bairro>()));
    	estadoManager.expects(once()).method("findAll").with(ANYTHING).will(returnValue(new ArrayList<Estado>()));
    	cidadeManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(new ArrayList<Cidade>()));
    	manager.expects(once()).method("remove").with(ANYTHING).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(bairro.getId(),""))));

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionErrors().isEmpty());
    }

    public void testGets() throws Exception
    {
    	action.getBairro();
    	action.getBairros();

    	action.getCidades();
    	action.getEstado();
    	action.getEstados();
    }
}
