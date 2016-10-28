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
import com.fortes.rh.dao.pesquisa.FichaMedicaDao;
import com.fortes.rh.model.pesquisa.FichaMedica;

@Component
@SuppressWarnings("unchecked")
public class FichaMedicaDaoHibernate extends GenericDaoHibernate<FichaMedica> implements FichaMedicaDao
{
	public FichaMedica findByIdProjection(Long fichaMedicaId) throws Exception
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.ativa"), "ativa");
		p.add(Projections.property("p.rodape"), "rodape");
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

		criteria.add(Expression.eq("p.id", fichaMedicaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (FichaMedica) criteria.uniqueResult();
	}

	public Collection<FichaMedica> findToList(Long empresaId, int page, int pagingSize)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new FichaMedica(ent.id, ent.rodape, count(cq.id), ent.ativa, q.id, q.titulo, q.liberado, q.tipo, q.aplicarPorAspecto, emp.id) ");
		hql.append("from FichaMedica as ent ");
		hql.append("	left join ent.questionario as q ");
		hql.append("	left join q.colaboradorQuestionarios as cq ");
		hql.append("	left join q.empresa as emp ");
		hql.append("where ");
		hql.append("    emp.id=:empId ");
		hql.append("group by  ");
		hql.append("    ent.id,ent.rodape, ent.ativa, q.id, q.titulo, q.liberado, q.tipo, q.aplicarPorAspecto, emp.id ");
		hql.append("order by ");
		hql.append("    q.titulo asc ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empId", empresaId);

		query.setFirstResult((page - 1) * pagingSize);
		query.setMaxResults(pagingSize);

		Collection<FichaMedica> fichaMedicas = query.list();

		return fichaMedicas;
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

		return criteria.list().isEmpty() ? 0 : (Integer) criteria.list().get(0);
	}

	public Collection<FichaMedica> findAllSelect(Long empresaId, Boolean ativa)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.empresa", "ent", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.rodape"), "rodape");
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

	public Collection<FichaMedica> findByColaborador(Long empresaId, Long colaboradorId)
	{
		StringBuilder hql = new StringBuilder("select new FichaMedica(q.titulo, cq.respondidaEm) ");
		hql.append("from FichaMedica fm ");
		hql.append("join fm.questionario q ");
		hql.append("join q.colaboradorQuestionarios cq ");
		hql.append("where q.empresa.id = :empresaId ");
		hql.append("and cq.colaborador.id = :colaboradorId ");

		hql.append("order by cq.respondidaEm ASC");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setLong("colaboradorId", colaboradorId);

		return query.list();
	}

	public FichaMedica findByQuestionario(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.ativa"), "ativa");
		p.add(Projections.property("p.rodape"), "rodape");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.cabecalho"), "projectionQuestionarioCabecalho");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");

		criteria.setProjection(p);

		criteria.add(Expression.eq("q.id", questionarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (FichaMedica) criteria.uniqueResult();
	}

}