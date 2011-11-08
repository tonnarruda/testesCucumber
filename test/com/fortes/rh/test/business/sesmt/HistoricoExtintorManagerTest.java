package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

import com.fortes.rh.business.sesmt.HistoricoExtintorManagerImpl;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;

public class HistoricoExtintorManagerTest extends MockObjectTestCase
{
	private HistoricoExtintorManagerImpl historicoExtintorManager = new HistoricoExtintorManagerImpl();
	private Mock historicoExtintorDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        historicoExtintorDao = new Mock(HistoricoExtintorDao.class);
        historicoExtintorManager.setDao((HistoricoExtintorDao) historicoExtintorDao.proxy());
    }
	
	@SuppressWarnings("unchecked")
	public void testFindByExtintor()
	{
		Extintor extintor = ExtintorFactory.getEntity(1L);
		
		HistoricoExtintor historicoExtintor = new HistoricoExtintor();
		historicoExtintor.setExtintor(extintor);
		
		Collection<HistoricoExtintor> historicos = Arrays.asList(new HistoricoExtintor[] { historicoExtintor });
		
		historicoExtintorDao.expects(once()).method("findByExtintor").with(eq(1L)).will(returnValue(historicos));
		
		assertEquals(1, historicoExtintorManager.findByExtintor(1L).size());
	}
}