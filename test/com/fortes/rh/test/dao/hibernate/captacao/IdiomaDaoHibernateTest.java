package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.IdiomaDao;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class IdiomaDaoHibernateTest extends GenericDaoHibernateTest<Idioma>
{
	private IdiomaDao idiomaDao;

	public Idioma getEntity()
	{
		Idioma idioma = new Idioma();
		
		idioma.setId(null);
		idioma.setNome("nome");
		return idioma;
	}

	public GenericDao<Idioma> getGenericDao()
	{
		return idiomaDao;
	}

	public void setIdiomaDao(IdiomaDao idiomaDao)
	{
		this.idiomaDao = idiomaDao;
	}

}
