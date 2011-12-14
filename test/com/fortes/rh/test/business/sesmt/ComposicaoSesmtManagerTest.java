package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ComposicaoSesmtManagerImpl;
import com.fortes.rh.dao.sesmt.ComposicaoSesmtDao;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.rh.test.factory.sesmt.ComposicaoSesmtFactory;

public class ComposicaoSesmtManagerTest extends MockObjectTestCase
{
	private ComposicaoSesmtManagerImpl composicaoSesmtManager = new ComposicaoSesmtManagerImpl();
	private Mock composicaoSesmtDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        composicaoSesmtDao = new Mock(ComposicaoSesmtDao.class);
        composicaoSesmtManager.setDao((ComposicaoSesmtDao) composicaoSesmtDao.proxy());
    }

	public void testFindAllSelect()
	{
		Long empresaId = 1L;
		
		Collection<ComposicaoSesmt> composicaoSesmts = ComposicaoSesmtFactory.getCollection(1L);

		composicaoSesmtDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(composicaoSesmts));
		assertEquals(composicaoSesmts, composicaoSesmtManager.findAllSelect(empresaId));
	}
}
