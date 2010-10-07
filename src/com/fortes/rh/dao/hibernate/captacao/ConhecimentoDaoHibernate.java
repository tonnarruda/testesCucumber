/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.dao.hibernate.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.model.captacao.Conhecimento;

@SuppressWarnings("unchecked")
public class ConhecimentoDaoHibernate extends GenericDaoHibernate<Conhecimento> implements ConhecimentoDao
{
	public Collection<Conhecimento> findByAreaOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId)
	{
		if(areaOrganizacionalIds == null || areaOrganizacionalIds.length == 0)
			return new ArrayList();

		Criteria criteria = getSession().createCriteria(Conhecimento.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.createCriteria("c.areaOrganizacionals","ao");
		criteria.add(Expression.in("ao.id", areaOrganizacionalIds));
		criteria.add(Expression.eq("empresa.id", empresasId));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Conhecimento.class));
		return criteria.list();
	}

	public Collection<Conhecimento> findByAreaInteresse(Long[] areasInteressesIds, Long empresaId)
	{

		if(areasInteressesIds == null || areasInteressesIds.length == 0)
			return new ArrayList();

		Criteria criteria = getSession().createCriteria(Conhecimento.class, "c");
		criteria.createCriteria("c.areaOrganizacionals","ao", Criteria.LEFT_JOIN);
		criteria.createCriteria("ao.areasInteresse","ai", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.in("ai.id", areasInteressesIds));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		criteria.addOrder(Order.asc("c.nome"));

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Conhecimento.class));

		return criteria.list();
	}

	public Collection<Conhecimento> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Conhecimento.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaId));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Conhecimento.class));

		return criteria.list();
	}

	public Collection<Conhecimento> findByCargo(Long cargoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Conhecimento(co.id, co.nome) ");
		hql.append("from Cargo as c ");
		hql.append("join c.conhecimentos as co ");
		hql.append("	where c.id = :cargoId ");
		hql.append("order by co.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("cargoId", cargoId);

		return query.list();
	}

	public Conhecimento findByIdProjection(Long conhecimentoId)
	{
		Criteria criteria = getSession().createCriteria(Conhecimento.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.id", conhecimentoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Conhecimento.class));
		
		return (Conhecimento) criteria.uniqueResult();
	}

	public Collection<Conhecimento> findAllSelectDistinctNome()
	{
		Criteria criteria = getSession().createCriteria(Conhecimento.class, "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.distinct(Projections.property("c.nome")), "nome");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Conhecimento.class));
		
		return criteria.list();
	}

	public Collection<Conhecimento> findByCandidato(Long candidatoId)
	{
		String hql = "select new Conhecimento(co.id, co.nome) from Candidato c join c.conhecimentos co where c.id = :candidatoId order by co.nome";
		Query query = getSession().createQuery(hql);
		query.setLong("candidatoId", candidatoId);

		return query.list();
	}

	public Collection<Conhecimento> findSincronizarConhecimentos(Long empresaOrigemId)
	{
		Criteria criteria = getSession().createCriteria(Conhecimento.class, "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.empresa.id", empresaOrigemId));

		criteria.addOrder(Order.asc("c.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Conhecimento.class));

		return criteria.list();
	}
}