package com.fortes.rh.dao.hibernate.acesso;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.model.acesso.Perfil;

@SuppressWarnings("unchecked")
public class PerfilDaoHibernate extends GenericDaoHibernate<Perfil> implements PerfilDao {

	public Collection<Perfil> findPerfisByCodigoPapel(String codigo) {
		StringBuilder hql = new StringBuilder(
				"select distinct new Perfil(pe.id,pe.nome) ");
		hql.append("from Perfil pe ");
		hql.append("join pe.papeis pa ");
		hql.append("where pa.codigo = :codigoPapel ");

		Query query = getSession().createQuery(hql.toString());
		query.setString("codigoPapel", codigo);

		return query.list();
	}
	
	public Collection<Perfil> findAll(Integer page, Integer pagingSize) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");

		criteria.setProjection(p);

		if(page != null)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);			
		}
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Perfil.class));

		return criteria.list();

	}

	public Collection<Perfil> findByIds(Long[] perfisIds) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		
		criteria.setProjection(p);
		if (perfisIds != null && perfisIds.length > 0)
			criteria.add(Expression.in("c.id", perfisIds));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Perfil.class));
		
		return criteria.list();
		
	}

	public Integer getCount() 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.setProjection(Projections.rowCount());

		return (Integer) criteria.uniqueResult();
	}

	public void removePerfilPapelByPapelId(Long papelId) {
		String[] sql = new String[] { "delete from perfil_papel where papeis_id ="  + papelId };
		JDBCConnection.executeQuery(sql);
	}

}