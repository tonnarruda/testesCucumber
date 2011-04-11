package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AtitudeManagerImpl;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.web.tags.CheckBox;

public class AtitudeManagerTest extends MockObjectTestCase
{
	AtitudeManagerImpl atitudeManager;
	Mock atitudeDao;

    protected void setUp() throws Exception
    {
        atitudeManager = new AtitudeManagerImpl();

        atitudeDao = new Mock(AtitudeDao.class);
        atitudeManager.setDao((AtitudeDao) atitudeDao.proxy());
        
    }

    public void testPopulaAtitudes()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	String[] atitudesCheck = new String[]{"1"};

    	assertEquals(1, atitudeManager.populaAtitudes(atitudesCheck).size());
    }
    public void testPopulaCheckOrderNome()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	atitudeDao.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(AtitudeFactory.getCollection()));

    	assertEquals(1, atitudeManager.populaCheckOrderNome(empresa.getId()).size());
    }

    public void testPopulaCheckOrderNomeException()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	atitudeManager.setDao(null);

    	Collection<CheckBox> checks = atitudeManager.populaCheckOrderNome(empresa.getId());
		assertTrue(checks.isEmpty());
    }
    
    public void testFindByCargo()
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	atitudeDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(new ArrayList<Atitude>()));

    	assertNotNull(atitudeManager.findByCargo(cargo.getId()));
    }
    
}
