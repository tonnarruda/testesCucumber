package com.fortes.rh.test.business.geral;

import org.junit.Assert;
import org.mockito.Mockito;

import com.fortes.rh.business.geral.EstabelecimentoManagerImpl;
import com.fortes.rh.dao.geral.EstabelecimentoDao;

public class EstabelecimentoManagerTest_JUnit4 
{
    EstabelecimentoManagerImpl estabelecimentoManager = null;
    EstabelecimentoDao estabelecimentoDao;

    public void setUp() throws Exception
    {
        estabelecimentoManager = new EstabelecimentoManagerImpl();

        estabelecimentoDao = Mockito.mock(EstabelecimentoDao.class);
        estabelecimentoManager.setDao(estabelecimentoDao);
    }

    public void testFindByMedicoCoordenador()
    {
    	Assert.assertTrue(false);
    }

    public void testFindByEngenheiroResponsavel()
    {
    	Assert.assertTrue(false);
    }
}