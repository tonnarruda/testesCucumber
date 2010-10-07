package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.MotivoSolicitacaoDao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.MotivoSolicitacaoFactory;

public class MotivoSolicitacaoDaoHibernateTest extends GenericDaoHibernateTest<MotivoSolicitacao>
{
	private MotivoSolicitacaoDao motivoSolicitacaoDao;

	public MotivoSolicitacao getEntity()
	{
		return MotivoSolicitacaoFactory.getEntity();
	}

	public GenericDao<MotivoSolicitacao> getGenericDao()
	{
		return motivoSolicitacaoDao;
	}

	public void setMotivoSolicitacaoDao(MotivoSolicitacaoDao motivoSolicitacaoDao)
	{
		this.motivoSolicitacaoDao = motivoSolicitacaoDao;
	}

}