package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;

@Component
@SuppressWarnings("unchecked")
public class ComissaoPeriodoDaoHibernate extends GenericDaoHibernate<ComissaoPeriodo> implements ComissaoPeriodoDao
{
	public Collection<ComissaoPeriodo> findByComissao(Long comissaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.aPartirDe"), "aPartirDe");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.comissao.id", comissaoId));

		criteria.addOrder(Order.desc("c.aPartirDe"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public ComissaoPeriodo findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.comissao", "com");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.aPartirDe"), "aPartirDe");
		p.add(Projections.property("com.id"), "projectionComissaoId");
		p.add(Projections.property("com.dataIni"), "projectionComissaoDataIni");
		p.add(Projections.property("com.dataFim"), "projectionComissaoDataFim");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (ComissaoPeriodo) criteria.uniqueResult();
	}

	public ComissaoPeriodo findProximo(ComissaoPeriodo comissaoPeriodo)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.aPartirDe"), "aPartirDe");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.comissao.id", comissaoPeriodo.getComissao().getId()));

		criteria.addOrder(Order.asc("c.aPartirDe"));
		criteria.add(Expression.gt("c.aPartirDe", comissaoPeriodo.getaPartirDe()));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		criteria.setMaxResults(1);

		return (ComissaoPeriodo) criteria.uniqueResult();
	}

	public Date maxDataComissaoPeriodo(Long comissaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.comissao", "com");
		criteria.setProjection(Projections.max("c.aPartirDe"));

		criteria.add(Expression.eq("com.id", comissaoId));

		return (Date) criteria.uniqueResult();
	}

	public boolean verificaComissaoNaMesmaData(ComissaoPeriodo comissaoPeriodo) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria.createCriteria("c.comissao", "com");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.aPartirDe", comissaoPeriodo.getaPartirDe()));
		criteria.add(Expression.ne("c.id", comissaoPeriodo.getId()));
		criteria.add(Expression.eq("com.id", comissaoPeriodo.getComissao().getId()));

		return criteria.list().size() > 0;
	}
}