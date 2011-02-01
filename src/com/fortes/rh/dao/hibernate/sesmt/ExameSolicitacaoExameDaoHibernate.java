package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class ExameSolicitacaoExameDaoHibernate extends GenericDaoHibernate<ExameSolicitacaoExame> implements ExameSolicitacaoExameDao
{
	public void removeAllBySolicitacaoExame(Long solicitacaoExameId)
	{
		String hql = "delete ExameSolicitacaoExame ex where ex.solicitacaoExame.id = :id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", solicitacaoExameId);
		query.executeUpdate();
	}

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long solicitacaoExameId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ese");
		criteria.createCriteria("ese.solicitacaoExame", "se");
		criteria.createCriteria("ese.exame", "e");
		criteria.createCriteria("ese.realizacaoExame", "re", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("ese.clinicaAutorizada", "cli", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ese.id"), "id");
		p.add(Projections.property("ese.periodicidade"), "periodicidade");
		p.add(Projections.property("se.id"), "projectionSolicitacaoExameId");
		p.add(Projections.property("se.data"), "projectionSolicitacaoExameData");
		p.add(Projections.property("e.id"), "projectionExameId");
		p.add(Projections.property("e.nome"), "projectionExameNome");
		p.add(Projections.property("re.id"), "projectionRealizacaoExameId");
		p.add(Projections.property("re.resultado"), "projectionRealizacaoExameResultado");
		p.add(Projections.property("re.observacao"), "projectionRealizacaoExameObservacao");
		p.add(Projections.property("cli.id"), "projectionClinicaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("se.id", solicitacaoExameId));

		criteria.addOrder( Order.asc("e.nome") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long[] solicitacaoExameIds)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ese");
		criteria.createCriteria("ese.solicitacaoExame", "se");
		criteria.createCriteria("ese.exame", "e");
		criteria.createCriteria("ese.realizacaoExame", "re", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ese.id"), "id");
		p.add(Projections.property("se.id"), "projectionSolicitacaoExameId");
		p.add(Projections.property("e.id"), "projectionExameId");
		p.add(Projections.property("e.nome"), "projectionExameNome");
		p.add(Projections.property("re.id"), "projectionRealizacaoExameId");
		p.add(Projections.property("re.resultado"), "projectionRealizacaoExameResultado");
		criteria.setProjection(p);

		criteria.add(Expression.in("se.id", solicitacaoExameIds));

		criteria.addOrder( Order.asc("e.nome") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public ExameSolicitacaoExame findDataSolicitacaoExame(Long colaboradorId, Long candidatoId, Long exameId)
	{
//		DetachedCriteria subQuery = DetachedCriteria.forClass(RealizacaoExame.class, "re2");
//		ProjectionList pSub = Projections.projectionList().create();
//
//		pSub.add(Projections.max("re2.data"));
//		subQuery.setProjection(pSub);
//
////		subQuery.add(Restrictions.sqlRestriction("this0__.colaborador_id=c1_.id"));
//		subQuery.add(Expression.le("re2.data", new Date()));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ese");
		criteria.createCriteria("ese.solicitacaoExame", "se");
		criteria.createCriteria("ese.exame", "e");
		criteria.createCriteria("ese.realizacaoExame", "re", CriteriaSpecification.LEFT_JOIN);
		
		if (colaboradorId != null)
			criteria.createCriteria("se.colaborador", "col", CriteriaSpecification.LEFT_JOIN);
		if (candidatoId != null)
			criteria.createCriteria("se.candidato", "cand", CriteriaSpecification.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		
		p.add(Projections.property("e.nome"), "projectionExameNome");
		p.add(Projections.property("re.data"), "projectionRealizacaoExameData");
		p.add(Projections.property("ese.id"), "id");
		p.add(Projections.property("ese.periodicidade"), "periodicidade");
		
		if (colaboradorId != null)
			p.add(Projections.property("col.nome"), "projectionColaboradorNome");
		if (candidatoId != null)
			p.add(Projections.property("cand.nome"), "projectionCandidatoNome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("e.id", exameId));
		criteria.add(Expression.le("re.data", new Date()));
//		?criteria.add(Subqueries.propertyEq("re.data", subQuery));
		
		if (colaboradorId != null)
			criteria.add(Expression.eq("col.id", colaboradorId));
		if (candidatoId != null)
			criteria.add(Expression.eq("cand.id", candidatoId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (ExameSolicitacaoExame) criteria.uniqueResult();
	}
	
	public ExameSolicitacaoExame findIdColaboradorOUCandidato(Long solicitacaoExameId, Long exameId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ese");
		criteria.createCriteria("ese.solicitacaoExame", "se");
		criteria.createCriteria("ese.exame", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("se.colaborador.id"), "projectionColaboradorId");
		p.add(Projections.property("se.candidato.id"), "projectionCandidatoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("se.id", solicitacaoExameId));
		criteria.add(Expression.eq("e.id", exameId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (ExameSolicitacaoExame) criteria.uniqueResult();
	}
}