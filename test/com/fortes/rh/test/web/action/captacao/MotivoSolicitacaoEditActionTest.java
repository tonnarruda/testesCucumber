package com.fortes.rh.test.web.action.captacao;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.web.action.captacao.MotivoSolicitacaoEditAction;

public class MotivoSolicitacaoEditActionTest extends MockObjectTestCase
{
	private MotivoSolicitacaoEditAction action;
	private Mock manager;
	private Mock empresaManager;
	
    protected void setUp() throws Exception
    {
        super.setUp();
        action = new MotivoSolicitacaoEditAction();
        manager = new Mock(MotivoSolicitacaoManager.class);
        action.setMotivoSolicitacaoManager((MotivoSolicitacaoManager) manager.proxy());

        empresaManager = new Mock(EmpresaManager.class);
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
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

    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	action.setEmpresaSistema(empresa);
    	
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	manager.expects(once()).method("findById").with(eq(motivoSolicitacao.getId())).will(returnValue(motivoSolicitacao));
    	empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));

    	assertEquals(action.prepareInsert(), "success");
    }

    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	action.setEmpresaSistema(empresa);
    	
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	manager.expects(once()).method("findById").with(eq(motivoSolicitacao.getId())).will(returnValue(motivoSolicitacao));
    	empresaManager.expects(once()).method("findByIdProjection").with(eq(empresa.getId())).will(returnValue(empresa));

    	assertEquals(action.prepareUpdate(), "success");
    }

    public void testInsert() throws Exception
    {
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	manager.expects(once()).method("save").with(eq(motivoSolicitacao));

    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	manager.expects(once()).method("update").with(eq(motivoSolicitacao));

    	assertEquals(action.update(), "success");
    }

    public void testGets() throws Exception
    {
    	@SuppressWarnings("unused")
		MotivoSolicitacao motivoSolicitacao = action.getMotivoSolicitacao();
    	@SuppressWarnings("unused")
		Object object = action.getModel();
    }
}