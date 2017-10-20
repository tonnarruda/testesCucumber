package com.fortes.rh.test.dao.hibernate.#NOME_PACOTE#;

import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.#NOME_PACOTE#.#NOME_CLASSE#Dao;
import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.#NOME_PACOTE#.#NOME_CLASSE#Factory;

public class #NOME_CLASSE#DaoHibernateTest extends GenericDaoHibernateTest_JUnit4<#NOME_CLASSE#>
{
	@Autowired
	private #NOME_CLASSE#Dao #NOME_CLASSE_MINUSCULO#Dao;

	@Override
	public #NOME_CLASSE# getEntity()
	{
		return #NOME_CLASSE#Factory.getEntity();
	}

	public GenericDao<#NOME_CLASSE#> getGenericDao()
	{
		return #NOME_CLASSE_MINUSCULO#Dao;
	}
}