package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExameSolicitacaoExameDao;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;


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

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long solicitacaoExameId, Boolean asoPadrao)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ese");
		criteria.createCriteria("ese.solicitacaoExame", "se");
		criteria.createCriteria("ese.exame", "e");
		criteria.createCriteria("ese.realizacaoExame", "re", Criteria.LEFT_JOIN);
		criteria.createCriteria("ese.clinicaAutorizada", "cli", Criteria.LEFT_JOIN);

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
		p.add(Projections.property("re.data"), "projectionRealizacaoExameData");
		p.add(Projections.property("cli.id"), "projectionClinicaId");

		criteria.setProjection(p);
		criteria.add(Expression.eq("se.id", solicitacaoExameId));

		if(asoPadrao!= null)
			criteria.add(Expression.eq("e.aso", asoPadrao));
		
		criteria.addOrder(Order.desc("e.aso") );
		criteria.addOrder(Order.asc("e.nome") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<ExameSolicitacaoExame> findBySolicitacaoExame(Long[] solicitacaoExameIds)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"ese");
		criteria.createCriteria("ese.solicitacaoExame", "se");
		criteria.createCriteria("ese.exame", "e");
		criteria.createCriteria("ese.realizacaoExame", "re", Criteria.LEFT_JOIN);
		criteria.createCriteria("ese.clinicaAutorizada", "ca", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ese.id"), "id");
		p.add(Projections.property("se.id"), "projectionSolicitacaoExameId");
		p.add(Projections.property("e.id"), "projectionExameId");
		p.add(Projections.property("e.nome"), "projectionExameNome");
		p.add(Projections.property("re.id"), "projectionRealizacaoExameId");
		p.add(Projections.property("re.resultado"), "projectionRealizacaoExameResultado");
		p.add(Projections.property("ca.id"), "projectionClinicaId");
		criteria.setProjection(p);

		criteria.add(Expression.in("se.id", solicitacaoExameIds));

		criteria.addOrder( Order.desc("e.aso") );
		criteria.addOrder( Order.asc("e.nome") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public ExameSolicitacaoExame findDataSolicitacaoExame(Long colaboradorId, Long candidatoId, Long exameId)
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new ExameSolicitacaoExame(ese.id, ese.periodicidade, e.nome, re.data, col.nome, can.nome) ");

		hql.append("from ExameSolicitacaoExame as ese ");
		hql.append("left join ese.solicitacaoExame as se ");
		hql.append("left join ese.exame as e ");
		hql.append("inner join ese.realizacaoExame as re ");//tem que ser inner
		hql.append("left join se.candidato as can ");
		hql.append("left join se.colaborador as col ");

		hql.append("where e.id = :exameId ");
		if (colaboradorId != null)
			hql.append("and se.colaborador.id = :colaboradorId ");
		if (candidatoId != null)
			hql.append("and se.candidato.id = :candidatoId ");
		
		hql.append("and re.data = ( ");
		
		hql.append("select max(reSub.data) from ExameSolicitacaoExame eseSub ");
		hql.append("left join eseSub.solicitacaoExame as seSub ");
		hql.append("inner join eseSub.realizacaoExame as reSub ");//tem que ser inner
		hql.append("where reSub.data <= :hoje ");
		if (colaboradorId != null)
			hql.append("and seSub.colaborador.id = :colaboradorId ");
		if (candidatoId != null)
			hql.append("and seSub.candidato.id = :candidatoId ");
		hql.append("and eseSub.exame.id = :exameId) ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		query.setLong("exameId", exameId);

		if (colaboradorId != null)
			query.setLong("colaboradorId", colaboradorId);
		if (candidatoId != null)
			query.setLong("candidatoId", candidatoId);

		return (ExameSolicitacaoExame) query.uniqueResult();
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