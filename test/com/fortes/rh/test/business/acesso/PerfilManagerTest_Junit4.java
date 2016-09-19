package com.fortes.rh.test.business.acesso;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.acesso.PerfilManagerImpl;
import com.fortes.rh.dao.acesso.PerfilDao;

public class PerfilManagerTest_Junit4
{
	private PerfilManagerImpl perfilManager = new PerfilManagerImpl();
	private PerfilDao perfilDao;
	
	@Before
	public void setUp()
	{
		perfilDao = mock(PerfilDao.class);
		perfilManager.setDao(perfilDao);
	}
	
	@Test
	public void testRemovePerfilPapelByPapelId() {
		Long papelId = 680L;
		
		perfilManager.removePerfilPapelByPapelId(papelId);
		verify(perfilDao, times(1)).removePerfilPapelByPapelId(papelId);
	}
}
