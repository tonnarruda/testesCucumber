package com.fortes.rh.test.web.action.captacao;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.captacao.SolicitacaoEditAction;

public class SolicitacaoEditActionTest extends MockObjectTestCase
{
	private SolicitacaoEditAction action;
	private Mock manager;
	private Mock candidatoSolicitacaoManager;
	private Mock cargoManager;
	private Mock empresaManager;
	private Mock solicitacaoManager;
	

    protected void setUp() throws Exception
    {
        action = new SolicitacaoEditAction();
       
        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        cargoManager = new Mock(CargoManager.class);        
        empresaManager = new Mock(EmpresaManager.class);
        solicitacaoManager = new Mock(SolicitacaoManager.class);
        action.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());
        action.setCandidatoSolicitacaoManager((CandidatoSolicitacaoManager) candidatoSolicitacaoManager.proxy());
        action.setCargoManager((CargoManager)cargoManager.proxy());
        action.setEmpresaManager((EmpresaManager)empresaManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    }

    protected void tearDown() throws Exception
    {
        candidatoSolicitacaoManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
    }

	public void testInsert()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		action.setBairrosCheck(new String[]{"1"});
		action.setSolicitacao(solicitacao);
		solicitacaoManager.expects(once()).method("save").with(eq(solicitacao), ANYTHING);
		
		Exception exception = null;
		try
		{
			action.insert();
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}
    

	
	public void testUpdateNaoValidado()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setLiberada(false);
		solicitacao.setCidade(CidadeFactory.getEntity());
		solicitacao.setAvaliacao(AvaliacaoFactory.getEntity());
		
		Solicitacao solicitacaoAux = SolicitacaoFactory.getSolicitacao();
		solicitacaoAux.setLiberada(false);
		action.setBairrosCheck(new String[]{"1"});
		action.setSolicitacao(solicitacao);
		solicitacaoManager.expects(once()).method("findByIdProjectionForUpdate").with(eq(solicitacao.getId())).will(returnValue(solicitacaoAux));
		solicitacaoManager.expects(once()).method("update").with(eq(solicitacao));
		
		Exception exception = null;
		try
		{
			action.update();
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}
	
	public void testUpdate()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);

		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao(1L);
		solicitacao.setCidade(CidadeFactory.getEntity());
		solicitacao.setAvaliacao(AvaliacaoFactory.getEntity());
		
		Solicitacao solicitacaoAux = SolicitacaoFactory.getSolicitacao();
		solicitacaoAux.setLiberada(false);
		action.setBairrosCheck(new String[]{"1"});
		action.setSolicitacao(solicitacao);
		MockSecurityUtil.verifyRole = true;
		solicitacaoManager.expects(once()).method("findByIdProjectionForUpdate").with(eq(solicitacao.getId())).will(returnValue(solicitacaoAux));
		solicitacaoManager.expects(once()).method("emailParaSolicitante").with(ANYTHING, eq(solicitacao), ANYTHING);
		solicitacaoManager.expects(once()).method("update").with(eq(solicitacao));
		
		System.out.println(MockSecurityUtil.verifyRole);
		
		Exception exception = null;
		try
		{
			action.update();
		}
		catch (Exception e)
		{
			exception = e;
		}
		assertNull(exception);
	}
	
}