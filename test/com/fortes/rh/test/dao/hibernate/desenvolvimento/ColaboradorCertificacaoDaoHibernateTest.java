package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;

public class ColaboradorCertificacaoDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorCertificacao>
{
	private ColaboradorCertificacaoDao colaboradorCertificacaoDao;

	@Override
	public ColaboradorCertificacao getEntity()
	{
		return ColaboradorCertificacaoFactory.getEntity();
	}

	@Override
	public GenericDao<ColaboradorCertificacao> getGenericDao()
	{
		return colaboradorCertificacaoDao;
	}

	public void setColaboradorCertificacaoDao(ColaboradorCertificacaoDao colaboradorCertificacaoDao)
	{
		this.colaboradorCertificacaoDao = colaboradorCertificacaoDao;
	}
}
