package com.fortes.rh.test.dao.hibernate.#NOME_PACOTE#;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.#NOME_PACOTE#.#NOME_CLASSE#Dao;
import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.#NOME_PACOTE#.#NOME_CLASSE#Factory;

public class #NOME_CLASSE#DaoHibernateTest extends GenericDaoHibernateTest<#NOME_CLASSE#>
{
	private #NOME_CLASSE#Dao #NOME_CLASSE_MINUSCULO#Dao;

	@Override
	public #NOME_CLASSE# getEntity()
	{
		return #NOME_CLASSE#Factory.getEntity();
	}

	@Override
	public GenericDao<#NOME_CLASSE#> getGenericDao()
	{
		return #NOME_CLASSE_MINUSCULO#Dao;
	}

	public void set#NOME_CLASSE#Dao(#NOME_CLASSE#Dao #NOME_CLASSE_MINUSCULO#Dao)
	{
		this.#NOME_CLASSE_MINUSCULO#Dao = #NOME_CLASSE_MINUSCULO#Dao;
	}
}