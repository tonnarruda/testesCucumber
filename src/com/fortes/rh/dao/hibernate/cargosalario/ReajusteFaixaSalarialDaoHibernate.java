package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.ReajusteFaixaSalarialDao;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;

@SuppressWarnings("unchecked")
public class ReajusteFaixaSalarialDaoHibernate extends GenericDaoHibernate<ReajusteFaixaSalarial> implements ReajusteFaixaSalarialDao
{
	public Collection<ReajusteFaixaSalarial> findByTabelaReajusteColaboradorId(	Long tabelaReajusteColaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "rfs");
		criteria.createCriteria("rfs.faixaSalarial", "fs", Criteria.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "c", Criteria.LEFT_JOIN);

		criteria.add(Expression.eq("rfs.tabelaReajusteColaborador.id", tabelaReajusteColaboradorId));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.addOrder(Order.asc("fs.nome"));

		return criteria.list();
	}
}