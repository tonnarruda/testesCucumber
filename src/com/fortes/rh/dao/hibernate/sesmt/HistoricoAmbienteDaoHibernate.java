package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.HistoricoAmbienteDao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteRisco;

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
		Criteria criteria = getSession().createCriteria(getEntityClass(), "historico");
		
		criteria.add(Expression.eq("historico.ambiente.id", ambienteId));
		criteria.addOrder(Order.desc("historico.data"));
		criteria.setMaxResults(1);
		return (HistoricoAmbiente)criteria.uniqueResult();
	}
	
	public HistoricoAmbiente findUltimoHistoricoAteData(Long ambienteId, Date dataMaxima)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "historico");
		
		criteria.add(Expression.eq("historico.ambiente.id", ambienteId));
		criteria.add(Expression.le("historico.data", dataMaxima));
		criteria.addOrder(Order.desc("historico.data"));
		
		criteria.setMaxResults(1);
		return (HistoricoAmbiente)criteria.uniqueResult();
	}

	public List<DadosAmbienteRisco> findDadosNoPeriodo(Long ambienteId, Date dataIni, Date dataFim) 
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.sesmt.relatorio.DadosAmbienteRisco(ha.ambiente.id, r.id, r.descricao, r.grupoRisco, ra.epcEficaz, ha.data) ");
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
	
}