package com.fortes.rh.test.business.sesmt;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.MedidaRiscoFasePcmatManagerImpl;
import com.fortes.rh.dao.sesmt.MedidaRiscoFasePcmatDao;

public class MedidaRiscoFasePcmatManagerTest extends MockObjectTestCase
{
	private MedidaRiscoFasePcmatManagerImpl medidaRiscoFasePcmatManager = new MedidaRiscoFasePcmatManagerImpl();
	private Mock medidaRiscoFasePcmatDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        medidaRiscoFasePcmatDao = new Mock(MedidaRiscoFasePcmatDao.class);
        medidaRiscoFasePcmatManager.setDao((MedidaRiscoFasePcmatDao) medidaRiscoFasePcmatDao.proxy());
    }
}
