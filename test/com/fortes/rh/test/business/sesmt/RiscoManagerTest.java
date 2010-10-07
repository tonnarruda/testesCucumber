package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.RiscoManagerImpl;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Risco;

public class RiscoManagerTest extends MockObjectTestCase
{
	private RiscoManagerImpl riscoManager = new RiscoManagerImpl();
	private Mock riscoDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        riscoDao = new Mock(RiscoDao.class);
        riscoManager.setDao((RiscoDao) riscoDao.proxy());
    }

	public void testFindEpisByRisco()throws Exception
	{
		Collection<Epi> epis = new ArrayList<Epi>();

		Epi f1 = new Epi();
		f1.setId(1L);
		f1.setNome("nome1");

		Epi f2 = new Epi();
		f2.setId(2L);
		f1.setNome("nome2");

		epis.add(f1);
		epis.add(f2);

		Risco risco = new Risco();
		risco.setId(1L);

		List<Object> lista = new ArrayList<Object>();
		Object[] ob1 = new Object[]{1L,"nome1"};
		Object[] ob2 = new Object[]{2L,"nome2"};

		lista.add(ob1);
		lista.add(ob2);

		riscoDao.expects(once()).method("findEpisByRisco").with(eq(risco.getId())).will(returnValue(lista));
		Collection<Epi> episTmps = riscoManager.findEpisByRisco(risco.getId());

		assertEquals(episTmps, epis);
	}
	
	public void testFindEpisByRiscoException()throws Exception
	{
		riscoDao.expects(once()).method("findEpisByRisco").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		riscoManager.findEpisByRisco(1L);
	}
}