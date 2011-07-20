package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManagerImpl;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;

public class QuantidadeLimiteColaboradoresPorCargoManagerTest extends MockObjectTestCase
{
	private QuantidadeLimiteColaboradoresPorCargoManagerImpl quantidadeLimiteColaboradoresPorCargoManager = new QuantidadeLimiteColaboradoresPorCargoManagerImpl();
	private Mock quantidadeLimiteColaboradoresPorCargoDao;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        quantidadeLimiteColaboradoresPorCargoDao = new Mock(QuantidadeLimiteColaboradoresPorCargoDao.class);
        quantidadeLimiteColaboradoresPorCargoManager.setDao((QuantidadeLimiteColaboradoresPorCargoDao) quantidadeLimiteColaboradoresPorCargoDao.proxy());
    }
	
	public void testSaveLimites() 
	{
		QuantidadeLimiteColaboradoresPorCargo qtd1 = new QuantidadeLimiteColaboradoresPorCargo();
		qtd1.setAreaOrganizacional(null);

		QuantidadeLimiteColaboradoresPorCargo qtd2 = new QuantidadeLimiteColaboradoresPorCargo();
		qtd2.setAreaOrganizacional(null);
		
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtds = new ArrayList<QuantidadeLimiteColaboradoresPorCargo>();
		qtds.add(qtd1);
		qtds.add(qtd2);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		quantidadeLimiteColaboradoresPorCargoDao.expects(atLeastOnce()).method("save").with(ANYTHING);
		
		quantidadeLimiteColaboradoresPorCargoManager.saveLimites(qtds, areaOrganizacional);
		
		assertEquals(new Long(1), qtd1.getAreaOrganizacional().getId());
	}
	
	public void testUpdateLimites() 
	{
		Collection<QuantidadeLimiteColaboradoresPorCargo> qtds = new ArrayList<QuantidadeLimiteColaboradoresPorCargo>();
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		quantidadeLimiteColaboradoresPorCargoDao.expects(once()).method("deleteByArea").with(ANYTHING);
		quantidadeLimiteColaboradoresPorCargoManager.updateLimites(qtds, areaOrganizacional);
	}

}
