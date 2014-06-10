package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;

public class ColaboradorPresencaDaoHibernate extends GenericDaoHibernate<ColaboradorPresenca> implements ColaboradorPresencaDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorPresenca> findPresencaByTurma(Long turmaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorPresenca.class, "cp");
		criteria.createCriteria("cp.colaboradorTurma", "ct");
		criteria.createCriteria("cp.diaTurma", "dt");
		criteria.createCriteria("ct.colaborador", "colab");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cp.id"), "id");
		p.add(Projections.property("ct.id"), "projectionColaboradorTurmaId");
		p.add(Projections.property("dt.id"), "projectionDiaTurmaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("ct.turma.id", turmaId));
		criteria.addOrder(Order.asc("colab.nomeComercial"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorPresenca.class));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<ColaboradorPresenca> existPresencaByTurma(Long turmaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorPresenca.class, "cp");
		criteria.createCriteria("cp.colaboradorTurma", "ct");

		ProjectionList p = Projections.projectionList().create();
        p.add(Projections.property("cp.id"), "id");
        criteria.setProjection(p);

		criteria.add(Expression.eq("ct.turma.id", turmaId));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorPresenca.class));

		return criteria.list();
	}

	public void remove(Long diaTurmaId, Long colaboradorTurmaId)
	{
		StringBuilder queryHQL = new StringBuilder("delete from ColaboradorPresenca cp where cp.diaTurma.id = :diaTurmaId ");

		if(colaboradorTurmaId != null)
				queryHQL.append("and cp.colaboradorTurma.id = :colaboradorTurmaId ");

		Query q = getSession().createQuery(queryHQL.toString());
		
		if(colaboradorTurmaId != null)
			q.setLong("colaboradorTurmaId", colaboradorTurmaId);
		
		q.setLong("diaTurmaId", diaTurmaId);
		q.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorPresenca> findByDiaTurma(Long diaTurmaId)
	{
		Criteria criteria = getSession().createCriteria(ColaboradorPresenca.class, "cp");
		criteria.createCriteria("cp.colaboradorTurma", "ct");
		criteria.createCriteria("cp.diaTurma", "dt");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cp.id"), "id");
		p.add(Projections.property("ct.id"), "projectionColaboradorTurmaId");
		p.add(Projections.property("ct.turma.id"), "projectionTurmaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("dt.id", diaTurmaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorPresenca.class));

		return criteria.list();
	}
	
	public void savePresencaDia(Long diaTurmaId, Long[] colaboradorTurmaIds)
	{
		for (Long colaboradorTurmaId : colaboradorTurmaIds)
		{
			ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
			colaboradorTurma.setId(colaboradorTurmaId);
			DiaTurma diaTurma = new DiaTurma();
			diaTurma.setId(diaTurmaId);
			ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca(colaboradorTurma, diaTurma, true);
			save(colaboradorPresenca);
		}
		
	}

	public void removeByColaboradorTurma(Long[] colaboradorTurmaIds)
	{
		StringBuilder queryHQL = new StringBuilder("delete from ColaboradorPresenca cp where cp.colaboradorTurma.id in (:colaboradorTurmaIds) ");

		Query q = getSession().createQuery(queryHQL.toString());
		
		q.setParameterList("colaboradorTurmaIds", colaboradorTurmaIds, Hibernate.LONG);
		q.executeUpdate();
	}
	
	public Integer qtdDiaPresentesTurma(Long turmaId, Long[] areasIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select count(dt.id) ");
		hql.append("from ColaboradorPresenca as cp ");
		hql.append("inner join cp.diaTurma dt ");
		hql.append("inner join cp.colaboradorTurma ct ");
		hql.append("inner join ct.colaborador c ");
		hql.append("inner join c.historicoColaboradors hc ");
		hql.append("where hc.data = ( ");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = c.id ");
		hql.append("		) ");
		
		if (turmaId != null)
			hql.append("and dt.turma.id = :turmaId ");
		
		if (areasIds != null && areasIds.length > 0)
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");
		
		Query query = getSession().createQuery(hql.toString());
		
		if (turmaId != null)
			query.setLong("turmaId", turmaId);
		
		if (areasIds != null && areasIds.length > 0)
			query.setParameterList("areasIds", areasIds, Hibernate.LONG);
		
		return (Integer) query.uniqueResult();	
	}
}