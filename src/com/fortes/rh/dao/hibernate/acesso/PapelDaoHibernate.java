/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.dao.hibernate.acesso;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.model.acesso.Papel;

@SuppressWarnings("unchecked")
public class PapelDaoHibernate extends GenericDaoHibernate<Papel> implements PapelDao
{

	public Collection<Papel> findPapeisAPartirDe(Long atualizaPapeisIdsAPartirDe) {
		
		Criteria criteria = getSession().createCriteria(Papel.class,"p");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.papelMae.id"), "papelMaeId");
		criteria.setProjection(p);
		
		criteria.add(Expression.ge("p.id", atualizaPapeisIdsAPartirDe));
		criteria.addOrder(Order.asc("p.id"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Papel.class));
		return criteria.list();
	}
}