package com.fortes.rh.test.dao.hibernate.captacao;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.NivelCompetenciaHistoricoDao;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.NivelCompetenciaHistoricoFactory;

public class NivelCompetenciaHistoricoDaoHibernateTest extends GenericDaoHibernateTest<NivelCompetenciaHistorico>
{
	private NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao;

	@Override
	public NivelCompetenciaHistorico getEntity()
	{
		return NivelCompetenciaHistoricoFactory.getEntity();
	}

	@Override
	public GenericDao<NivelCompetenciaHistorico> getGenericDao()
	{
		return nivelCompetenciaHistoricoDao;
	}

	public void setNivelCompetenciaHistoricoDao(NivelCompetenciaHistoricoDao nivelCompetenciaHistoricoDao)
	{
		this.nivelCompetenciaHistoricoDao = nivelCompetenciaHistoricoDao;
	}
}
