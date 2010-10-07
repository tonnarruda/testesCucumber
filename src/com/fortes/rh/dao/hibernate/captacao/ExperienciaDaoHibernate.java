package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ExperienciaDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.geral.Colaborador;

@SuppressWarnings("unchecked")
public class ExperienciaDaoHibernate extends GenericDaoHibernate<Experiencia> implements ExperienciaDao
{
	public void removeCandidato(Candidato candidato)
	{
		String queryHQL = "delete from Experiencia e where e.candidato.id = :valor";

		getSession().createQuery(queryHQL).setLong("valor",candidato.getId()).executeUpdate();
	}

	public void removeColaborador(Colaborador colaborador)
	{
		String queryHQL = "delete from Experiencia e where e.colaborador.id = :valor";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("valor", colaborador.getId());

		query.executeUpdate();
	}

	public Collection<Experiencia> findInCandidatos(Long[] candidatoIds)
	{
		Criteria criteria = getSession().createCriteria(Experiencia.class, "e");
		criteria.createCriteria("e.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("e.candidato", "cand", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.empresa"), "empresa");
		p.add(Projections.property("e.colaborador"), "colaborador");
		p.add(Projections.property("e.candidato"), "candidato");
		p.add(Projections.property("e.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("e.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("e.observacao"), "observacao");
		p.add(Projections.property("e.nomeMercado"), "nomeMercado");
		p.add(Projections.property("cand.id"), "candidatoId");
		p.add(Projections.property("c.nomeMercado"), "cargoNomeMercado");
		criteria.setProjection(p);

		criteria.add(Expression.in("cand.id", candidatoIds));

		criteria.addOrder(Order.asc("cand.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Experiencia.class));
		return criteria.list();
	}

	public Collection<Experiencia> findByColaborador(Colaborador colaborador)
	{
		Criteria criteria = getSession().createCriteria(Experiencia.class, "e");
		criteria.createCriteria("e.cargo", "c", Criteria.LEFT_JOIN);
		criteria.createCriteria("e.candidato", "cand", Criteria.LEFT_JOIN);
		criteria.createCriteria("e.colaborador", "colab", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.empresa"), "empresa");
		p.add(Projections.property("e.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("e.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("e.observacao"), "observacao");
		p.add(Projections.property("e.nomeMercado"), "nomeMercado");
		p.add(Projections.property("e.salario"), "salario");
		p.add(Projections.property("cand.id"), "candidatoId");
		p.add(Projections.property("colab.id"), "colaboradorId");
		p.add(Projections.property("c.nomeMercado"), "cargoNomeMercado");
		p.add(Projections.property("c.id"), "cargoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.colaborador", colaborador));

		//criteria.addOrder(Order.asc("cand.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Experiencia.class));
		return criteria.list();
	}

	public Collection<Experiencia> findByCandidato(Long candidatoId)
	{
		Criteria criteria = getSession().createCriteria(Experiencia.class, "e");
		criteria.createCriteria("e.cargo", "c", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.empresa"), "empresa");
		p.add(Projections.property("e.nomeMercado"), "nomeMercado");
		p.add(Projections.property("e.dataAdmissao"), "dataAdmissao");
		p.add(Projections.property("e.dataDesligamento"), "dataDesligamento");
		p.add(Projections.property("e.observacao"), "observacao");
		p.add(Projections.property("e.salario"), "salario");
		p.add(Projections.property("e.motivoSaida"), "motivoSaida");
		
		p.add(Projections.property("c.id"), "cargoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.candidato.id", candidatoId));

		criteria.addOrder(Order.desc("e.dataAdmissao"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Experiencia.class));
		return criteria.list();
	}

}