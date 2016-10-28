package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.util.LongUtil;

@Component
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
		
		q.setParameterList("colaboradorTurmaIds", colaboradorTurmaIds, StandardBasicTypes.LONG);
		q.executeUpdate();
	}
	
	public Integer qtdDiaPresentesTurma(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select coalesce(count(distinct cp.id), 0) ");
		hql.append("from ColaboradorPresenca as cp ");
		hql.append("inner join cp.diaTurma dt ");
		hql.append("inner join dt.turma t ");
		hql.append("inner join t.curso cs ");
		hql.append("left join cs.empresasParticipantes ep ");
		hql.append("inner join cp.colaboradorTurma ct ");
		hql.append("inner join ct.colaborador c ");
		hql.append("inner join c.historicoColaboradors hc ");
		hql.append("where hc.data = ( ");
		hql.append("			select max(hc2.data) ");
		hql.append("			from HistoricoColaborador as hc2 ");
		hql.append("			where hc2.colaborador.id = c.id ");
		hql.append("		) ");
		
		if (dataIni != null)
			hql.append("and t.dataPrevIni >= :dataIni ");

		if (dataFim != null)
			hql.append("and t.dataPrevFim <= :dataFim "); 

		if (LongUtil.arrayIsNotEmpty(empresaIds))
			hql.append("and (cs.empresa.id in (:empresaIds) or ep.id in (:empresaIds)) ");
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			hql.append("and t.curso.id in (:cursoIds) ");
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			hql.append("and hc.areaOrganizacional.id in (:areasIds) ");
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			hql.append("and hc.estabelecimento.id in (:estabelecimentosIds) ");
		
		Query query = getSession().createQuery(hql.toString());
		
		if (dataIni != null)
			query.setDate("dataIni", dataIni);

		if (dataFim != null)
			query.setDate("dataFim", dataFim);
		
		if (LongUtil.arrayIsNotEmpty(empresaIds))
			query.setParameterList("empresaIds", empresaIds, StandardBasicTypes.LONG);
		
		if (LongUtil.arrayIsNotEmpty(cursoIds))
			query.setParameterList("cursoIds", cursoIds, StandardBasicTypes.LONG);
		
		if (LongUtil.arrayIsNotEmpty(areasIds))
			query.setParameterList("areasIds", areasIds, StandardBasicTypes.LONG);
		
		if (LongUtil.arrayIsNotEmpty(estabelecimentosIds))
			query.setParameterList("estabelecimentosIds", estabelecimentosIds, StandardBasicTypes.LONG);
		
		return (Integer) query.uniqueResult();	
	}

	public Integer qtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId(Long diaTurma, Long estabelecimentoId) 
	{
		DetachedCriteria subQueryHc = DetachedCriteria.forClass(HistoricoColaborador.class, "hc2")
				.setProjection(Projections.max("hc2.data"))
				.add(Restrictions.eqProperty("hc2.colaborador.id", "c.id"));
		
		Criteria criteria = getSession().createCriteria(ColaboradorPresenca.class, "cp");
		criteria.createCriteria("cp.diaTurma", "dt");
		criteria.createCriteria("cp.colaboradorTurma", "ct");
		criteria.createCriteria("ct.colaborador", "c");
		criteria.createCriteria("c.historicoColaboradors", "hc");
		
		criteria.add(Subqueries.propertyEq("hc.data", subQueryHc));
		
		criteria.setProjection(Projections.rowCount());
		criteria.add(Expression.eq("dt.id", diaTurma));
		
		if(estabelecimentoId != null)
			criteria.add(Expression.eq("hc.estabelecimento.id", estabelecimentoId));

		return (Integer) criteria.uniqueResult();
	}
}