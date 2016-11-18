package com.fortes.rh.test.business.avaliacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.PeriodoExperienciaManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.test.factory.avaliacao.PeriodoExperienciaFactory;


public class PeriodoExperienciaManagerTest_Junit4 
{
	private PeriodoExperienciaManagerImpl periodoExperienciaManager = new PeriodoExperienciaManagerImpl();
	private PeriodoExperienciaDao periodoExperienciaDao;
	
	@Before
	public void setUp(){
		periodoExperienciaDao = mock(PeriodoExperienciaDao.class);
		periodoExperienciaManager.setDao(periodoExperienciaDao);
	}
	
	@Test
	public void testFindPeriodosAtivosAndPeriodosConfiguradosParaColaborador(){
		Long empresaId = 2L; 
		Long colaboradorId = 3L;
		
		Collection<PeriodoExperiencia> periodos = PeriodoExperienciaFactory.getCollection();
		when(periodoExperienciaDao.findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(empresaId, colaboradorId)).thenReturn(periodos);
		assertEquals(periodos, periodoExperienciaManager.findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(empresaId, colaboradorId));
	}
	
	@Test
	public void testFindPeriodosAtivosAndPeriodoDaAvaliacaoId(){
		Long empresaId = 1L;
		Long avaliacaoId = 2L;
		Collection<PeriodoExperiencia> periodos = PeriodoExperienciaFactory.getCollection();
		when(periodoExperienciaDao.findPeriodosAtivosAndPeriodoDaAvaliacaoId(empresaId, avaliacaoId)).thenReturn(periodos);
		assertEquals(periodos, periodoExperienciaManager.findPeriodosAtivosAndPeriodoDaAvaliacaoId(empresaId, avaliacaoId));
	}
}