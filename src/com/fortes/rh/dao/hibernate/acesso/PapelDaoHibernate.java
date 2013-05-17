/* Autor: Igo Coelho
 * Data: 29/05/2006
 * Requisito: RFA32 */
package com.fortes.rh.dao.hibernate.acesso;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.acesso.PapelDao;
import com.fortes.rh.model.acesso.Papel;

@SuppressWarnings("unchecked")
public class PapelDaoHibernate extends GenericDaoHibernate<Papel> implements PapelDao
{
	public Collection<Papel> findByPerfil(Long perfilId) 
	{
		StringBuilder hql = new StringBuilder("select new Papel(pa.id, pa.nome, pa.ordem, pa.papelMae.id) ");
		hql.append("from Perfil pe ");
		hql.append("join pe.papeis pa ");
		hql.append("where pe.id = :perfilId ");
		hql.append("order by pa.papelMae.id desc, pa.ordem ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("perfilId", perfilId);
		
		return query.list();
	}

	public Collection<Papel> findNotIn(Collection<Long> ids) 
	{
		Criteria criteria = getSession().createCriteria(Papel.class,"p");

		if(ids != null && !ids.isEmpty())
			criteria.add(Expression.not(Expression.in("p.id", ids)));
		
		criteria.addOrder(Order.asc("p.ordem"));
		
		return criteria.list();
	}
}