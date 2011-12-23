package com.fortes.rh.test.web.action.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.web.action.geral.OcorrenciaEditAction;

public class OcorrenciaEditActionTest extends MockObjectTestCase
{
	private OcorrenciaEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(OcorrenciaManager.class);
		action = new OcorrenciaEditAction();
		action.setOcorrenciaManager((OcorrenciaManager) manager.proxy());
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}
	
	public void testPrepareInsert() throws Exception
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity(1L);
		action.setOcorrencia(ocorrencia);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		empresa.setAcIntegra(true);
		action.setEmpresaSistema(empresa);
		
		manager.expects(once()).method("findById").with(ANYTHING).will(returnValue(new Ocorrencia()));
		assertEquals("success", action.prepareInsert());
		assertNotNull(action.getOcorrencia());
		assertTrue(action.isEmpresaIntegradaComAC());
	}
}