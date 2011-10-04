package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EpiDao;
import com.fortes.rh.model.sesmt.Epi;

@SuppressWarnings("unchecked")
public class EpiDaoHibernate extends GenericDaoHibernate<Epi> implements EpiDao
{
	public Epi findByIdProjection(Long epiId)
	{
		Criteria criteria = getSession().createCriteria(Epi.class, "e");
		criteria.createCriteria("e.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("emp.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", epiId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Epi.class));

		return (Epi) criteria.uniqueResult();
	}
	
	public Collection<Epi> findAllSelect(Long empresaId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Epi(epi.id, epi.nome, epi.fabricante, hist.CA, hist.vencimentoCA) ");
		hql.append("from Epi as epi ");
		hql.append("left join epi.epiHistoricos as hist "); 
		hql.append("where epi.empresa.id = :empresaId ");
		hql.append("  and hist.data = (select max(eh.data) from EpiHistorico eh where eh.epi.id = epi.id) ");
		hql.append("order by epi.nome");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);

		return query.list();
	}

	public Collection<Epi> findByVencimentoCa(Date data, Long empresaId, Long[] tipoEPIIds)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Epi(e.id, e.nome, e.fabricante, eh.CA, eh.vencimentoCA)");
		hql.append("  from Epi e");
		hql.append("  left join e.epiHistoricos eh");
		hql.append(" where eh.data = (select max(eh2.data)");
		hql.append("                    from EpiHistorico eh2");
		hql.append("                   where eh2.epi.id = e.id");
		hql.append("                     and eh2.data <= :data)");
		hql.append("   and eh.vencimentoCA <= :data");
		hql.append("   and e.empresa.id = :empresaId");
		
		if(tipoEPIIds != null && tipoEPIIds.length > 0)
			hql.append("   and e.tipoEPI.id in (:tipoEPIIds) ");
		
		hql.append(" order by eh.vencimentoCA");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("empresaId", empresaId);
		
		if(tipoEPIIds != null && tipoEPIIds.length > 0)
			query.setParameterList("tipoEPIIds", tipoEPIIds, Hibernate.LONG);

		return query.list();
	}

	public Collection<Epi> findEpisDoAmbiente(Long ambienteId, Date data) 
	{
		StringBuilder hql = new StringBuilder();
		
		
		hql.append("select new Epi(e.id, e.nome, e.fabricante, eh.CA, eh.vencimentoCA) ");

		hql.append("from HistoricoAmbiente ha ");
		hql.append("join ha.riscoAmbientes ra ");
		hql.append("join ra.risco r ");
		hql.append("join r.epis e ");
		hql.append("left join e.epiHistoricos eh ");
		
		hql.append("where eh.data = (select max(eh2.data)");
		hql.append("                    from EpiHistorico eh2");
		hql.append("                    where eh2.epi.id = e.id");
		hql.append("                    and eh2.data <= :data)");
		
		hql.append("and ha.ambiente.id = :ambienteId ");
		hql.append("  and ha.data = (select max(ha2.data) ");
		hql.append(" 					from HistoricoAmbiente ha2 ");
		hql.append("					where ha2.data <= :data ");
		hql.append("					and ha2.ambiente.id = ha.ambiente.id) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("ambienteId", ambienteId);

		return query.list();
	}

	public Collection<Epi> findByRiscoAmbiente(Long riscoId, Long ambienteId, Date data)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Epi(e.id, e.nome, e.fabricante, eh.CA, eh.vencimentoCA) ");
		hql.append("  from RiscoAmbiente ra");
		hql.append("  join ra.risco r");
		hql.append("  join ra.historicoAmbiente ha");
		hql.append("  join r.epis e");
		hql.append("  left join e.epiHistoricos eh");
		hql.append("  where eh.data = (select max(eh2.data)");
		hql.append("                    from EpiHistorico eh2");
		hql.append("                    where eh2.epi.id = e.id");
		hql.append("                    and eh2.data <= :hoje)");
		
		if (data != null)
			hql.append("  and ha.data = :data");
		
		if (ambienteId != null)
				hql.append("  and ha.ambiente.id = :ambienteId");
		
		hql.append("  and ra.risco.id = :riscoId");
		
		hql.append("  order by eh.CA");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("hoje", new Date());
		
		if (data != null)
			query.setDate("data", data);
		
		if (ambienteId != null)
			query.setLong("ambienteId", ambienteId);
		
		query.setLong("riscoId", riscoId);
		
		return query.list();
	}
	
	public Collection<Epi> findByRisco(Long riscoId)
	{
		StringBuilder hql = new StringBuilder();

		hql.append("select new Epi(e.id, e.nome, e.fabricante, eh.CA, eh.vencimentoCA) ");
		hql.append("  from RiscoAmbiente ra");
		hql.append("  join ra.risco r");
		hql.append("  join r.epis e");
		hql.append("  left join e.epiHistoricos eh");
		hql.append("  where eh.data = (select max(eh2.data)");
		hql.append("                    from EpiHistorico eh2");
		hql.append("                    where eh2.epi.id = e.id");
		hql.append("                    and eh2.data <= :hoje)");
		hql.append("  and r.id = :riscoId");
		hql.append("  order by eh.CA");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("riscoId", riscoId);
		query.setDate("hoje", new Date());
		
		return query.list();
	}

	public Collection<Epi> findByHistoricoFuncao(Long historicoFuncaoId)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new Epi(e.id, e.nome) ");
		hql.append("from HistoricoFuncao as hf ");
		hql.append("join hf.epis as e ");
		hql.append("	where hf.id = :historicoFuncaoId ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("historicoFuncaoId", historicoFuncaoId);

		return query.list();
	}	
	
	public Collection<Epi> findSincronizarEpiInteresse(Long empresaOrigemId) 
	{
		Criteria criteria = getSession().createCriteria(Epi.class, "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.fardamento"), "fardamento");
		p.add(Projections.property("e.fabricante"), "fabricante");
		p.add(Projections.property("e.tipoEPI.id"), "tipoEPIIdProjection");

		criteria.setProjection(p);
		criteria.add(Expression.eq("e.empresa.id", empresaOrigemId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Epi.class));

		return criteria.list();
	}
	
	public Collection<String> findFabricantesDistinctByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.fabricante"), "fabricante");
		
		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("e.fabricante"));
		
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		return criteria.list();
	}
}