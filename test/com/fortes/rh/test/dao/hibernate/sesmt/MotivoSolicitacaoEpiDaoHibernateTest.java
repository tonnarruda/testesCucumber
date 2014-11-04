package com.fortes.rh.test.dao.hibernate.sesmt;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.sesmt.MotivoSolicitacaoEpiDao;
import com.fortes.rh.model.sesmt.MotivoSolicitacaoEpi;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.sesmt.MotivoSolicitacaoEpiFactory;

public class MotivoSolicitacaoEpiDaoHibernateTest extends GenericDaoHibernateTest<MotivoSolicitacaoEpi>
{
	private MotivoSolicitacaoEpiDao motivoSolicitacaoEpiDao;

	@Override
	public MotivoSolicitacaoEpi getEntity()
	{
		return MotivoSolicitacaoEpiFactory.getEntity();
	}

	@Override
	public GenericDao<MotivoSolicitacaoEpi> getGenericDao()
	{
		return motivoSolicitacaoEpiDao;
	}

	public void setMotivoSolicitacaoEpiDao(MotivoSolicitacaoEpiDao motivoSolicitacaoEpiDao)
	{
		this.motivoSolicitacaoEpiDao = motivoSolicitacaoEpiDao;
	}
}
