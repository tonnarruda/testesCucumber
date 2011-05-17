package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Query;

import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;

public class ComoFicouSabendoVagaDaoHibernate extends GenericDaoHibernate<ComoFicouSabendoVaga> implements ComoFicouSabendoVagaDao
{

	@SuppressWarnings("unchecked")
	public Collection<ComoFicouSabendoVaga> findAllSemOutros() 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ComoFicouSabendoVaga(c.id, c.nome) ");
		hql.append("from ComoFicouSabendoVaga as c ");
		hql.append("	where c.id <> 1 ");
		hql.append("order by c.nome ");

		Query query = getSession().createQuery(hql.toString());

		return  query.list();
	}
}
