package com.fortes.rh.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;

@SuppressWarnings("unchecked")
public class HistoricoAmbienteDaoHibernate extends GenericDaoHibernate<HistoricoAmbiente> implements HistoricoAmbienteDao
{
	public boolean removeByAmbiente(Long ambienteId) 
	{
		String hql = "delete from HistoricoAmbiente h where h.ambiente.id=:id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", ambienteId);
		
		return query.executeUpdate() == 1;
	}

	public Collection<HistoricoAmbiente> findByAmbiente(Long ambienteId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "historico");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("historico.id"), "id");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("historico.ambiente.id", ambienteId));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public HistoricoAmbiente findUltimoHistorico(Long ambienteId) 
	{
		StringBuilder hql = new StringBuilder("from HistoricoAmbiente ha ");
		hql.append("where ha.ambiente.id = :ambienteId ");
		hql.append("and ha.data >= (select max(ha2.data) from HistoricoAmbiente ha2 where ha2.ambiente.id = :ambienteId) ");
		hql.append("order by ha.data ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("ambienteId", ambienteId);
		
		query.setMaxResults(1);
		return (HistoricoAmbiente)query.uniqueResult();
	}
	
	public HistoricoAmbiente findUltimoHistoricoAteData(Long ambienteId, Date dataMaxima)
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.le("ha2.data", dataMaxima))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "a.id"));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ha");
		criteria.createCriteria("ha.ambiente", "a", Criteria.INNER_JOIN);
		criteria.createCriteria("ha.riscoAmbientes", "ra", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ha.id"), "id");
		p.add(Projections.property("ha.data"), "data");
		p.add(Projections.property("a.id"), "ambienteId");
		p.add(Projections.property("ra.id"), "ricoAmbienteId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("a.id", ambienteId));
		criteria.add(Subqueries.propertyGe("ha.data", subQuery));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setMaxResults(1);
		
		return (HistoricoAmbiente) criteria.uniqueResult();
	}

	public List<DadosAmbienteOuFuncaoRisco> findDadosNoPeriodo(Long ambienteId, Date dataIni, Date dataFim) 
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco(ha.ambiente.id, r.id, r.descricao, r.grupoRisco, ra.epcEficaz, ha.data) ");
		hql.append("from HistoricoAmbiente ha ");
		hql.append("join ha.riscoAmbientes ra ");
		hql.append("join ra.risco r ");
		hql.append("where ha.ambiente.id = :ambienteId ");
		hql.append("and ha.data < :dataFim ");
		hql.append("and ha.data >= (select max(ha2.data) from HistoricoAmbiente ha2 ");
		hql.append("				where ha2.ambiente.id = :ambienteId ");
		hql.append("				and ha2.data <= :dataIni) ");
		hql.append("order by ha.data ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("ambienteId", ambienteId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		
		return query.list();
	}
	
	public Collection<HistoricoAmbiente> findRiscosAmbientes(Collection<Long> ambienteIds, Date data)
	{
		if(ambienteIds == null || ambienteIds.isEmpty())
			return null;
		
		StringBuilder hql = new StringBuilder("select new HistoricoAmbiente(a.id, a.nome, r.id, r.descricao) ");
		hql.append("from HistoricoAmbiente h ");
		hql.append("left join h.riscoAmbientes ra ");
		hql.append("left join h.ambiente a ");
		hql.append("left join ra.risco r ");
			hql.append("where h.data = (select max(h2.data) ");
							hql.append("from HistoricoAmbiente h2 ");
							hql.append("where h2.ambiente.id = h.ambiente.id ");
							hql.append("and h2.data <= :dataHist) ");
			hql.append("and a.id in (:ambienteIds) ");
			hql.append("and r.id is not null ");
		hql.append("order by a.nome, r.descricao ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataHist", data);
		query.setParameterList("ambienteIds", ambienteIds, Hibernate.LONG);

		Collection<HistoricoAmbiente> historicosDistinct = new ArrayList<HistoricoAmbiente>();
		List<HistoricoAmbiente> historicos = query.list();
		
		Map<Ambiente, Collection<Risco>> riscosPorAmbiente = new HashMap<Ambiente, Collection<Risco>>();
		
		for (HistoricoAmbiente historicoAmbiente : historicos)
		{
			if(riscosPorAmbiente.get(historicoAmbiente.getAmbiente()) == null)
				riscosPorAmbiente.put(historicoAmbiente.getAmbiente(), new ArrayList<Risco>());

			riscosPorAmbiente.get(historicoAmbiente.getAmbiente()).add(historicoAmbiente.getRisco());
		}
		
		Collection<Ambiente> ambientes = riscosPorAmbiente.keySet();
		
		for (Ambiente chave : ambientes)
		{
			HistoricoAmbiente historicoFuncao = new HistoricoAmbiente();
			historicoFuncao.setAmbiente(chave);
			historicoFuncao.setRiscos((riscosPorAmbiente.get(chave)));
			
			historicosDistinct.add(historicoFuncao);			
		}
		
		return historicosDistinct;
	}

	public HistoricoAmbiente findByData(Date data, Long historicoAmbienteId, Long ambienteId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ha");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ha.id"), "id");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ha.ambiente.id", ambienteId));
		criteria.add(Expression.eq("ha.data", data));
		
		if (historicoAmbienteId != null)
			criteria.add(Expression.ne("ha.id", historicoAmbienteId));

		criteria.setMaxResults(1);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (HistoricoAmbiente)criteria.uniqueResult();
	}
}