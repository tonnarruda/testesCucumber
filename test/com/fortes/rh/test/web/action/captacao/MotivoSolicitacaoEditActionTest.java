package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.geral.ConfiguracaoLimiteColaboradorManager;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;
import com.fortes.rh.web.action.captacao.MotivoSolicitacaoEditAction;

public class MotivoSolicitacaoEditActionTest
{
	private MotivoSolicitacaoEditAction action;
	private MotivoSolicitacaoManager manager;
	private ConfiguracaoLimiteColaboradorManager configuracaoLimiteColaboradorManager;
	
	@Before
    public void setUp() throws Exception
    {
        action = new MotivoSolicitacaoEditAction();
        
        manager = mock(MotivoSolicitacaoManager.class);
        action.setMotivoSolicitacaoManager(manager);
        
        configuracaoLimiteColaboradorManager = mock(ConfiguracaoLimiteColaboradorManager.class);
        action.setConfiguracaoLimiteColaboradorManager(configuracaoLimiteColaboradorManager);
        
    }

	@After
    public void tearDown() throws Exception
    {
        manager = null;
        action = null;
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    @Test
    public void testPrepareInsert() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	action.setEmpresaSistema(empresa);
    	
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	when(manager.findById(motivoSolicitacao.getId())).thenReturn(motivoSolicitacao);
    	when(configuracaoLimiteColaboradorManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<ConfiguracaoLimiteColaborador>());

    	assertEquals(action.prepareInsert(), "success");
    }

    @Test
    public void testPrepareUpdate() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	action.setEmpresaSistema(empresa);
    	
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	when(manager.findById(motivoSolicitacao.getId())).thenReturn(motivoSolicitacao);
    	when(configuracaoLimiteColaboradorManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(new ArrayList<ConfiguracaoLimiteColaborador>());

    	assertEquals(action.prepareUpdate(), "success");
    }

    @Test
    public void testInsert() throws Exception
    {
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	assertEquals(action.insert(), "success");
    }

    @Test
    public void testUpdate() throws Exception
    {
    	MotivoSolicitacao motivoSolicitacao = MotivoSolicitacaoFactory.getEntity();
    	motivoSolicitacao.setId(1L);

    	action.setMotivoSolicitacao(motivoSolicitacao);

    	assertEquals(action.update(), "success");
    }

    @Test
    public void testGets() throws Exception
    {
    	@SuppressWarnings("unused")
		MotivoSolicitacao motivoSolicitacao = action.getMotivoSolicitacao();
    	@SuppressWarnings("unused")
		Object object = action.getModel();
    }
}