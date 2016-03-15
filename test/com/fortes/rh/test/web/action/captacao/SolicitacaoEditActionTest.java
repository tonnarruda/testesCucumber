package com.fortes.rh.test.web.action.captacao;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
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
        MockSecurityUtil.verifyRole = false;
    }

	public void testInsert()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		
		Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();
		action.setBairrosCheck(new String[]{"1"});
		action.setSolicitacao(solicitacao);
		solicitacaoManager.expects(once()).method("save").with(eq(solicitacao), ANYTHING, ANYTHING);
		
		Exception exception = null;
		try
		{
			action.insert();
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
		solicitacao.setStatus(StatusAprovacaoSolicitacao.APROVADO);
		
		action.setBairrosCheck(new String[]{"1"});
		action.setSolicitacao(solicitacao);
		solicitacaoManager.expects(once()).method("updateSolicitacao").with(eq(solicitacao), ANYTHING, ANYTHING, ANYTHING).isVoid();
		
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