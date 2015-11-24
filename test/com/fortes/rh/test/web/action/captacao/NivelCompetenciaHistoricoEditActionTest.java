package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;
import com.fortes.rh.web.action.captacao.NivelCompetenciaHistoricoEditAction;

public class NivelCompetenciaHistoricoEditActionTest extends MockObjectTestCase
{
	private NivelCompetenciaHistoricoEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(NivelCompetenciaHistoricoManager.class);
		action = new NivelCompetenciaHistoricoEditAction();
		action.setNivelCompetenciaHistoricoManager((NivelCompetenciaHistoricoManager) manager.proxy());

		action.setNivelCompetenciaHistorico(new NivelCompetenciaHistorico());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		setEmpresaSistema();
		
		manager.expects(once()).method("find").will(returnValue(new ArrayList<NivelCompetenciaHistorico>()));
		assertEquals("success", action.list());
		assertNotNull(action.getNivelCompetenciaHistoricos());
	}

	public void testDelete() throws Exception
	{
		setEmpresaSistema();
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		action.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);

		manager.expects(once()).method("removeNivelConfiguracaoHistorico");
		manager.expects(once()).method("find").will(returnValue(new ArrayList<NivelCompetenciaHistorico>()));
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		setEmpresaSistema();
		
		NivelCompetenciaHistorico nivelCompetenciaHistorico = NivelCompetenciaHistoricoFactory.getEntity(1L);
		action.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
		
		manager.expects(once()).method("removeNivelConfiguracaoHistorico").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		manager.expects(once()).method("find").will(returnValue(new ArrayList<NivelCompetenciaHistorico>()));
		assertEquals("success", action.delete());
	}

	private Empresa setEmpresaSistema() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
		return empresa;
	}

	public void testGetSet() throws Exception
	{
		action.setNivelCompetenciaHistorico(null);

		assertNotNull(action.getNivelCompetenciaHistorico());
		assertTrue(action.getNivelCompetenciaHistorico() instanceof NivelCompetenciaHistorico);
	}
	
	
}
