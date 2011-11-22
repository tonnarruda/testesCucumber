package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.CidadeDao;
import com.fortes.rh.model.geral.Cidade;

@SuppressWarnings("unchecked")
public class CidadeDaoHibernate extends GenericDaoHibernate<Cidade> implements CidadeDao
{
	public Collection<Cidade> findAllSelect(Long estadoId)
	{
		Criteria criteria = getSession().createCriteria(Cidade.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.uf.id", estadoId));
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cidade.class));

		return criteria.list();

	}

	public Collection<Cidade> findAllByUf(String uf)
	{
		Criteria criteria = getSession().createCriteria(Cidade.class, "c");
		criteria.createCriteria("c.uf", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.sigla", uf));
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cidade.class));

		return criteria.list();
	}

	public Cidade findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(Cidade.class, "c");
		criteria.createCriteria("c.uf", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("e.id"), "projectionUfId");
		p.add(Projections.property("e.sigla"), "projectionUfSigla");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", id));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cidade.class));

		return (Cidade) criteria.uniqueResult();

	}

	public Cidade findByCodigoAC(String codigoAC, String sigla)
	{
		Criteria criteria = getSession().createCriteria(Cidade.class, "c");
		criteria.createCriteria("c.uf", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		p.add(Projections.property("c.codigoAC"), "codigoAC");
		p.add(Projections.property("e.id"), "projectionUfId");
		p.add(Projections.property("e.sigla"), "projectionUfSigla");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.codigoAC", codigoAC));
		criteria.add(Expression.eq("e.sigla", sigla));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cidade.class));

		return (Cidade) criteria.uniqueResult();
	}

	public Cidade findByNome(String nome, Long estadoId) 
	{
		Criteria criteria = getSession().createCriteria(Cidade.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.uf.id", estadoId));
		criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nome + "%", Hibernate.STRING));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cidade.class));

		return (Cidade) criteria.uniqueResult();
	}

	public Collection<Cidade> findSemCodigoAC() {
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");

		criteria.setProjection(p);
		
		criteria.add(Expression.or(Expression.isNull("c.codigoAC"), Expression.eq("c.codigoAC","")));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();	
	}
}