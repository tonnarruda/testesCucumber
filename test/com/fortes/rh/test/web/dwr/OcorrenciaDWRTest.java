package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.web.dwr.OcorrenciaDWR;

@SuppressWarnings("unchecked")
public class OcorrenciaDWRTest extends MockObjectTestCase
{
	private OcorrenciaDWR ocorrenciaDWR;
	private Mock ocorrenciaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		ocorrenciaDWR = new OcorrenciaDWR();

		ocorrenciaManager = new Mock(OcorrenciaManager.class);
		ocorrenciaDWR.setOcorrenciaManager((OcorrenciaManager) ocorrenciaManager.proxy());
	}

	public void testGetByEmpresas() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Long[] empresaIds = new Long[]{1L};
		
		Ocorrencia ocorrenciaA = OcorrenciaFactory.getEntity(1L);
		ocorrenciaA.setDescricao("OcorrenciaA");
		ocorrenciaA.setEmpresa(empresa);

		Ocorrencia ocorrenciaB = OcorrenciaFactory.getEntity(2L);
		ocorrenciaB.setDescricao("OcorrenciaB");
		ocorrenciaB.setEmpresa(empresa);

		Ocorrencia ocorrenciaC = OcorrenciaFactory.getEntity(3L);
		ocorrenciaC.setDescricao("OcorrenciaC");
		ocorrenciaC.setEmpresa(empresa);
		
		Collection<Ocorrencia> ocorrenciaCollection = new ArrayList<Ocorrencia>();
		ocorrenciaCollection.add(ocorrenciaA);
		ocorrenciaCollection.add(ocorrenciaB);
		ocorrenciaCollection.add(ocorrenciaC);
		
		ocorrenciaManager.expects(once()).method("findAllSelect").with(eq(empresaIds)).will(returnValue(ocorrenciaCollection));
		
		Map<Object, Object> retorno = ocorrenciaDWR.getByEmpresas(empresa.getId(), empresaIds);
		
		assertEquals(3, retorno.size());
		assertEquals(3, retorno.size());
	}

	
}
