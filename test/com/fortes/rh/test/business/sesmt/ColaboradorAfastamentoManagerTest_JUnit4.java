package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManagerImpl;
import com.fortes.rh.dao.sesmt.ColaboradorAfastamentoDao;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public class ColaboradorAfastamentoManagerTest_JUnit4 
{
	private ColaboradorAfastamentoDao colaboradorAfastamentoDao;
	private ColaboradorAfastamentoManagerImpl colaboradorAfastamentoManagerImpl;

	String[] estabelecimentoCheck = new String[]{};
	ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();

	@Before
	public void setUp() throws Exception
    {
        colaboradorAfastamentoManagerImpl = new ColaboradorAfastamentoManagerImpl();
        
        colaboradorAfastamentoDao = mock(ColaboradorAfastamentoDao.class);
        colaboradorAfastamentoManagerImpl.setDao(colaboradorAfastamentoDao);
    }

	@Test
	public void testGetCount()
	{
		when(colaboradorAfastamentoDao.getCount(anyLong(), anyLong(), anyString(), anyString(), any(Long[].class), any(Date.class), any(Date.class))).thenReturn(1);
		assertEquals(Integer.valueOf(1),colaboradorAfastamentoManagerImpl.getCount(1L, "", "", estabelecimentoCheck, colaboradorAfastamento));
	}

}
