package com.fortes.rh.test.business.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoFactory;

public class SituacaoGeradoraAcidenteTrabalhoManagerTest
{
	private SituacaoGeradoraAcidenteTrabalhoManagerImpl situacaoGeradoraAcidenteTrabalhoManager = new SituacaoGeradoraAcidenteTrabalhoManagerImpl();
	private SituacaoGeradoraAcidenteTrabalhoDao situacaoGeradoraAcidenteTrabalhoDao;
	
	@Before
	public void setUp() throws Exception
    {
        situacaoGeradoraAcidenteTrabalhoDao = mock(SituacaoGeradoraAcidenteTrabalhoDao.class);
        situacaoGeradoraAcidenteTrabalhoManager.setDao(situacaoGeradoraAcidenteTrabalhoDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<SituacaoGeradoraAcidenteTrabalho> situacaoGeradoraAcidenteTrabalhos = SituacaoGeradoraAcidenteTrabalhoFactory.getCollection(1L);

		when(situacaoGeradoraAcidenteTrabalhoDao.findAllSelect()).thenReturn(situacaoGeradoraAcidenteTrabalhos);
		assertEquals(1, situacaoGeradoraAcidenteTrabalhoManager.findAllSelect().size());
	}
}
