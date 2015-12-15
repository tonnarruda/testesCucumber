package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AtitudeManagerImpl;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class AtitudeManagerTest extends MockObjectTestCase
{
	AtitudeManagerImpl atitudeManager;
	Mock atitudeDao;
	Mock areaOrganizacionalManager ;
	private Mock cursoManager;
	private Mock criterioAvaliacaoCompetenciaManager;

    protected void setUp() throws Exception
    {
        atitudeManager = new AtitudeManagerImpl();

        atitudeDao = new Mock(AtitudeDao.class);
        atitudeManager.setDao((AtitudeDao) atitudeDao.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        atitudeManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        criterioAvaliacaoCompetenciaManager = new Mock(CriterioAvaliacaoCompetenciaManager.class);
        atitudeManager.setCriterioAvaliacaoCompetenciaManager((CriterioAvaliacaoCompetenciaManager) criterioAvaliacaoCompetenciaManager.proxy());
        
        cursoManager = new Mock(CursoManager.class);
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
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

    	assertEquals(1, atitudeManager.populaCheckOrderNome(null, empresa.getId()).size());
    }

    public void testPopulaCheckOrderNomeException()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	atitudeManager.setDao(null);

    	Collection<CheckBox> checks = atitudeManager.populaCheckOrderNome(null, empresa.getId());
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
    	criterioAvaliacaoCompetenciaManager.expects(atLeastOnce()).method("sincronizaCriterioAvaliacaoCompetencia").will(returnValue(new ArrayList<CriterioAvaliacaoCompetencia>()));
    	 
    	Map<Long, Long> areaIds = new  HashMap<Long, Long>();
    	areaIds.put(1L, 5L);
		
		Long empresaOrigemId=1L;
		Long empresaDestinoId=2L;
    	Map<Long, Long> atitudeIds = new  HashMap<Long, Long>();
    	
		atitudeManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, atitudeIds);
		
		assertEquals(3, atitudeIds.size());
    }
    
	public void testFindByIdProjection()
	{
		MockSpringUtil.mocks.put("cursoManager", cursoManager);
		
		Atitude atitude = AtitudeFactory.getEntity(1L);
		
		atitudeDao.expects(once()).method("findByIdProjection").with(eq(atitude.getId())).will(returnValue(atitude));

		Collection<AreaOrganizacional> areas = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));
		
		areaOrganizacionalManager.expects(once()).method("findByAtitude").with(eq(atitude.getId())).will(returnValue(areas));
		cursoManager.expects(once()).method("findByCompetencia").with(eq(atitude.getId()), eq(TipoCompetencia.ATITUDE)).will(returnValue(areas));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("findByCompetencia").with(eq(atitude.getId()), eq(TipoCompetencia.ATITUDE));

		
		assertEquals(atitude, atitudeManager.findByIdProjection(atitude.getId()));
	}

}
