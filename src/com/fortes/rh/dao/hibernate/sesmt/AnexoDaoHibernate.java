package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.AnexoDao;
import com.fortes.rh.model.sesmt.Anexo;

@Component
public class AnexoDaoHibernate extends GenericDaoHibernate<Anexo> implements AnexoDao
{
	@SuppressWarnings("unchecked")
	public Collection<Anexo> findByOrigem(Long origemId, char origem)
	{
		Criteria criteria = getSession().createCriteria(Anexo.class, "a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.url"), "url");
		p.add(Projections.property("a.nome"), "nome");
		p.add(Projections.property("a.observacao"), "observacao");
		p.add(Projections.property("a.origemId"), "origemId");
		p.add(Projections.property("a.origem"), "origem");
		criteria.setProjection(p);

		criteria.add(Expression.eq("a.origemId", origemId));
		criteria.add(Expression.eq("a.origem", origem));

		criteria.addOrder(Order.asc("a.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Anexo.class));

		return criteria.list();
	}
}