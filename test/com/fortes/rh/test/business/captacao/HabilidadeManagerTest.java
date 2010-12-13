package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.AtitudeManagerImpl;
import com.fortes.rh.business.captacao.HabilidadeManagerImpl;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.dao.captacao.HabilidadeDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AtitudeFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.web.tags.CheckBox;

public class HabilidadeManagerTest extends MockObjectTestCase
{
	HabilidadeManagerImpl HabilidadeManager;
	Mock HabilidadeDao;

    protected void setUp() throws Exception
    {
        HabilidadeManager = new HabilidadeManagerImpl();

        HabilidadeDao = new Mock(HabilidadeDao.class);
        HabilidadeManager.setDao((HabilidadeDao) HabilidadeDao.proxy());
        
    }

    public void testPopulaHabilidades()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	String[] HabilidadesCheck = new String[]{"1"};

    	assertEquals(1, HabilidadeManager.populaHabilidades(HabilidadesCheck).size());
    }
    public void testPopulaCheckOrderNome()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	HabilidadeDao.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(HabilidadeFactory.getCollection()));

    	assertEquals(1, HabilidadeManager.populaCheckOrderNome(empresa.getId()).size());
    }

    public void testPopulaCheckOrderNomeException()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	HabilidadeManager.setDao(null);

    	Collection<CheckBox> checks = HabilidadeManager.populaCheckOrderNome(empresa.getId());
		assertTrue(checks.isEmpty());
    }
    
    public void testFindByCargo()
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	HabilidadeDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(new ArrayList<Habilidade>()));

    	assertNotNull(HabilidadeManager.findByCargo(cargo.getId()));
    }
    
}
