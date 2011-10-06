package com.fortes.rh.dao.hibernate.geral;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ColaboradorPeriodoExperienciaAvaliacaoDao;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public class ColaboradorPeriodoExperienciaAvaliacaoDaoHibernate extends GenericDaoHibernate<ColaboradorPeriodoExperienciaAvaliacao> implements ColaboradorPeriodoExperienciaAvaliacaoDao
{

	public void removeByColaborador(Long colaboradorId) 
	{
		String queryHQL = "delete from ColaboradorPeriodoExperienciaAvaliacao c where c.colaborador.id = :colaboradorId";
		getSession().createQuery(queryHQL).setLong("colaboradorId", colaboradorId).executeUpdate();	
	}

}