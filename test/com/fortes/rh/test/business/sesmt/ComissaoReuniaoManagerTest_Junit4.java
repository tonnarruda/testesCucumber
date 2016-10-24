package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.Date;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.ComissaoReuniaoManagerImpl;
import com.fortes.rh.business.sesmt.ComissaoReuniaoPresencaManager;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ComissaoReuniaoManagerTest_Junit4
{
	private ComissaoReuniaoManagerImpl comissaoReuniaoManager = new ComissaoReuniaoManagerImpl();
	private ComissaoReuniaoDao comissaoReuniaoDao;
	private ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager;
	private ComissaoManager comissaoManager;

	@Before
	public void setUp() throws Exception
    {
        comissaoReuniaoDao = mock(ComissaoReuniaoDao.class);
        comissaoReuniaoPresencaManager = mock(ComissaoReuniaoPresencaManager.class);
        comissaoManager = mock(ComissaoManager.class);
        MockSpringUtilJUnit4.mocks.put("comissaoManager", comissaoManager);

        comissaoReuniaoManager.setDao(comissaoReuniaoDao);
        comissaoReuniaoManager.setComissaoReuniaoPresencaManager(comissaoReuniaoPresencaManager);

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
    }

	@Test
	public void testSugerirReuniao()
	{
		Comissao comissao = ComissaoFactory.getEntity(1L);
		comissao.setDataIni(new Date());
		comissao.setDataFim(DateUtil.incrementaMes(comissao.getDataIni(), 12));
		Exception exception = null;
		try{
			comissaoReuniaoManager.sugerirReuniao(comissao);
		
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}
}