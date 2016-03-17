package com.fortes.rh.test.web.action.geral;

import java.util.Arrays;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MotivoDemissaoFactory;
import com.fortes.rh.web.action.geral.ColaboradorDesligaAction;

public class ColaboradorDesligaActionTest extends MockObjectTestCase
{
	private ColaboradorDesligaAction action;
	private Mock colaboradorManager;
	private Mock motivoDemissaoManager;

	protected void setUp () throws Exception
	{
		action = new ColaboradorDesligaAction();

		colaboradorManager = new Mock(ColaboradorManager.class);
		motivoDemissaoManager = new Mock(MotivoDemissaoManager.class);
		
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setMotivoDemissaoManager((MotivoDemissaoManager)motivoDemissaoManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	protected void tearDown() throws Exception
    {
        colaboradorManager = null;
        action = null;
        Mockit.restoreAllOriginalDefinitions();
        super.tearDown();
    }

	public void testPrepareUpdate() throws Exception
    {
		MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setMotivoDemissao(motivoDemissao);
		colaborador.setDemissaoGerouSubstituicao('S');
		
		action.setColaborador(colaborador);
		
		motivoDemissaoManager.expects(once()).method("findAllSelect").with(eq(action.getEmpresaSistema().getId())).will(returnValue(Arrays.asList(motivoDemissao)));
		colaboradorManager.expects(once()).method("findColaboradorById").with(eq(colaborador.getId())).will(returnValue(colaborador));
				
    	assertEquals(action.prepareUpdate(), "success");
    }
}