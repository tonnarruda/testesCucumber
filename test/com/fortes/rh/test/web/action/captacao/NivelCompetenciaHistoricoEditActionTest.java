package com.fortes.rh.test.web.action.captacao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.web.action.captacao.NivelCompetenciaHistoricoEditAction;

public class NivelCompetenciaHistoricoEditActionTest
{
	private NivelCompetenciaHistoricoEditAction action;
	private NivelCompetenciaHistoricoManager manager;

	@Before
	public void setUp() throws Exception
	{
		action = new NivelCompetenciaHistoricoEditAction();

		manager = mock(NivelCompetenciaHistoricoManager.class);
		action.setNivelCompetenciaHistoricoManager(manager);

		action.setNivelCompetenciaHistorico(new NivelCompetenciaHistorico());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	@Test
	public void testList() throws Exception
	{
		when(manager.find(new String[]{"empresa.id"}, new Object[]{1L})).thenReturn(new ArrayList<NivelCompetenciaHistorico>());
		assertEquals("success", action.list());
		assertNotNull(action.getNivelCompetenciaHistoricos());
	}

	@Test
	public void testDelete() throws Exception
	{
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		action.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);

		when(manager.find(new String[]{"empresa.id"}, new Object[]{1L})).thenReturn(new ArrayList<NivelCompetenciaHistorico>());
		assertEquals("success", action.delete());
	}
	
	@Test
	public void testDeleteException() throws Exception
	{
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		action.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
	
		when(manager.find(new String[]{"empresa.id"}, new Object[]{1L})).thenReturn(new ArrayList<NivelCompetenciaHistorico>());
		doThrow(Exception.class).when(manager).removeNivelConfiguracaoHistorico(1L);
		
		assertEquals("success", action.delete());
		assertEquals("Não foi possível excluir o histórico dos níveis de competência.", action.getActionErrors().toArray()[0]);
	}

	@Test
	public void testGetSet() throws Exception
	{
		action.setNivelCompetenciaHistorico(null);

		assertNotNull(action.getNivelCompetenciaHistorico());
		assertTrue(action.getNivelCompetenciaHistorico() instanceof NivelCompetenciaHistorico);
	}
}
