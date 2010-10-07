package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaFormacaoManagerImpl;
import com.fortes.rh.dao.geral.AreaFormacaoDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

public class AreaFormacaoManagerTest extends MockObjectTestCase
{
	private AreaFormacaoManagerImpl areaFormacaoManager = new AreaFormacaoManagerImpl();
	private Mock areaFormacaoDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        areaFormacaoDao = new Mock(AreaFormacaoDao.class);

        areaFormacaoManager.setDao((AreaFormacaoDao) areaFormacaoDao.proxy());

		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
    }

    public void testFindByCargo()
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	areaFormacaoDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(new ArrayList<AreaFormacao>()));

    	assertNotNull(areaFormacaoManager.findByCargo(cargo.getId()));
    }

    public void testPopulaCheckOrderNome()
    {
    	Collection<CheckBox> checkboxs = new ArrayList<CheckBox>();

		areaFormacaoDao.expects(once()).method("findAll").will(returnValue(null));

    	Collection<CheckBox> retorno = areaFormacaoManager.populaCheckOrderNome();

    	assertEquals(checkboxs.size(), retorno.size());
    }

    public void testPopulaAreas()
	{
		String[] areas = {"1","2"};

		Collection<AreaFormacao> retorno = areaFormacaoManager.populaAreas(areas);

		assertEquals(areas.length, retorno.size());
	}
    
    public void testFindByFiltro()
    {
    	AreaFormacao areaFormacao = new AreaFormacao();
    	areaFormacao.setId(1L);
    	areaFormacao.setNome("teste");

    	areaFormacaoDao.expects(once()).method("findByFiltro").with(eq(1),eq(15),eq(areaFormacao)).will(returnValue(new ArrayList<AreaFormacao>()));

    	assertNotNull(areaFormacaoManager.findByFiltro(1, 15, areaFormacao));
    }
    
    public void testgetCount()
    {
    	AreaFormacao areaFormacao = new AreaFormacao();
    	areaFormacao.setId(1L);
    	areaFormacao.setNome("teste");
    	
    	areaFormacaoDao.expects(once()).method("getCount").with(eq(areaFormacao)).will(returnValue(1));

    	assertNotNull(areaFormacaoManager.getCount(areaFormacao));
    }
}
