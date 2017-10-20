package com.fortes.rh.test.business.sesmt.eSocialTabelas;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.eSocialTabelas.ParteCorpoAtingidaManagerImpl;
import com.fortes.rh.dao.sesmt.eSocialTabelas.ParteCorpoAtingidaDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.ParteCorpoAtingida;
import com.fortes.rh.test.factory.sesmt.eSocialTabelas.ParteCorpoAtingidaFactory;

public class ParteCorpoAtingidaManagerTest
{
	private ParteCorpoAtingidaManagerImpl parteCorpoAtingidaManager = new ParteCorpoAtingidaManagerImpl();
	private ParteCorpoAtingidaDao parteCorpoAtingidaDao;
	
	@Before
	public void setUp() throws Exception
    {
        parteCorpoAtingidaDao = mock(ParteCorpoAtingidaDao.class);
        parteCorpoAtingidaManager.setDao(parteCorpoAtingidaDao);
    }

	@Test
	public void testFindAllSelect()
	{
		Collection<ParteCorpoAtingida> parteCorpoAtingidas = ParteCorpoAtingidaFactory.getCollection(1L);

		when(parteCorpoAtingidaDao.findAllSelect()).thenReturn(parteCorpoAtingidas);
		assertEquals(parteCorpoAtingidas, parteCorpoAtingidaManager.findAllSelect());
	}
}
