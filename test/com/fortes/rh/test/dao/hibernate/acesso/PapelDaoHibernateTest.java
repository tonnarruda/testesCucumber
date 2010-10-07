package com.fortes.rh.test.dao.hibernate.acesso;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class PapelDaoHibernateTest extends GenericDaoHibernateTest<Papel>
{
	private PapelDao papelDao;
	
	public Papel getEntity()
	{
		Papel papel = new Papel();
		
		papel.setId(null);
		papel.setNome("nome do papel");
		papel.setCodigo("0");
		papel.setOrdem(0);
		papel.setPapelMae(null);
		papel.setUrl("url");
		
		return papel;
	}
	
	public void testFindPapeisAPartirDe()
	{
		Papel papel1 = new Papel();
		papel1.setId(null);
		papelDao.save(papel1);
		
		Papel papel2 = new Papel();
		papel2.setId(null);
		papelDao.save(papel2);
		
		Papel papel3 = new Papel();
		papel3.setId(null);
		papelDao.save(papel3);
		
		Collection<Papel> ids = papelDao.findPapeisAPartirDe(papel2.getId());
		
		assertEquals(2, ids.size());
		assertFalse(ids.contains(papel1));
	}
	
	public GenericDao<Papel> getGenericDao()
	{
		return papelDao;
	}

	public void setPapelDao(PapelDao papelDao)
	{
		this.papelDao = papelDao;
	}

}