package com.fortes.rh.test.business.captacao;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fortes.rh.business.captacao.ConhecimentoManagerImpl;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.util.SpringUtil;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringUtil.class)
public class ConhecimentoManagerTest_Junit4 {

	private ConhecimentoManagerImpl conhecimentoManager = new ConhecimentoManagerImpl();
	private CursoManager cursoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	private ConhecimentoDao conhecimentoDao;

	@Before
	public void setUp() throws Exception {
		cursoManager = mock(CursoManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		criterioAvaliacaoCompetenciaManager = mock(CriterioAvaliacaoCompetenciaManager.class);
		conhecimentoDao = mock(ConhecimentoDao.class);
		
		conhecimentoManager.setCriterioAvaliacaoCompetenciaManager(criterioAvaliacaoCompetenciaManager);
		conhecimentoManager.setAreaOrganizacionalManager(areaOrganizacionalManager);
		conhecimentoManager.setDao(conhecimentoDao);
		
		PowerMockito.mockStatic(SpringUtil.class);
		BDDMockito.given(SpringUtil.getBean("cursoManager")).willReturn(cursoManager);
	}

	@Test
	public void testDelete() throws Exception {
		
		doNothing().when(criterioAvaliacaoCompetenciaManager).removeByCompetencia(1L, TipoCompetencia.CONHECIMENTO, null);
		doNothing().when(areaOrganizacionalManager).removeVinculoComConhecimento(1L);
		doNothing().when(cursoManager).removeVinculoComConhecimento(1L);
		doNothing().when(conhecimentoDao).remove(1L);
		
		conhecimentoManager.delete(1l);

		verify(criterioAvaliacaoCompetenciaManager, times(1)).removeByCompetencia(1L, TipoCompetencia.CONHECIMENTO, null);
		verify(areaOrganizacionalManager, times(1)).removeVinculoComConhecimento(1L);
		verify(cursoManager, times(1)).removeVinculoComConhecimento(1L);;
		verify(conhecimentoDao, times(1)).remove(1L);;

	}
}
