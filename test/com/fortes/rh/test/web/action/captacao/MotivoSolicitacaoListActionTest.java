package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.web.action.captacao.MotivoSolicitacaoListAction;

public class MotivoSolicitacaoListActionTest extends MockObjectTestCase
{
	private MotivoSolicitacaoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new MotivoSolicitacaoListAction();
        manager = new Mock(MotivoSolicitacaoManager.class);
        action.setMotivoSolicitacaoManager((MotivoSolicitacaoManager) manager.proxy());
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
    	MotivoSolicitacao ms1 = MotivoSolicitacaoFactory.getEntity();
    	ms1.setId(1L);

    	MotivoSolicitacao ms2 = MotivoSolicitacaoFactory.getEntity();
    	ms2.setId(2L);

    	Collection<MotivoSolicitacao> motivoSolicitacaos = new ArrayList<MotivoSolicitacao>();
    	motivoSolicitacaos.add(ms1);
    	motivoSolicitacaos.add(ms2);

    	manager.expects(once()).method("findAll").will(returnValue(motivoSolicitacaos));

    	assertEquals(action.list(), "success");
    	manager.verify();
    }

    public void testDelete() throws Exception
    {
    	MotivoSolicitacao ms = MotivoSolicitacaoFactory.getEntity();
    	ms.setId(1L);

    	action.setMotivoSolicitacao(ms);

    	manager.expects(once()).method("remove").with(eq(ms.getId()));

    	assertEquals("success", action.delete());
    }

    public void testGets() throws Exception
    {
    	action.getMotivoSolicitacao();

    	action.getMotivoSolicitacaos();
    }
}