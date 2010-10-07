package com.fortes.rh.test.business;
/*
 package com.fortes.rh.test.business;
import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.geral.GrupoEmpresarialManagerImpl;
import com.fortes.rh.dao.geral.GrupoEmpresarialDao;
import com.fortes.rh.model.geral.GrupoEmpresarial;

public class GenericManagerTest extends MockObjectTestCase implements ManagerTest
{
	private GrupoEmpresarialManagerImpl manager;
	private Mock dao;

    protected void setUp() throws Exception 
    {
        super.setUp();
        manager = new GrupoEmpresarialManagerImpl();
        dao = new Mock(GrupoEmpresarialDao.class);
        manager.setDao((GrupoEmpresarialDao) dao.proxy());
    }

	protected void tearDown() throws Exception
	{
        dao = null;
        manager = null;
		super.tearDown();        
	}

	public void testSave() throws Exception
    {
    	GrupoEmpresarial testData = new GrupoEmpresarial();
    	testData.setId(1L);
    	testData.setNome("Grupo Empresarial Teste");
    	
        dao.expects(once()).method("save").will(returnValue(testData));
    	
        GrupoEmpresarial grupoEmpresarial = new GrupoEmpresarial(); 
        grupoEmpresarial.setNome("Grupo Empresarial Teste");
        grupoEmpresarial = manager.save(grupoEmpresarial);
        
        assertTrue(grupoEmpresarial != null);
        assertTrue(grupoEmpresarial.getId().equals(1L));
        dao.verify();
    }

    public void testFindById() throws Exception
    {
    	GrupoEmpresarial testData = new GrupoEmpresarial();
    	testData.setId(1L);
    	testData.setNome("Grupo Empresarial Teste");
    	
        dao.expects(once()).method("findById").with(eq(1L)).will(returnValue(testData));
    	
        GrupoEmpresarial grupoEmpresarial = manager.findById(1L);
        assertTrue(grupoEmpresarial != null);
        dao.verify();
    }

    public void testFind() throws Exception
    {
    	GrupoEmpresarial testData = new GrupoEmpresarial();
    	testData.setId(1L);
    	testData.setNome("Grupo Empresarial Teste");
    	
    	Collection<GrupoEmpresarial> grupos = new ArrayList<GrupoEmpresarial>();
    	grupos.add(testData);
    	
    	GrupoEmpresarial ge = new GrupoEmpresarial();
    	ge.setId(1L);
        dao.expects(once()).method("find").with(eq(ge)).will(returnValue(grupos));
    	
        Collection<GrupoEmpresarial> grupoEmpresarials = manager.find(ge);
        assertTrue(grupoEmpresarials != null);
        assertTrue(grupoEmpresarials.size() == 1);
        dao.verify();
    }

    public void testFindAll() throws Exception
    {
    	GrupoEmpresarial testData = new GrupoEmpresarial();
    	testData.setId(1L);
    	testData.setNome("Grupo Empresarial Teste");
    	
    	GrupoEmpresarial testData2 = new GrupoEmpresarial();
    	testData.setId(2L);
    	testData.setNome("Grupo Empresarial Teste2");
    	
    	Collection<GrupoEmpresarial> grupos = new ArrayList<GrupoEmpresarial>();
    	grupos.add(testData);
    	grupos.add(testData2);
    	
        dao.expects(once()).method("findAll").will(returnValue(grupos));
    	
        Collection<GrupoEmpresarial> grupoEmpresarials = manager.findAll();
        assertTrue(grupoEmpresarials != null);
        assertTrue(grupoEmpresarials.size() == 2);
        dao.verify();
    }

    public void testException() 
    {
    	GrupoEmpresarial grupoEmpresarial = new GrupoEmpresarial();
    	grupoEmpresarial.setId(1L);
    	grupoEmpresarial.setNome("Grupo Empresarial Teste");
    	
        Exception ex = new DataIntegrityViolationException("");
        dao.expects(once()).method("save").with(same(grupoEmpresarial)).will(throwException(ex));
        
        try 
        {
            manager.save(grupoEmpresarial);
        }
        catch (DataIntegrityViolationException e) 
        {
            assertNotNull(e);
        }
    }
}
**/