package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.dwr.EmpresaDWR;

public class EmpresaDWRTest
{
	private EmpresaDWR empresaDWR;
	private EmpresaManager empresaManager;

	@Before
	public void setUp()
	{
		empresaDWR = new EmpresaDWR();
		empresaManager = mock(EmpresaManager.class);
		empresaDWR.setEmpresaManager(empresaManager);
	}
	
	@Test
	public void testFindDistinctEmpresasByAvaliacaoDesempenho()
	{
		Collection<Empresa> empresas= EmpresaFactory.getEmpresas(2l);
		Long avaliacaoDesempenhoId=1l;
		
		when(empresaManager.findDistinctEmpresasByAvaliacaoDesempenho(avaliacaoDesempenhoId, null)).thenReturn(empresas);	
		
		Collection<Empresa> resultado = empresaDWR.findDistinctEmpresasByAvaliacaoDesempenho(avaliacaoDesempenhoId, null);
		
		assertEquals(2,resultado.size());
	}
	
}
