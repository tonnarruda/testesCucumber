package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.AspectoDao;
import com.fortes.rh.model.pesquisa.Aspecto;

@Component
public class AspectoDaoHibernate extends GenericDaoHibernate<Aspecto> implements AspectoDao
{
	public Aspecto findByIdProjection(Long aspectoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "aspecto");
		criteria.createCriteria("aspecto.questionario","questionario", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("aspecto.id"), "id");
		p.add(Projections.property("aspecto.nome"), "nome");
		p.add(Projections.property("questionario.id"), "projectionQuestionarioId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("aspecto.id", aspectoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setMaxResults(1);

		return (Aspecto) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<Aspecto> findByQuestionario(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "aspecto");
		criteria.createCriteria("aspecto.questionario","questionario", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("aspecto.id"), "id");
		p.add(Projections.property("aspecto.nome"), "nome");
		p.add(Projections.property("questionario.id"), "projectionQuestionarioId");
		
		criteria.setProjection(p);

        Disjunction disjunction = Expression.disjunction();
        disjunction.add(Expression.eq("questionario.id", questionarioId));
        disjunction.add(Expression.eq("avaliacao.id", questionarioId));
        criteria.add(disjunction);

		criteria.addOrder(Order.asc("aspecto.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Aspecto findByNomeQuestionario(String aspectoNome, Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "aspecto");
		criteria.createCriteria("aspecto.questionario","questionario", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("aspecto.id"), "id");
		p.add(Projections.property("aspecto.nome"), "nome");
		p.add(Projections.property("questionario.id"), "projectionQuestionarioId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("aspecto.nome", aspectoNome));
		criteria.add(Expression.eq("questionario.id", questionarioId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setMaxResults(1);

		return (Aspecto) criteria.uniqueResult();
	}

	public void removerAspectosDoQuestionario(Long questionarioId)
	{
		String queryHQL = "delete from Aspecto a where a.questionario.id = :questionarioId";

		getSession().createQuery(queryHQL).setLong("questionarioId",questionarioId).executeUpdate();
	}
	
	public void removerAspectosDaAvaliacao(Long avaliacaoId) 
	{
		String queryHQL = "delete from Aspecto a where a.avaliacao.id = :avaliacaoId";

		getSession().createQuery(queryHQL).setLong("avaliacaoId",avaliacaoId).executeUpdate();
	}
	
	@Override
	public boolean verificaEmpresa(Long aspectoId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "aspecto");
		criteria.createCriteria("aspecto.questionario", "questionario", Criteria.LEFT_JOIN);
		criteria.createCriteria("questionario.empresa", "empresa", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("aspecto.id"), "id");
		
		criteria.setProjection(p);

		criteria.add(Expression.eq("aspecto.id", aspectoId));
		criteria.add(Expression.eq("empresa.id", empresaId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return !criteria.list().isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> getNomesByAvaliacao(Long avaliacaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "aspecto");
		criteria.createCriteria("aspecto.avaliacao","ape", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("aspecto.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("avaliacao.id", avaliacaoId));

		criteria.addOrder(Order.asc("aspecto.nome"));
		return criteria.list();
	}

	public Aspecto findByNomeAvaliacao(String nome, Long avaliacaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "aspecto");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("aspecto.id"), "id");
		p.add(Projections.property("aspecto.nome"), "nome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("aspecto.nome", nome));
		criteria.add(Expression.eq("avaliacao.id", avaliacaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setMaxResults(1);

		return (Aspecto) criteria.uniqueResult();
	}


}