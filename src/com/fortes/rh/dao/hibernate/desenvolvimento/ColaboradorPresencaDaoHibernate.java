package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
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

	public Collection<ColaboradorPresenca> findColaboradorPresencaAprovadoOuReprovadoAvaliacao(Collection<Long> turmaIds, boolean aprovado)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ColaboradorPresenca(ct.colaborador.id, t.id, count(cp.id)) ");
		hql.append("from ColaboradorPresenca as cp ");
		hql.append("right join cp.colaboradorTurma ct ");
		hql.append("join ct.turma t ");
		hql.append("left join ct.aproveitamentoAvaliacaoCursos avc ");
		hql.append("left join avc.avaliacaoCurso ac ");
		
		if (aprovado)
			hql.append("where (avc.valor >= ac.minimoAprovacao or ac.minimoAprovacao is null or avc.valor is null) ");
		else
			hql.append("where (avc.valor < ac.minimoAprovacao or ac.minimoAprovacao is null or avc.valor is null) ");
		
		if (turmaIds != null && !turmaIds.isEmpty())
			hql.append("and  t.id in (:turmaId) ");
		
		hql.append("group by ct.colaborador.id, t.id ");
		hql.append("order by t.id");

		Query query = getSession().createQuery(hql.toString());
		
		if (turmaIds != null && !turmaIds.isEmpty())
			query.setParameterList("turmaId", turmaIds, Hibernate.LONG);

		return query.list();
	}
	
	public Integer qtdDiaPresentesTurma(Long turmaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select count(dt.id) ");
		hql.append("from ColaboradorPresenca as cp ");
		hql.append("join cp.diaTurma dt ");
		
		if(turmaId != null)
			hql.append("where dt.turma.id = :turmaId ");
		
		Query query = getSession().createQuery(hql.toString());
		
		if(turmaId != null)
			query.setLong("turmaId", turmaId);
		
		return (Integer) query.uniqueResult();	
	}
}