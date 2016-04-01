package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;

import com.fortes.rh.business.geral.AreaFormacaoManagerImpl;
import com.fortes.rh.dao.geral.AreaFormacaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.test.business.MockObjectTestCaseManager;
import com.fortes.rh.test.business.TesteAutomaticoManager;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

public class AreaFormacaoManagerTest extends MockObjectTestCaseManager<AreaFormacaoManagerImpl> implements TesteAutomaticoManager
{
	private Mock areaFormacaoDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        manager = new AreaFormacaoManagerImpl();
        
        areaFormacaoDao = new Mock(AreaFormacaoDao.class);

        manager.setDao((AreaFormacaoDao) areaFormacaoDao.proxy());

		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
    }

    public void testFindByCargo()
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	areaFormacaoDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(new ArrayList<AreaFormacao>()));

    	assertNotNull(manager.findByCargo(cargo.getId()));
    }

    public void testPopulaCheckOrderNome()
    {
    	Collection<CheckBox> checkboxs = new ArrayList<CheckBox>();

		areaFormacaoDao.expects(once()).method("findAll").will(returnValue(null));

    	Collection<CheckBox> retorno = manager.populaCheckOrderNome();

    	assertEquals(checkboxs.size(), retorno.size());
    }

    public void testPopulaAreas()
	{
		String[] areas = {"1","2"};

		Collection<AreaFormacao> retorno = manager.populaAreas(areas);

		assertEquals(areas.length, retorno.size());
	}
    
    public void testFindByFiltro()
    {
    	AreaFormacao areaFormacao = new AreaFormacao();
    	areaFormacao.setId(1L);
    	areaFormacao.setNome("teste");

    	areaFormacaoDao.expects(once()).method("findByFiltro").with(eq(1),eq(15),eq(areaFormacao)).will(returnValue(new ArrayList<AreaFormacao>()));

    	assertNotNull(manager.findByFiltro(1, 15, areaFormacao));
    }
    
    public void testgetCount()
    {
    	AreaFormacao areaFormacao = new AreaFormacao();
    	areaFormacao.setId(1L);
    	areaFormacao.setNome("teste");
    	
    	areaFormacaoDao.expects(once()).method("getCount").with(eq(areaFormacao)).will(returnValue(1));

    	assertNotNull(manager.getCount(areaFormacao));
    }

	public void testExecutaTesteAutomaticoDoManager() {
		testeAutomatico(areaFormacaoDao);
	}
}
