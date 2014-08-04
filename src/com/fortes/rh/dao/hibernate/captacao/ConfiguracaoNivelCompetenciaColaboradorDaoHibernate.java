package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.ConfiguracaoNivelCompetenciaColaboradorDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;

public class ConfiguracaoNivelCompetenciaColaboradorDaoHibernate extends GenericDaoHibernate<ConfiguracaoNivelCompetenciaColaborador> implements ConfiguracaoNivelCompetenciaColaboradorDao
{
	public ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.faixaSalarial", "fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("ca.id"), "projectionCargoId");
		p.add(Projections.property("ca.nome"), "projectionCargoNome");
		p.add(Projections.property("fs.id"), "projectionFaixaSalarialId");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("cncc.id", configuracaoNivelCompetenciaColaboradorId));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		
		return (ConfiguracaoNivelCompetenciaColaborador)criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.faixaSalarial", "fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("ca.nome"), "projectionCargoNome");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("co.id", colaboradorId));
		criteria.addOrder(Order.desc("cncc.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		
		return criteria.list();
	}

	public ConfiguracaoNivelCompetenciaColaborador checarHistoricoMesmaData(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");

		criteria.setProjection(p);
		
		if (configuracaoNivelCompetenciaColaborador.getId() != null)
			criteria.add(Expression.ne("cncc.id", configuracaoNivelCompetenciaColaborador.getId()));
		
		criteria.add(Expression.eq("cncc.data", configuracaoNivelCompetenciaColaborador.getData()));
		criteria.add(Expression.eq("co.id", configuracaoNivelCompetenciaColaborador.getColaborador().getId()));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		
		
		return (ConfiguracaoNivelCompetenciaColaborador)criteria.uniqueResult();
	}

	public void removeColaborador(Colaborador colaborador) {
		String queryHQL = "delete from ConfiguracaoNivelCompetenciaColaborador cncc where cncc.colaborador.id = :colaboradorId";

		Query query = getSession().createQuery(queryHQL);

		query.setLong("colaboradorId", colaborador.getId());

		query.executeUpdate();
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception {
		if(faixaIds != null && faixaIds.length > 0)
		{
			String hql = "delete ConfiguracaoNivelCompetenciaColaborador where faixaSalarial.id in (:faixaIds)";
			Query query = getSession().createQuery(hql);

			query.setParameterList("faixaIds", faixaIds, Hibernate.LONG);
			query.executeUpdate();		
		}
	}

	public ConfiguracaoNivelCompetenciaColaborador findByData(Date data, Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId) 
	{
		Criteria criteria = getSession().createCriteria(ConfiguracaoNivelCompetenciaColaborador.class, "cncc");
		criteria.createCriteria("cncc.colaborador", "co", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.colaboradorQuestionario", "cq", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cncc.faixaSalarial", "fs", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("fs.cargo", "ca", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cncc.id"), "id");
		p.add(Projections.property("cncc.data"), "data");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("ca.nome"), "projectionCargoNome");
		p.add(Projections.property("fs.nome"), "projectionFaixaSalarialNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.eq("co.id", colaboradorId));
		criteria.add(Expression.or(Expression.eq("cq.id", colaboradorQuestionarioId),Expression.isNull("cq.id")));
		criteria.add(Expression.or(Expression.eq("cq.avaliador.id", avaliadorId),Expression.isNull("cq.avaliador.id")));
		criteria.add(Expression.eq("cncc.data", data));
		criteria.addOrder(Order.desc("cncc.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ConfiguracaoNivelCompetenciaColaborador.class));
		criteria.setMaxResults(1);
		
		return (ConfiguracaoNivelCompetenciaColaborador) criteria.uniqueResult();
	}
}
