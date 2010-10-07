package com.fortes.rh.dao.hibernate.avaliacao;

import java.util.Collection;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public class PeriodoExperienciaDaoHibernate extends GenericDaoHibernate<PeriodoExperiencia> implements PeriodoExperienciaDao
{

	public Collection<PeriodoExperiencia> findAllSelect(Long empresaId)
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new PeriodoExperiencia(p.id, p.dias, p.empresa.id) ");
		hql.append("from PeriodoExperiencia p ");
		hql.append("where p.empresa.id = :empresaId order by p.dias");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		
		return query.list();
	}
}
