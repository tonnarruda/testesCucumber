package com.fortes.rh.test.business.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalFactory;

public class SituacaoGeradoraDoencaProfissionalManagerTest
{
	private SituacaoGeradoraDoencaProfissionalManagerImpl situacaoGeradoraDoencaProfissionalManager = new SituacaoGeradoraDoencaProfissionalManagerImpl();
	private SituacaoGeradoraDoencaProfissionalDao situacaoGeradoraDoencaProfissionalDao;
	
	@Before
	public void setUp() throws Exception
    {
        situacaoGeradoraDoencaProfissionalDao = mock(SituacaoGeradoraDoencaProfissionalDao.class);
        situacaoGeradoraDoencaProfissionalManager.setDao(situacaoGeradoraDoencaProfissionalDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<SituacaoGeradoraDoencaProfissional> situacaoGeradoraDoencaProfissionals = SituacaoGeradoraDoencaProfissionalFactory.getCollection(1L);

		when(situacaoGeradoraDoencaProfissionalDao.findAllSelect()).thenReturn(situacaoGeradoraDoencaProfissionals);
		assertEquals(situacaoGeradoraDoencaProfissionals, situacaoGeradoraDoencaProfissionalManager.findAllSelect());
	}
}
