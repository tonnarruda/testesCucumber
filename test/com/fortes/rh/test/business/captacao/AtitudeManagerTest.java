package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AtitudeManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.web.tags.CheckBox;

public class AtitudeManagerTest extends MockObjectTestCase
{
	AtitudeManagerImpl atitudeManager;
	Mock atitudeDao;
	Mock areaOrganizacionalManager ;

    protected void setUp() throws Exception
    {
        atitudeManager = new AtitudeManagerImpl();

        atitudeDao = new Mock(AtitudeDao.class);
        atitudeManager.setDao((AtitudeDao) atitudeDao.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        atitudeManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
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
    
    public void testSincronizar()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	
    	AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional1.setNome("área1");
		areas.add(areaOrganizacional1);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional2.setNome("área2");
		areas.add(areaOrganizacional2);
    	
    	Collection<Atitude> atitudesOrigem = new ArrayList<Atitude>();
    	Atitude atitude1 = AtitudeFactory.getEntity(1L);
    	Atitude atitude2 = AtitudeFactory.getEntity(2L);
    	Atitude atitude3 = AtitudeFactory.getEntity(3L);
    	atitudesOrigem.add(atitude1);
    	atitudesOrigem.add(atitude2);
    	atitudesOrigem.add(atitude3);
    	
    	atitudeDao.expects(once()).method("findSincronizarAtitudes").will(returnValue(atitudesOrigem));
    	atitudeDao.expects(atLeastOnce()).method("save");
    	areaOrganizacionalManager.expects(atLeastOnce()).method("findByAtitude").will(returnValue(areas));
    	atitudeDao.expects(atLeastOnce()).method("update");
    	
    	Map<Long, Long> areaIds = new  HashMap<Long, Long>();
    	areaIds.put(1L, 5L);
		
		Long empresaOrigemId=1L;
		Long empresaDestinoId=2L;
    	Map<Long, Long> atitudeIds = new  HashMap<Long, Long>();
    	
		atitudeManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, atitudeIds);
		
		assertEquals(3, atitudeIds.size());
    }
    
}
