package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.MotivoDemissaoManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MotivoDemissaoFactory;
import com.fortes.rh.web.action.geral.ColaboradorDesligaAction;

public class ColaboradorDesligaActionTest
{
	private ColaboradorDesligaAction action;
	private ColaboradorManager colaboradorManager;
	private MotivoDemissaoManager motivoDemissaoManager;

	@Before
	public void setUp () throws Exception
	{
		action = new ColaboradorDesligaAction();

		colaboradorManager = mock(ColaboradorManager.class);
		motivoDemissaoManager = mock(MotivoDemissaoManager.class);
		
		action.setColaboradorManager(colaboradorManager);
		action.setMotivoDemissaoManager(motivoDemissaoManager);
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	@Test
	public void testPrepareUpdate() throws Exception
    {
		MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setMotivoDemissao(motivoDemissao);
		colaborador.setDemissaoGerouSubstituicao('S');
		action.setColaborador(colaborador);
		
		when(motivoDemissaoManager.findMotivoDemissao(null, null, action.getEmpresaSistema().getId(), null, true)).thenReturn(Arrays.asList(motivoDemissao));
		when(colaboradorManager.findColaboradorById(colaborador.getId())).thenReturn(colaborador);
				
    	assertEquals(action.prepareUpdate(), "success");
    }
	
	@Test
	public void testPrepareDesliga() throws Exception
    {
		MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity(1L);
		when(motivoDemissaoManager.findMotivoDemissao(null, null, action.getEmpresaSistema().getId(), null, true)).thenReturn(Arrays.asList(motivoDemissao));
				
    	assertEquals(action.prepareDesliga(), "success");
    }
	
	@Test
	public void testVisualizarSolicitacaoDesligamento() throws Exception
    {
		MotivoDemissao motivoDemissao = MotivoDemissaoFactory.getEntity(1L);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		action.setColaborador(colaborador);
		
		when(colaboradorManager.findColaboradorByIdProjection(colaborador.getId())).thenReturn(colaborador);
		when(motivoDemissaoManager.findAllSelect(action.getEmpresaSistema().getId())).thenReturn(Arrays.asList(motivoDemissao));
				
    	assertEquals(action.visualizarSolicitacaoDesligamento(), "success");
    }
}