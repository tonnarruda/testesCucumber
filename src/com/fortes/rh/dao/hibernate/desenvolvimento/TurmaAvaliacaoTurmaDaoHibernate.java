package com.fortes.rh.dao.hibernate.desenvolvimento;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.TurmaAvaliacaoTurmaDao;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;
import com.fortes.rh.util.LongUtil;

public class TurmaAvaliacaoTurmaDaoHibernate extends GenericDaoHibernate<TurmaAvaliacaoTurma> implements TurmaAvaliacaoTurmaDao
{

	public boolean verificaAvaliacaoliberada(Long turmaId) 
	{
    	Criteria criteria = getSession().createCriteria(TurmaAvaliacaoTurma.class, "tat");

    	ProjectionList p = Projections.projectionList().create();

    	p.add(Projections.property("tat.id"), "id");

    	criteria.setProjection(p);
    	criteria.add(Expression.eq("tat.turma.id", turmaId));
    	criteria.add(Expression.eq("tat.liberada", true));

    	return criteria.list().size() > 0;
	}

	public void updateLiberada(Long turmaId, Long avaliacaoTurmaId, Boolean liberada) 
	{
		String hql = "update TurmaAvaliacaoTurma set liberada = :liberada where turma.id = :turmaId and  avaliacaoTurma.id = :avaliacaoTurmaId";

		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);
		query.setLong("avaliacaoTurmaId", avaliacaoTurmaId);
		query.setBoolean("liberada", liberada);

		query.executeUpdate();
	}

	public void removeByTurma(Long turmaId, Long[] avaliacaoTurmaIdsQueNaoDevemSerRemovidas) {
		String hql = "delete from TurmaAvaliacaoTurma where turma.id = :turmaId ";
		if(LongUtil.arrayIsNotEmpty(avaliacaoTurmaIdsQueNaoDevemSerRemovidas))
			hql+="and avaliacaoTurma.id not in(:avaliacaoTurmaIds)"; 

		Query query = getSession().createQuery(hql);
		query.setLong("turmaId", turmaId);
		if(LongUtil.arrayIsNotEmpty(avaliacaoTurmaIdsQueNaoDevemSerRemovidas))
			query.setParameterList("avaliacaoTurmaIds", avaliacaoTurmaIdsQueNaoDevemSerRemovidas);

		query.executeUpdate();
	}
}