package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.EntrevistaDao;
import com.fortes.rh.model.pesquisa.Entrevista;

@Component
@SuppressWarnings("unchecked")
public class EntrevistaDaoHibernate extends GenericDaoHibernate<Entrevista> implements EntrevistaDao
{
	public Entrevista findByIdProjection(Long entrevistaId) throws Exception
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.ativa"), "ativa");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.dataInicio"), "projectionQuestionarioDataInicio");
		p.add(Projections.property("q.dataFim"), "projectionQuestionarioDataFim");
		p.add(Projections.property("q.cabecalho"), "projectionQuestionarioCabecalho");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");
		p.add(Projections.property("q.liberado"), "projectionQuestionarioLiberado");
		p.add(Projections.property("q.anonimo"), "projectionQuestionarioAnonimo");
		p.add(Projections.property("q.aplicarPorAspecto"), "projectionQuestionarioAplicarPorAspecto");
		p.add(Projections.property("q.tipo"), "projectionQuestionarioTipo");
		p.add(Projections.property("e.id"), "projectionQuestionarioEmpresaId");
		p.add(Projections.property("e.emailRespRH"), "projectionEmailRespRH");
		p.add(Projections.property("e.emailRemetente"), "projectionEmailRemetente");

		criteria.setProjection(p);

		criteria.add(Expression.eq("p.id", entrevistaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Entrevista) criteria.uniqueResult();
	}

	public Collection<Entrevista> findToList(Long empresaId, int page, int pagingSize)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Entrevista(ent.id, count(cq.id), ent.ativa, q.id, q.titulo, q.liberado, q.tipo, q.aplicarPorAspecto, emp.id) ");
		hql.append("from Entrevista as ent ");
		hql.append("	left join ent.questionario as q ");
		hql.append("	left join q.colaboradorQuestionarios as cq ");
		hql.append("	left join q.empresa as emp ");
		hql.append("where ");
		hql.append("    emp.id=:empId ");
		hql.append("group by  ");
		hql.append("    ent.id, ent.ativa, q.id, q.titulo, q.liberado, q.tipo, q.aplicarPorAspecto, emp.id ");
		hql.append("order by ");
		hql.append("    q.titulo asc ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empId", empresaId);

		query.setFirstResult((page - 1) * pagingSize);
		query.setMaxResults(pagingSize);

		Collection<Entrevista> entrevistas = query.list();

		return entrevistas;
	}

	public Long getIdByQuestionario(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("q.id", questionarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Long) criteria.uniqueResult();
	}

	public boolean verificaEmpresaDoQuestionario(Long entrevistaId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q");
		criteria.createCriteria("q.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("p.id", entrevistaId));
		criteria.add(Expression.eq("e.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		Long id = (Long) criteria.uniqueResult();

		return id != null;
	}

	public Integer getCount(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"p");
		criteria.createCriteria("p.questionario", "q");
		criteria.createCriteria("q.empresa", "e");

		criteria.setProjection(Projections.rowCount());

		criteria.add(Expression.eq("e.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Integer) criteria.list().get(0);
	}

	public Collection<Entrevista> findAllSelect(Long empresaId, Boolean ativa)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.empresa", "ent", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");
		p.add(Projections.property("ent.id"), "projectionQuestionarioEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ent.id", empresaId));
	
		if(ativa != null)
			criteria.add(Expression.eq("e.ativa", ativa));

		criteria.addOrder(Order.asc("q.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

}