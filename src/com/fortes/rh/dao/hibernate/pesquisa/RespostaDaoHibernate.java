package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.RespostaDao;
import com.fortes.rh.model.pesquisa.Resposta;

@Component
public class RespostaDaoHibernate extends GenericDaoHibernate<Resposta> implements RespostaDao
{
	@SuppressWarnings("unchecked")
	public Collection<Resposta> findByPergunta(Long perguntaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"resposta");
		criteria.createCriteria("resposta.pergunta", "pergunta");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("resposta.id"), "id");
		p.add(Projections.property("resposta.texto"), "texto");
		p.add(Projections.property("resposta.ordem"), "ordem");
		p.add(Projections.property("resposta.peso"), "peso");
		p.add(Projections.property("pergunta.id"), "projectionPerguntaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("pergunta.id", perguntaId));

		criteria.addOrder(Order.asc("resposta.ordem"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<Resposta> findInPerguntaIds(Long[] perguntaIds)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"resposta");
		criteria.createCriteria("resposta.pergunta", "pergunta");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("resposta.id"), "id");
		p.add(Projections.property("resposta.texto"), "texto");
		p.add(Projections.property("resposta.ordem"), "ordem");
		p.add(Projections.property("resposta.peso"), "peso");
		p.add(Projections.property("pergunta.id"), "projectionPerguntaId");

		criteria.setProjection(p);
		criteria.add(Expression.in("pergunta.id", perguntaIds));

		criteria.addOrder(Order.asc("pergunta.ordem"));
		criteria.addOrder(Order.asc("resposta.ordem"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void removerRespostasDasPerguntas(Collection<Long> perguntaIds)
	{
		String queryHQL = "delete from Resposta r where r.pergunta.id in (:perguntaIds)";

		Query query = getSession().createQuery(queryHQL);

		query.setParameterList("perguntaIds", perguntaIds, StandardBasicTypes.LONG);

		query.executeUpdate();

	}
	
	public void removerRespostasDasPerguntasDaAvaliacao(Long avaliacaoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Resposta r where r.id in (");
		hql.append("	select r.id from Resposta as r left join r.pergunta as p where p.avaliacao.id = :avaliacaoId");
		hql.append(")");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("avaliacaoId", avaliacaoId);
		
		query.executeUpdate();
	}

}