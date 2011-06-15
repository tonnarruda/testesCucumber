package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.captacao.HabilidadeManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.web.tags.CheckBox;

public class HabilidadeManagerTest extends MockObjectTestCase
{
	HabilidadeManagerImpl habilidadeManagerImpl;
	Mock habilidadeDao;
	Mock areaOrganizacionalManager;

    protected void setUp() throws Exception
    {
        habilidadeManagerImpl = new HabilidadeManagerImpl();

        habilidadeDao = new Mock(HabilidadeDao.class);
        habilidadeManagerImpl.setDao((HabilidadeDao) habilidadeDao.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        habilidadeManagerImpl.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
    }

    public void testPopulaHabilidades()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	String[] HabilidadesCheck = new String[]{"1"};

    	assertEquals(1, habilidadeManagerImpl.populaHabilidades(HabilidadesCheck).size());
    }
    public void testPopulaCheckOrderNome()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	habilidadeDao.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(HabilidadeFactory.getCollection()));

    	assertEquals(1, habilidadeManagerImpl.populaCheckOrderNome(empresa.getId()).size());
    }

    public void testPopulaCheckOrderNomeException()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	habilidadeManagerImpl.setDao(null);

    	Collection<CheckBox> checks = habilidadeManagerImpl.populaCheckOrderNome(empresa.getId());
		assertTrue(checks.isEmpty());
    }
    
    public void testFindByCargo()
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	habilidadeDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(new ArrayList<Habilidade>()));

    	assertNotNull(habilidadeManagerImpl.findByCargo(cargo.getId()));
    }
    
    public void testFindByAreasOrganizacionalIds() {
    	
    	Long[] areas = new Long[] {1L};
		Long empresaId = 69L;
		
		habilidadeDao.expects(once())
			.method("findByAreasOrganizacionalIds")
				.with(eq(areas), eq(empresaId))
					.will(returnValue(getListaDeHabilidades()));
		
		Collection<Habilidade> habilidades = habilidadeManagerImpl.findByAreasOrganizacionalIds(areas, empresaId);
    	
		assertEquals("quantidade de habilidades encontradas", 2, habilidades.size());
    }

	private Collection<Habilidade> getListaDeHabilidades() {
		Collection<Habilidade> habilidades = new ArrayList<Habilidade>();

		habilidades.add(HabilidadeFactory.getEntity());
		habilidades.add(HabilidadeFactory.getEntity());
		
		return habilidades;
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
    	
    	Collection<Habilidade> habilidadesOrigem = new ArrayList<Habilidade>();
    	Habilidade habilidade1 = HabilidadeFactory.getEntity(1L);
    	Habilidade habilidade2 = HabilidadeFactory.getEntity(2L);
    	Habilidade habilidade3 = HabilidadeFactory.getEntity(3L);
    	habilidadesOrigem.add(habilidade1);
    	habilidadesOrigem.add(habilidade2);
    	habilidadesOrigem.add(habilidade3);
    	
    	habilidadeDao.expects(once()).method("findSincronizarHabilidades").will(returnValue(habilidadesOrigem));
    	habilidadeDao.expects(atLeastOnce()).method("save");
    	areaOrganizacionalManager.expects(atLeastOnce()).method("findByHabilidade").will(returnValue(areas));
    	habilidadeDao.expects(atLeastOnce()).method("update");
    	
    	Map<Long, Long> areaIds = new  HashMap<Long, Long>();
    	areaIds.put(1L, 5L);
		
		Long empresaOrigemId=1L;
		Long empresaDestinoId=2L;
    	Map<Long, Long> habilidadeIds = new  HashMap<Long, Long>();
    	
		habilidadeManagerImpl.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, habilidadeIds);
		
		assertEquals(3, habilidadeIds.size());
    }
    
}
