package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ObraManagerImpl;
import com.fortes.rh.dao.sesmt.ObraDao;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.test.factory.sesmt.ObraFactory;

public class ObraManagerTest extends MockObjectTestCase
{
	private ObraManagerImpl obraManager = new ObraManagerImpl();
	private Mock obraDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        obraDao = new Mock(ObraDao.class);
        obraManager.setDao((ObraDao) obraDao.proxy());
    }

	public void testFindAllSelect()
	{
		String nome = "Maison";
		Long empresaId = 1L;
		
		Collection<Obra> obras = ObraFactory.getCollection(1L);

		obraDao.expects(once()).method("findAllSelect").with(eq(empresaId)).will(returnValue(obras));
		
		assertEquals(obras, obraManager.findAllSelect(nome, empresaId));
	}
}
