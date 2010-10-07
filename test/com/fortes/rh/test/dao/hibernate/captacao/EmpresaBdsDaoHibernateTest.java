package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.EmpresaBdsDao;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaBdsFactory;

public class EmpresaBdsDaoHibernateTest extends GenericDaoHibernateTest<EmpresaBds>
{
	private EmpresaBdsDao empresaBdsDao;

	public EmpresaBds getEntity()
	{
		EmpresaBds empresaBds = new EmpresaBds();
		empresaBds.setContato("contato");
		empresaBds.setEmail("mail@mail.com.br");
		empresaBds.setFone("33333333");
		empresaBds.setId(null);
		empresaBds.setNome("nome emp");
		empresaBds.setDdd("085");
		return empresaBds;
	}

    public void testFindAllProjection() throws Exception
    {
    	EmpresaBds e1 = EmpresaBdsFactory.getEntity();
    	e1 = empresaBdsDao.save(e1);
    	
    	EmpresaBds e2 = EmpresaBdsFactory.getEntity();
    	e2 = empresaBdsDao.save(e2);

    	Collection<EmpresaBds> empresaBdss = new ArrayList<EmpresaBds>();
    	empresaBdss.add(e1);
    	empresaBdss.add(e2);

    	Long[] ids = {e1.getId(),e2.getId()};

    	Collection<EmpresaBds> retorno = empresaBdsDao.findAllProjection(ids);

    	assertEquals(2, retorno.size());
    }
    
    public void testFindByIdProjection() throws Exception
    {
    	EmpresaBds empresaBds = EmpresaBdsFactory.getEntity();
    	empresaBds = empresaBdsDao.save(empresaBds);
    	
    	assertEquals(empresaBds, empresaBdsDao.findByIdProjection(empresaBds.getId()));
    }
	
	public GenericDao<EmpresaBds> getGenericDao()
	{
		return empresaBdsDao;
	}

	public void setEmpresaBdsDao(EmpresaBdsDao empresaBdsDao)
	{
		this.empresaBdsDao = empresaBdsDao;
	}
}
