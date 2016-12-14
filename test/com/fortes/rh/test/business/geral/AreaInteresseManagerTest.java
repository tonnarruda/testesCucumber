package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Mock;

import com.fortes.rh.business.geral.AreaInteresseManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.geral.AreaInteresseDao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;

public class AreaInteresseManagerTest extends MockObjectTestCaseManager<AreaInteresseManagerImpl> implements TesteAutomaticoManager
{
	private Mock areaInteresseDao = null;
	Mock areaOrganizacionalManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new AreaInteresseManagerImpl();
        
        areaInteresseDao = new Mock(AreaInteresseDao.class);

        manager.setDao((AreaInteresseDao) areaInteresseDao.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        manager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
    }

    public void testFindAreasInteresseByAreaOrganizacional()
    {
    	AreaOrganizacional ao1 = AreaOrganizacionalFactory.getEntity();
    	ao1.setId(1L);

    	Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
    	areaOrganizacionals.add(ao1);

    	AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse(1L);
    	areaInteresse.setAreasOrganizacionais(areaOrganizacionals);

    	Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();
    	areaInteresses.add(areaInteresse);

    	areaInteresseDao.expects(once()).method("findAreasInteresseByAreaOrganizacional").with(eq(ao1)).will(returnValue(areaInteresses));

    	Collection<AreaInteresse> retorno = manager.findAreasInteresseByAreaOrganizacional(ao1);

    	assertEquals(1, retorno.size());
    }

    public void testFindAllSelect()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse(1L);

    	Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();
    	areaInteresses.add(areaInteresse);

    	areaInteresseDao.expects(once()).method("findAllSelect").with(eq(new Long[]{empresa.getId()})).will(returnValue(areaInteresses));

    	assertEquals(1, manager.findAllSelect(empresa.getId()).size());
    }
    
    public void testFindByIdProjection()
    {
    	AreaInteresse areaInteresse = AreaInteresseFactory.getAreaInteresse(1L);
    	
    	areaInteresseDao.expects(once()).method("findByIdProjection").with(eq(areaInteresse.getId())).will(returnValue(areaInteresse));
    	
    	assertEquals(areaInteresse, manager.findByIdProjection(areaInteresse.getId()));
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
    	
    	Collection<AreaInteresse> areaInteressesOrigem = new ArrayList<AreaInteresse>();
    	AreaInteresse areaInteresse1 = AreaInteresseFactory.getAreaInteresse(1L);
    	AreaInteresse areaInteresse2 = AreaInteresseFactory.getAreaInteresse(2L);
    	AreaInteresse areaInteresse3 = AreaInteresseFactory.getAreaInteresse(3L);
    	areaInteressesOrigem.add(areaInteresse1);
    	areaInteressesOrigem.add(areaInteresse2);
    	areaInteressesOrigem.add(areaInteresse3);
    	
    	areaInteresseDao.expects(once()).method("findSincronizarAreasInteresse").will(returnValue(areaInteressesOrigem));
    	areaInteresseDao.expects(atLeastOnce()).method("save");
    	areaOrganizacionalManager.expects(atLeastOnce()).method("getAreasByAreaInteresse").will(returnValue(areas));
    	areaInteresseDao.expects(atLeastOnce()).method("update");
    	
    	Map<Long, Long> areaIds = new  HashMap<Long, Long>();
    	areaIds.put(1L, 5L);
		
		Long empresaOrigemId=1L;
		Long empresaDestinoId=2L;
    	Map<Long, Long> areaInteresseIds = new  HashMap<Long, Long>();
    	
		manager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, areaInteresseIds);
		
		assertEquals(3, areaInteresseIds.size());
    }

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(areaInteresseDao);
	}
}
