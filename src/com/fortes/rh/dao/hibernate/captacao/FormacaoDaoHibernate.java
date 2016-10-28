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
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.Colaborador;

@Component
@SuppressWarnings("unchecked")
public class FormacaoDaoHibernate extends GenericDaoHibernate<Formacao> implements FormacaoDao
{
	public void removeCandidato(Candidato candidato)
	{
		String queryHQL = "delete from Formacao f where f.candidato.id = :valor";

		getSession().createQuery(queryHQL).setLong("valor",candidato.getId()).executeUpdate();
	}

	public void removeColaborador(Colaborador colaborador)
	{
		String queryHQL = "delete from Formacao e where e.colaborador.id = :valor";

		getSession().createQuery(queryHQL).setLong("valor",colaborador.getId()).executeUpdate();
	}

	public Collection<Formacao> findByColaborador(Long colaboradorId)
	{
		Criteria criteria = getSession().createCriteria(Formacao.class, "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.situacao"), "situacao");
		p.add(Projections.property("f.tipo"), "tipo");
		p.add(Projections.property("f.curso"), "curso");
		p.add(Projections.property("f.local"), "local");
		p.add(Projections.property("f.conclusao"), "conclusao");
		p.add(Projections.property("f.areaFormacao"), "areaFormacao");
		p.add(Projections.property("f.candidato.id"), "candidatoId");
		p.add(Projections.property("f.colaborador.id"), "colaboradorId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("f.colaborador.id", colaboradorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Formacao.class));
		return criteria.list();
	}

	public Collection<Formacao> findInCandidatos(Long[] candidatoIds)
	{
		Criteria criteria = getSession().createCriteria(Formacao.class, "f");
		criteria.createCriteria("f.candidato", "cand", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.situacao"), "situacao");
		p.add(Projections.property("f.tipo"), "tipo");
		p.add(Projections.property("f.curso"), "curso");
		p.add(Projections.property("f.local"), "local");
		p.add(Projections.property("f.conclusao"), "conclusao");
		p.add(Projections.property("f.areaFormacao"), "areaFormacao");
		p.add(Projections.property("cand.id"), "candidatoId");

		criteria.setProjection(p);

		criteria.add(Expression.in("cand.id", candidatoIds));

		criteria.addOrder(Order.asc("cand.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Formacao.class));
		return criteria.list();
	}

	public Collection<Formacao> findByCandidato(Long candidatoId)
	{
		Criteria criteria = getSession().createCriteria(Formacao.class, "f");
		criteria.createCriteria("f.areaFormacao", "a", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.situacao"), "situacao");
		p.add(Projections.property("f.tipo"), "tipo");
		p.add(Projections.property("f.curso"), "curso");
		p.add(Projections.property("f.local"), "local");
		p.add(Projections.property("f.conclusao"), "conclusao");
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("a.id"), "areaFormacaoId");
		p.add(Projections.property("a.nome"), "areaFormacaoNome");
		p.add(Projections.property("f.colaborador.id"), "colaboradorId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("f.candidato.id", candidatoId));

		criteria.addOrder(Order.asc("f.conclusao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Formacao.class));
		return criteria.list();
	}

}