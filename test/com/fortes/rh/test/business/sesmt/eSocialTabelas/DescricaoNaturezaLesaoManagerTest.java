package com.fortes.rh.test.business.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.eSocialTabelas.DescricaoNaturezaLesaoManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.DescricaoNaturezaLesaoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.DescricaoNaturezaLesaoFactory;

public class DescricaoNaturezaLesaoManagerTest
{
	private DescricaoNaturezaLesaoManagerImpl descricaoNaturezaLesaoManager = new DescricaoNaturezaLesaoManagerImpl();
	private DescricaoNaturezaLesaoDao descricaoNaturezaLesaoDao;
	
	@Before
	public void setUp() throws Exception
    {
        descricaoNaturezaLesaoDao = mock(DescricaoNaturezaLesaoDao.class);
        descricaoNaturezaLesaoManager.setDao(descricaoNaturezaLesaoDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<DescricaoNaturezaLesao> descricaoNaturezaLesaos = DescricaoNaturezaLesaoFactory.getCollection(1L);

		when(descricaoNaturezaLesaoDao.findAllSelect()).thenReturn(descricaoNaturezaLesaos);
		assertEquals(descricaoNaturezaLesaos, descricaoNaturezaLesaoManager.findAllSelect());
	}
}
