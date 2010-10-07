package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.model.desenvolvimento.DiaTurma;

public class DiaTurmaDaoHibernate extends GenericDaoHibernate<DiaTurma> implements DiaTurmaDao
{
	public void deleteDiasTurma(Long turmaId)
	{
		String queryHQL = "delete DiaTurma d where d.turma.id = :id";

		Session newSession = getSession();
		newSession.createQuery(queryHQL).setLong("id", turmaId).executeUpdate();
	}

	public Collection<DiaTurma> findByTurma(Long turmaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "dt");
		criteria.createCriteria("dt.turma", "t", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("dt.id"), "id");
		p.add(Projections.property("dt.dia"), "dia");

		criteria.setProjection(p);
		criteria.add(Expression.eq("t.id", turmaId));

		criteria.addOrder( Order.asc("dt.dia") );
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Integer qtdDiasDasTurmas(Long turmaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "dt");
		criteria.setProjection(Projections.rowCount());
		
		if(turmaId != null)
			criteria.add(Expression.eq("dt.turma.id", turmaId));

		return (Integer) criteria.uniqueResult();
	}
	
}