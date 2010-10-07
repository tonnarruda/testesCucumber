package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.EmpresaBdsManagerImpl;
import com.fortes.rh.dao.captacao.EmpresaBdsDao;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.test.factory.captacao.EmpresaBdsFactory;

public class EmpresaBdsManagerTest extends MockObjectTestCase
{
	private EmpresaBdsManagerImpl empresaBdsManager = new EmpresaBdsManagerImpl();
	private Mock empresaBdsDao;

    protected void setUp() throws Exception
    {
    	empresaBdsDao = new Mock(EmpresaBdsDao.class);
    	empresaBdsManager.setDao((EmpresaBdsDao) empresaBdsDao.proxy());
    }

    public void testFindAllProjection() throws Exception
    {
    	EmpresaBds e1 = EmpresaBdsFactory.getEntity();
    	e1.setId(1L);
    	EmpresaBds e2 = EmpresaBdsFactory.getEntity();
    	e2.setId(2L);

    	Collection<EmpresaBds> empresaBdss = new ArrayList<EmpresaBds>();
    	empresaBdss.add(e1);
    	empresaBdss.add(e2);

    	Long[] ids = {e1.getId(),e2.getId()};

    	empresaBdsDao.expects(once()).method("findAllProjection").with(eq(ids)).will(returnValue(empresaBdss));

    	Collection<EmpresaBds> retorno = empresaBdsManager.findAllProjection(ids);

    	assertEquals(2, retorno.size());
    }
    
    public void testFindByIdProjection() throws Exception
    {
    	EmpresaBds empresaBds = EmpresaBdsFactory.getEntity();
    	empresaBds.setId(1L);
    	
    	empresaBdsDao.expects(once()).method("findByIdProjection").with(eq(empresaBds.getId())).will(returnValue(empresaBds));
    	
    	assertEquals(empresaBds, empresaBdsManager.findByIdProjection(empresaBds.getId()));
    }
    
    public void testFindAllProjectionVazio() throws Exception
    {    	
    	Collection<EmpresaBds> retorno = empresaBdsManager.findAllProjection(null);
    	
    	assertEquals(0, retorno.size());
    }


}
