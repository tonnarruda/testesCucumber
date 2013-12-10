package com.fortes.rh.test.business.sesmt;

import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.AreaVivenciaManagerImpl;
import com.fortes.rh.dao.sesmt.AreaVivenciaDao;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.test.factory.sesmt.AreaVivenciaPcmatFactory;

public class AreaVivenciaManagerTest extends MockObjectTestCase
{
	private AreaVivenciaManagerImpl areaVivenciaManager = new AreaVivenciaManagerImpl();
	private Mock areaVivenciaDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        areaVivenciaDao = new Mock(AreaVivenciaDao.class);
        areaVivenciaManager.setDao((AreaVivenciaDao) areaVivenciaDao.proxy());
    }
	
	public void testFindByPcmat()
	{
		Long empresaId = 1L;
		
		Collection<AreaVivenciaPcmat> areaVivenciaPcmats = AreaVivenciaPcmatFactory.getCollection(1L);

		areaVivenciaDao.expects(once()).method("findAllSelect").with(eq(null),eq(empresaId)).will(returnValue(areaVivenciaPcmats));
		assertEquals(areaVivenciaPcmats, areaVivenciaManager.findAllSelect(null, empresaId));
	}
}
