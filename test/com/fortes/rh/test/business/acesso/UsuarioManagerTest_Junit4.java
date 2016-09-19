package com.fortes.rh.test.business.acesso;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.acesso.UsuarioManagerImpl;
import com.fortes.rh.dao.acesso.UsuarioDao;

public class UsuarioManagerTest_Junit4
{
	private UsuarioDao usuarioDao;
	private UsuarioManagerImpl usuarioManager = new UsuarioManagerImpl();

	@Before
    public void setUp() throws Exception
    {
        usuarioDao = mock(UsuarioDao.class);
        usuarioManager.setDao(usuarioDao);
    }
	
	@Test
	public void testFindUsuarioProjection() {
		Long usuarioId = 5L;
		usuarioManager.findEmailByUsuarioId(usuarioId);
		verify(usuarioDao, times(1)).findEmailByUsuarioId(usuarioId);
	}
}
