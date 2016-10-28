package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.CandidatoIdiomaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;

@Component
@SuppressWarnings("unchecked")
public class CandidatoIdiomaDaoHibernate extends GenericDaoHibernate<CandidatoIdioma> implements CandidatoIdiomaDao
{
	public void removeCandidato(Candidato candidato)
	{
		String queryHQL = "delete from CandidatoIdioma ci where ci.candidato.id = :valor";

		getSession().createQuery(queryHQL).setLong("valor",candidato.getId()).executeUpdate();
	}

	public Collection<CandidatoIdioma> findInCandidatos(Long[] candidatoIds)
	{
		Criteria criteria = getSession().createCriteria(CandidatoIdioma.class, "ci");
		criteria.createCriteria("ci.candidato", "cand", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ci.candidato"), "candidato");
		p.add(Projections.property("ci.idioma"), "idioma");
		p.add(Projections.property("ci.nivel"), "nivel");
		p.add(Projections.property("cand.id"), "candidatoId");

		criteria.setProjection(p);

		criteria.add(Expression.in("cand.id", candidatoIds));

		criteria.addOrder(Order.asc("cand.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoIdioma.class));
		return criteria.list();
	}

	public Collection<CandidatoIdioma> findByCandidato(Long candidatoId)
	{
		Criteria criteria = getSession().createCriteria(CandidatoIdioma.class, "ci");
		criteria.createCriteria("ci.idioma", "i", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ci.nivel"), "nivel");
		p.add(Projections.property("ci.id"), "id");
		p.add(Projections.property("ci.candidato.id"), "candidatoId");
		p.add(Projections.property("i.nome"), "projectionIdiomaNome");
		p.add(Projections.property("i.id"), "projectionIdiomaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ci.candidato.id", candidatoId));

		criteria.addOrder(Order.asc("i.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(CandidatoIdioma.class));
		return criteria.list();
	}
}