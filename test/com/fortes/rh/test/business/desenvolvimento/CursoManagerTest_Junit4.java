package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.CursoManagerImpl;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;

public class CursoManagerTest_Junit4 {
	private CursoManagerImpl cursoManager = new CursoManagerImpl();
	private CursoDao cursoDao;

	@Before
	public void setUp() throws Exception {
		cursoDao = mock(CursoDao.class);
		cursoManager.setDao(cursoDao);
	}

	@Test
	public void testClonarParaAMesmaEmpresa() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Curso curso = CursoFactory.getEntity(1L);
		when(cursoDao.findById(curso.getId())).thenReturn(curso);
		Exception exception = null;
		try {
			cursoManager.clonar(curso.getId(), empresa.getId(), null, "");
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	@Test
	public void testClonarParaOutraEmpresa() {
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Curso curso = CursoFactory.getEntity(1L);

		when(cursoDao.findById(curso.getId())).thenReturn(curso);

		Exception exception = null;

		try {
			cursoManager.clonar(curso.getId(), empresa.getId(), new Long[] { 2L }, "Novo Título do Clone");
		}

		catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	@Test
	public void testExisteTurmaRealizada() {
		Long cursoId = 2L;
		when(cursoDao.existeTurmaRealizada(cursoId)).thenReturn(true);
		assertTrue(cursoManager.existeTurmaRealizada(cursoId));
		verify(cursoDao, only()).existeTurmaRealizada(cursoId);
	}

	@Test
	public void testRemoveVinculoConhecimentoComCursos() throws Exception {
		doNothing().when(cursoDao).removeVinculoComConhecimento(1l);
		cursoManager.removeVinculoComConhecimento(1l);
		verify(cursoDao).removeVinculoComConhecimento(1L);
	}

}