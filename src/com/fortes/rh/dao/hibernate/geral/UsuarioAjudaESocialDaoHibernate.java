package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.UsuarioAjudaESocialDao;
import com.fortes.rh.model.geral.UsuarioAjudaESocial;

public class UsuarioAjudaESocialDaoHibernate extends GenericDaoHibernate<UsuarioAjudaESocial> implements UsuarioAjudaESocialDao
{
	@SuppressWarnings("unchecked")
	public Collection<String> findByUsuarioId(Long usuarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "uae");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("uae.telaAjuda"), "telaAjuda");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("uae.usuario.id", usuarioId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return criteria.list();
	}
}