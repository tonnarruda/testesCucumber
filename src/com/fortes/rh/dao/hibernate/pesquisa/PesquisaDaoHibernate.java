package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.PesquisaDao;
import com.fortes.rh.model.pesquisa.Pesquisa;

@SuppressWarnings("unchecked")
public class PesquisaDaoHibernate extends GenericDaoHibernate<Pesquisa> implements PesquisaDao
{
	public Pesquisa findByIdProjection(Long pesquisaId) throws Exception
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("p.id"), "id");
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

		criteria.add(Expression.eq("p.id", pesquisaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Pesquisa) criteria.uniqueResult();
	}

	public Pesquisa findByQuestionario(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q");
		criteria.createCriteria("q.empresa", "e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");
		p.add(Projections.property("q.dataInicio"), "projectionQuestionarioDataInicio");
		p.add(Projections.property("q.dataFim"), "projectionQuestionarioDataFim");
		p.add(Projections.property("q.liberado"), "projectionQuestionarioLiberado");
		p.add(Projections.property("q.anonimo"), "projectionQuestionarioAnonimo");
		p.add(Projections.property("e.emailRemetente"), "projectionEmailRemetente");

		criteria.setProjection(p);

		criteria.add(Expression.eq("q.id", questionarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Pesquisa) criteria.uniqueResult();
	}

	public Collection<Pesquisa> findToList(Long empresaId, int page, int pagingSize)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q", Criteria.LEFT_JOIN);
		criteria.createCriteria("q.empresa", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("q.id"), "projectionQuestionarioId");
		p.add(Projections.property("q.dataInicio"), "projectionQuestionarioDataInicio");
		p.add(Projections.property("q.dataFim"), "projectionQuestionarioDataFim");
		p.add(Projections.property("q.titulo"), "projectionQuestionarioTitulo");
		p.add(Projections.property("q.liberado"), "projectionQuestionarioLiberado");
		p.add(Projections.property("q.tipo"), "projectionQuestionarioTipo");
		p.add(Projections.property("q.aplicarPorAspecto"), "projectionQuestionarioAplicarPorAspecto");
		p.add(Projections.property("e.id"), "projectionQuestionarioEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", empresaId));

		criteria.setFirstResult(((page - 1) * pagingSize));
		criteria.setMaxResults(pagingSize);

		criteria.addOrder(Order.desc("q.dataInicio"));
		criteria.addOrder(Order.asc("q.titulo"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
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

	public boolean verificaEmpresaDoQuestionario(Long pesquisaId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "p");
		criteria.createCriteria("p.questionario", "q");
		criteria.createCriteria("q.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		criteria.setProjection(p);

		criteria.add(Expression.eq("p.id", pesquisaId));
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

	public void removerPesquisaDoQuestionario(Long questionarioId)
	{
		String queryHQL = "delete from Pesquisa p where p.questionario.id = :questionarioId";

		getSession().createQuery(queryHQL).setLong("questionarioId",questionarioId).executeUpdate();
	}
}