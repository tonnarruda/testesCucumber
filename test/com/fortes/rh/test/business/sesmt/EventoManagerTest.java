package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EventoManagerImpl;
import com.fortes.rh.dao.sesmt.EventoDao;

public class EventoManagerTest extends MockObjectTestCase
{
	private EventoManagerImpl eventoManager = new EventoManagerImpl();
	private Mock eventoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        eventoDao = new Mock(EventoDao.class);
        eventoManager.setDao((EventoDao) eventoDao.proxy());
    }
	
	public void testDemiguel()
	{
		assertTrue(true);
	}
}
