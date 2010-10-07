package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.DependenteDao;
import com.fortes.rh.model.geral.Dependente;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;

public class DependenteDaoHibernateTest extends GenericDaoHibernateTest<Dependente> 
{
	private DependenteDao dependenteDao;

	public Dependente getEntity()
	{
		Dependente dependente = new Dependente(); 
		
		dependente.setId(null);
		dependente.setColaborador(null);
		dependente.setDataNascimento(new Date());
		dependente.setNome("nome do dependente");
		dependente.setSeqAc("0");
		
		return dependente;
	}

	public GenericDao<Dependente> getGenericDao()
	{
		return dependenteDao;
	}
	
	public void setDependenteDao(DependenteDao dependenteDao)
	{
		this.dependenteDao = dependenteDao;
	}

}