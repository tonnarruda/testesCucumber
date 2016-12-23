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

		ProjectionList projections = adicionaCamposBase();
		criteria.setProjection(projections);

		criteria.add(Expression.in("e.candidato.id", candidatoIds));

		criteria.addOrder(Order.asc("e.candidato.id"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Experiencia.class));
		
		return criteria.list();
	}

	public Collection<Experiencia> findByColaborador(Colaborador colaborador)
	{
		Criteria criteria = getSession().createCriteria(Experiencia.class, "e");
		criteria.createCriteria("e.cargo", "c", Criteria.LEFT_JOIN);

		ProjectionList projections = adicionaCamposBase();
		criteria.setProjection(projections);

		criteria.add(Expression.eq("e.colaborador", colaborador));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Experiencia.class));
		
		return criteria.list();
	}
	
	public Collection<Experiencia> findByCandidato(Long candidatoId)
	{
		Criteria criteria = getSession().createCriteria(Experiencia.class, "e");

		ProjectionList projections = adicionaCamposBase();
		criteria.setProjection(projections);

		criteria.add(Expression.eq("e.candidato.id", candidatoId));

		criteria.addOrder(Order.desc("e.dataAdmissao"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Experiencia.class));
		
		return criteria.list();
	}

	public Experiencia findByIdProjection(Long experienciaId)
	{
		Criteria criteria = getSession().createCriteria(Experiencia.class, "e");

		ProjectionList projections = adicionaCamposBase();
		criteria.setProjection(projections);

		criteria.add(Expression.eq("e.id", experienciaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Experiencia.class));
		
		return (Experiencia) criteria.uniqueResult();
	}

	private ProjectionList adicionaCamposBase()
	{
		ProjectionList projections = Projections.projectionList().create();
		
		projections.add(Projections.property("e.id"), "id");
		projections.add(Projections.property("e.empresa"), "empresa");
		projections.add(Projections.property("e.cargo"), "cargo");
		projections.add(Projections.property("e.colaborador"), "colaborador");
		projections.add(Projections.property("e.candidato"), "candidato");
		projections.add(Projections.property("e.dataAdmissao"), "dataAdmissao");
		projections.add(Projections.property("e.dataDesligamento"), "dataDesligamento");
		projections.add(Projections.property("e.observacao"), "observacao");
		projections.add(Projections.property("e.nomeMercado"), "nomeMercado");
		projections.add(Projections.property("e.salario"), "salario");
		projections.add(Projections.property("e.motivoSaida"), "motivoSaida");
		projections.add(Projections.property("e.contatoEmpresa"), "contatoEmpresa");
		
		return projections;
	}

	public void desvinculaCargo(Long cargoId, String cargoNomeMercado)
	{
		String queryHQL = "update Experiencia set nomemercado = :cargoNomeMercado, cargo.id = null where cargo.id = :cargoId";
		
		Query query = getSession().createQuery(queryHQL);
		
		query.setLong("cargoId",cargoId);
		query.setString("cargoNomeMercado",cargoNomeMercado);
		
		query.executeUpdate();		
	}
}