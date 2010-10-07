package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;

@SuppressWarnings("unchecked")
public class CertificacaoDaoHibernate extends GenericDaoHibernate<Certificacao> implements CertificacaoDao
{
	public Collection<Certificacao> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<Certificacao> findAllSelect(Long empresaId, String nomeBusca)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		if(StringUtils.isNotBlank(nomeBusca))
			criteria.add(Restrictions.sqlRestriction("normalizar(this_.nome) ilike  normalizar(?)", "%" + nomeBusca + "%", Hibernate.STRING));
		
		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Certificacao> findByFaixa(Long faixaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		criteria.createCriteria("c.faixaSalarials", "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("f.id", faixaId));
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<MatrizTreinamento> findMatrizTreinamento(Collection<Long> faixaIds)
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento(cert.id, cert.nome, faixa.nome, cargo.nome, curso.nome, curso.id) ");
		hql.append("from Certificacao cert ");
		hql.append("left join cert.cursos curso ");
		hql.append("left join cert.faixaSalarials faixa ");
		hql.append("left join faixa.cargo cargo ");
		hql.append("where faixa.id in (:faixasIds) ");

		hql.append("group by cert.id, cert.nome, faixa.nome, cargo.nome, curso.nome, curso.id ");
		hql.append("order by cargo.nome, faixa.nome, cert.nome, curso.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setParameterList("faixasIds", faixaIds, Hibernate.LONG);

		return query.list();
	}

	public Certificacao findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (Certificacao) criteria.uniqueResult();
	}
}