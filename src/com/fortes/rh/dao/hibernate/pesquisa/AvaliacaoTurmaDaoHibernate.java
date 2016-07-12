package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.AvaliacaoTurmaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;

@SuppressWarnings("unchecked")
public class AvaliacaoTurmaDaoHibernate extends GenericDaoHibernate<AvaliacaoTurma> implements AvaliacaoTurmaDao
{
	//ok
	public AvaliacaoTurma findByIdProjection(Long avaliacaoTurmaId) throws Exception
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

		criteria.add(Expression.eq("p.id", avaliacaoTurmaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (AvaliacaoTurma) criteria.uniqueResult();
	}
	//ok
	public Collection<AvaliacaoTurma> findToList(Long empresaId, int page, int pagingSize)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new AvaliacaoTurma(ent.id, count(cq.id), ent.ativa, q.id, q.titulo, q.liberado, q.tipo, q.aplicarPorAspecto, emp.id) ");
		hql.append("from AvaliacaoTurma as ent ");
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

		Collection<AvaliacaoTurma> avaliacaoTurmas = query.list();

		return avaliacaoTurmas;
	}
	//ok
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
	//ok
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

	//ok
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

	public Collection<AvaliacaoTurma> findAllSelect(Boolean ativa, Long... empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.empresa", "ent", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.ativa"), "ativa");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");
		p.add(Projections.property("ent.id"), "projectionQuestionarioEmpresaId");

		criteria.setProjection(p);

		if(empresaId != null && empresaId.length > 0)
			criteria.add(Expression.in("ent.id", empresaId));

		if(ativa != null)
			criteria.add(Expression.eq("e.ativa", ativa));

		criteria.addOrder(Order.asc("q.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<AvaliacaoTurma> findByTurma(Long turmaId) 
	{
		Criteria criteria = getSession().createCriteria(Turma.class, "t");
		criteria.createCriteria("t.turmaAvaliacaoTurmas", "tat");
		criteria.createCriteria("tat.avaliacaoTurma", "a");
		criteria.createCriteria("a.questionario", "q");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("t.id"), "projectionTurmaId");
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("tat.liberada"), "liberada");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");
		p.add(Projections.sqlProjection("(select count(*) from colaboradorquestionario cq where cq.questionario_id = q3_.id) as qtdColaboradorQuestionario" , 
				                        new String[] {"qtdColaboradorQuestionario"}, 
				                        new Type[] {Hibernate.INTEGER}));

		criteria.setProjection(Projections.distinct(p));
		criteria.add(Expression.eq("t.id", turmaId));

		criteria.addOrder(Order.asc("q.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

}