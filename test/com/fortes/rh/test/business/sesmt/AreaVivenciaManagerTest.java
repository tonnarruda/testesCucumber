package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.sesmt.AreaVivenciaManagerImpl;
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;
import com.fortes.rh.model.sesmt.AreaVivencia;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaFactory;

public class AreaVivenciaManagerTest 
{
	private AreaVivenciaManagerImpl areaVivenciaManager = new AreaVivenciaManagerImpl();
	private AreaVivenciaDao areaVivenciaDao;
	
	@Before
	public void setUp() throws Exception
    {
        areaVivenciaDao = mock(AreaVivenciaDao.class);
        areaVivenciaManager.setDao(areaVivenciaDao);
    }
	
	@Test
	public void testFindByPcmat()
	{
		Long empresaId = 1L;
		
		Collection<AreaVivencia> areaVivencias = AreaVivenciaFactory.getCollection(1L);

		when(areaVivenciaDao.findAllSelect(null, empresaId)).thenReturn(areaVivencias);
		assertEquals(areaVivencias, areaVivenciaManager.findAllSelect(null, empresaId));
	}
}
