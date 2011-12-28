package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;

public class TurmaTipoDespesaDaoHibernate extends GenericDaoHibernate<TurmaTipoDespesa> implements TurmaTipoDespesaDao
{

	public Collection<TurmaTipoDespesa> findTipoDespesaTurma(Long turmaId) {
		
		Criteria criteria = getSession().createCriteria(TurmaTipoDespesa.class, "t");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("t.tipoDespesa.id"), "projectionTipoDespesaId");
		p.add(Projections.property("t.despesa"), "despesa");

		criteria.setProjection(p);

		criteria.add(Expression.eq("t.turma.id", turmaId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(TurmaTipoDespesa.class));

		return criteria.list();
	}

	public void removeByTurma(Long turmaId) 
	{
		String hql = "delete TurmaTipoDespesa t where t.turma.id = :turmaId";

		Query query = getSession().createQuery(hql);

		query.setLong("turmaId", turmaId);
		query.executeUpdate();
	}
}
