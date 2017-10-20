package com.fortes.rh.test.business.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoFactory;

public class CodificacaoAcidenteTrabalhoManagerTest
{
	private CodificacaoAcidenteTrabalhoManagerImpl codificacaoAcidenteTrabalhoManager = new CodificacaoAcidenteTrabalhoManagerImpl();
	private CodificacaoAcidenteTrabalhoDao codificacaoAcidenteTrabalhoDao;
	
	@Before
	public void setUp() throws Exception
    {
        codificacaoAcidenteTrabalhoDao = mock(CodificacaoAcidenteTrabalhoDao.class);
        codificacaoAcidenteTrabalhoManager.setDao(codificacaoAcidenteTrabalhoDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<CodificacaoAcidenteTrabalho> codificacaoAcidenteTrabalhos = CodificacaoAcidenteTrabalhoFactory.getCollection(1L);

		when(codificacaoAcidenteTrabalhoDao.findAllSelect()).thenReturn(codificacaoAcidenteTrabalhos);
		assertEquals(1, codificacaoAcidenteTrabalhoManager.findAllSelect().size());
	}
}
