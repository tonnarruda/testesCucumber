package com.fortes.rh.test.dao.hibernate.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.GerenciadorComunicacaoDao;
import com.fortes.rh.model.geral.GerenciadorComunicacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.geral.GerenciadorComunicacaoFactory;

public class GerenciadorComunicacaoDaoHibernateTest extends GenericDaoHibernateTest<GerenciadorComunicacao>
{
	private GerenciadorComunicacaoDao gerenciadorComunicacaoDao;

	@Override
	public GerenciadorComunicacao getEntity()
	{
		return GerenciadorComunicacaoFactory.getEntity();
	}

	@Override
	public GenericDao<GerenciadorComunicacao> getGenericDao()
	{
		return gerenciadorComunicacaoDao;
	}

	public void setGerenciadorComunicacaoDao(GerenciadorComunicacaoDao gerenciadorComunicacaoDao)
	{
		this.gerenciadorComunicacaoDao = gerenciadorComunicacaoDao;
	}
}
