package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFuncao;

@SuppressWarnings("unchecked")
public class RiscoDaoHibernate extends GenericDaoHibernate<Risco> implements RiscoDao
{
	public List findEpisByRisco(Long riscoId)
	{
		String queryHQL = "select distinct e.id, e.nome from Risco r left join r.epis e where r.id = :id";

		return getSession().createQuery(queryHQL).setLong("id", riscoId).list();
	}
	
	public Collection<Risco> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "risco");
		
		criteria.setFetchMode("epis", FetchMode.JOIN);
		
		criteria.add(Expression.eq("risco.empresa.id", empresaId));
		
		criteria.addOrder(Order.asc("risco.descricao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}
}