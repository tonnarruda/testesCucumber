package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.test.factory.sesmt.EtapaProcessoEleitoralFactory;
import com.fortes.rh.web.action.sesmt.EtapaProcessoEleitoralEditAction;

public class EtapaProcessoEleitoralEditActionTest extends MockObjectTestCase
{
	private EtapaProcessoEleitoralEditAction action;
	private Mock etapaProcessoEleitoralManager;
	private Mock eleicaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        etapaProcessoEleitoralManager = new Mock(EtapaProcessoEleitoralManager.class);
        action = new EtapaProcessoEleitoralEditAction();
        action.setEtapaProcessoEleitoralManager((EtapaProcessoEleitoralManager) etapaProcessoEleitoralManager.proxy());
        
        eleicaoManager = mock(EleicaoManager.class);
        action.setEleicaoManager((EleicaoManager) eleicaoManager.proxy());
        
        action.setEleicao(EleicaoFactory.getEntity(1L));
    }

    protected void tearDown() throws Exception
    {
        etapaProcessoEleitoralManager = null;
        action = null;
        super.tearDown();
    }

    public void testPrepareInsert() throws Exception
    {
    	assertEquals("success",action.prepareInsert());
    }
    public void testPrepareUpdate() throws Exception
    {
    	Long id = 1L;
    	EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity(id);
    	etapaProcessoEleitoralManager.expects(once()).method("findById").with(eq(id)).will(returnValue(etapaProcessoEleitoral));

    	action.setEtapaProcessoEleitoral(etapaProcessoEleitoral);
    	assertEquals("success",action.prepareUpdate());
    }

    public void testInsert() throws Exception
    {
    	EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity();
    	etapaProcessoEleitoralManager.expects(once()).method("save").with(eq(etapaProcessoEleitoral)).will(returnValue(etapaProcessoEleitoral));
    	action.setEtapaProcessoEleitoral(etapaProcessoEleitoral);

    	assertEquals("success",action.insert());
    }

    public void testUpdate() throws Exception
    {
    	EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity();
    	etapaProcessoEleitoralManager.expects(once()).method("update").with(eq(etapaProcessoEleitoral)).isVoid();
    	action.setEtapaProcessoEleitoral(etapaProcessoEleitoral);

    	assertEquals("success",action.update());
    }

    public void testGetSet() throws Exception
    {
    	EtapaProcessoEleitoral etapaProcessoEleitoral = new EtapaProcessoEleitoral();

    	action.setEtapaProcessoEleitoral(etapaProcessoEleitoral);
    	assertTrue(action.getEtapaProcessoEleitoral() instanceof EtapaProcessoEleitoral);

    	action.getAntesOuDepois();
    	action.setAntesOuDepois("");
    	action.setEleicao(new Eleicao());
    	action.getEleicao();
    }

    public void testList() throws Exception
    {
    	etapaProcessoEleitoralManager.expects(once()).method("findAllSelect").with(eq(1L),eq(1L)).will(returnValue(new ArrayList<EtapaProcessoEleitoral>()));
    	//list
    	eleicaoManager.expects(once()).method("findByIdProjection").with(eq(1L)).will(returnValue(EleicaoFactory.getEntity(1L)));
    	
    	action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    	assertEquals("success", action.list());
    }

    public void testDelete() throws Exception
    {
    	Long id = 1L;
    	EtapaProcessoEleitoral etapaProcessoEleitoral = EtapaProcessoEleitoralFactory.getEntity(1L);
    	etapaProcessoEleitoralManager.expects(once()).method("remove").with(eq(new Long[]{id})).isVoid();

    	action.setEtapaProcessoEleitoral(etapaProcessoEleitoral);
    	assertEquals("success", action.delete());
    }
    
	public void testGerarEtapasModelo() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	etapaProcessoEleitoralManager.expects(atLeastOnce()).method("gerarEtapasModelo").with(eq(empresa)).isVoid();
		action.setEmpresaSistema(empresa);
		assertEquals("success", action.gerarEtapasModelo());	
	}
}