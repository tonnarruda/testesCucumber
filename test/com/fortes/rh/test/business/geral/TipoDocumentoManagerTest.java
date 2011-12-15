package com.fortes.rh.test.business.geral;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.TipoDocumentoManagerImpl;
import com.fortes.rh.dao.geral.TipoDocumentoDao;
import com.fortes.rh.model.geral.TipoDocumento;
import com.fortes.rh.test.factory.geral.TipoDocumentoFactory;

public class TipoDocumentoManagerTest extends MockObjectTestCase
{
	private TipoDocumentoManagerImpl tipoDocumentoManager = new TipoDocumentoManagerImpl();
	private Mock tipoDocumentoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        tipoDocumentoDao = new Mock(TipoDocumentoDao.class);
        tipoDocumentoManager.setDao((TipoDocumentoDao) tipoDocumentoDao.proxy());
    }

	public void testFindAllSelect()
	{
		Collection<TipoDocumento> tipoDocumentos = TipoDocumentoFactory.getCollection(1L);

		tipoDocumentoDao.expects(once()).method("findAll").will(returnValue(tipoDocumentos));
		assertEquals(tipoDocumentos, tipoDocumentoManager.findAll());
	}
}
