package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.CursoManagerImpl;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;

public class CursoManagerTest_Junit4
{
	private CursoManagerImpl cursoManager = new CursoManagerImpl();
	private CursoDao cursoDao;

	@Before
	public void setUp() throws Exception
	{
		cursoDao = mock(CursoDao.class);
		cursoManager.setDao(cursoDao);
	}

	@Test
	public void testClonarParaAMesmaEmpresa(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Curso curso = CursoFactory.getEntity(1L);
		when(cursoDao.findById(curso.getId())).thenReturn(curso);
		Exception exception = null;
    	try
		{
    		cursoManager.clonar(curso.getId(), empresa.getId(), null, "");
		}
		catch (Exception e)
		{
			exception = e;
		}
    	assertNull(exception);
	}
	
	@Test
	public void testClonarParaOutraEmpresa(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Curso curso = CursoFactory.getEntity(1L);
		
		when(cursoDao.findById(curso.getId())).thenReturn(curso);
		
		Exception exception = null;

		try
		{
    		cursoManager.clonar(curso.getId(), empresa.getId(), new Long[]{2L}, "Novo Título do Clone");
		}
		
		catch (Exception e)
		{
			exception = e;
		}
    	assertNull(exception);
	}
}