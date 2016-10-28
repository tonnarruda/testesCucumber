package com.fortes.rh.dao.hibernate.pesquisa;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.pesquisa.QuestionarioDao;
import com.fortes.rh.model.pesquisa.Questionario;

@Component
@SuppressWarnings("unchecked")
public class QuestionarioDaoHibernate extends GenericDaoHibernate<Questionario> implements QuestionarioDao
{
	public Questionario findByIdProjection(Long questionarioId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		criteria.createCriteria("q.empresa", "e");
		criteria.createCriteria("q.fichaMedica", "f", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("q.id"), "id");
		p.add(Projections.property("q.cabecalho"), "cabecalho");
		p.add(Projections.property("q.titulo"), "titulo");
		p.add(Projections.property("q.liberado"), "liberado");
		p.add(Projections.property("q.anonimo"), "anonimo");
		p.add(Projections.property("q.aplicarPorAspecto"), "aplicarPorAspecto");
		p.add(Projections.property("q.tipo"), "tipo");
		p.add(Projections.property("q.dataInicio"), "dataInicio");
		p.add(Projections.property("q.dataFim"), "dataFim");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		p.add(Projections.property("f.rodape"), "projectionFichaMedicaRodape");
		p.add(Projections.property("f.id"), "projectionFichaMedicaId");

		criteria.setProjection(p);
		//condição
		criteria.add(Expression.eq("q.id", questionarioId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (Questionario) criteria.uniqueResult();
	}

	public boolean checarQuestionarioLiberado(Questionario questionario)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("q.liberado"), "liberado");

		criteria.setProjection(p);
		criteria.add(Expression.eq("q.id", questionario.getId()));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (Boolean) criteria.uniqueResult();
	}

	public void aplicarPorAspecto(Long questionarioId, boolean aplicarPorAspecto)
	{
		String queryHQL = "update Questionario q set q.aplicarPorAspecto = :aplicarPorAspecto where q.id = :questionarioId";

		Query query = getSession().createQuery(queryHQL);

		query.setBoolean("aplicarPorAspecto", aplicarPorAspecto);
		query.setLong("questionarioId", questionarioId);

		query.executeUpdate();
	}

	public void liberarQuestionario(Long questionarioId)
	{
		String hql = "update Questionario set liberado = :liberado where id = :id";

		Query query = getSession().createQuery(hql);

		query.setLong("id", questionarioId);
		query.setBoolean("liberado", true);

		query.executeUpdate();
	}

	public Collection<Questionario> findQuestionarioNaoLiberados(Date questionarioInicio)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		criteria.createCriteria("q.empresa", "e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("q.id"), "id");
		p.add(Projections.property("q.titulo"), "titulo");
		p.add(Projections.property("q.dataInicio"), "dataInicio");
		p.add(Projections.property("q.tipo"), "tipo");
		p.add(Projections.property("e.id"), "projectionEmpresaId");
		p.add(Projections.property("e.emailRespRH"), "projectionEmailRespRH");
		p.add(Projections.property("e.emailRemetente"), "projectionEmailRemetente");

		criteria.setProjection(p);
		criteria.add(Expression.eq("q.dataInicio", questionarioInicio));
		criteria.add(Expression.eq("q.liberado", false));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<Questionario> findQuestionarioPorUsuario(Long usuarioId)
	{
		return montaQuery("c.usuario.id", usuarioId);
	}

	public Collection<Questionario> findQuestionario(Long colaboradorId) 
	{
		return montaQuery("c.id", colaboradorId);
	}
	
	public Collection<Questionario> montaQuery(String property, Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "q");
		criteria.createCriteria("q.colaboradorQuestionarios", "cq", Criteria.LEFT_JOIN);
		criteria.createCriteria("cq.colaborador", "c");
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("q.id"), "id");
		p.add(Projections.property("q.titulo"), "titulo");
		p.add(Projections.property("q.dataInicio"), "dataInicio");
		p.add(Projections.property("q.tipo"), "tipo");
		
		criteria.setProjection(p);
		Date hoje = new Date();
		
		criteria.add(Expression.eq(property, id));
		
		criteria.add(Expression.eq("cq.respondida", false));
		criteria.add(Expression.eq("q.liberado", true));
		criteria.add(Expression.le("q.dataInicio", hoje));
		criteria.add(Expression.ge("q.dataFim", hoje));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}
}