package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.HabilidadeManagerImpl;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.web.tags.CheckBox;

public class HabilidadeManagerTest extends MockObjectTestCase
{
	HabilidadeManagerImpl habilidadeManagerImpl;
	Mock habilidadeDao;

    protected void setUp() throws Exception
    {
        habilidadeManagerImpl = new HabilidadeManagerImpl();

        habilidadeDao = new Mock(HabilidadeDao.class);
        habilidadeManagerImpl.setDao((HabilidadeDao) habilidadeDao.proxy());
        
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
    
}
