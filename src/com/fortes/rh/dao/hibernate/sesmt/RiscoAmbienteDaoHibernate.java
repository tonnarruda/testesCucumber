package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoAmbiente;

@SuppressWarnings("unchecked")
public class RiscoAmbienteDaoHibernate extends GenericDaoHibernate<RiscoAmbiente> implements RiscoAmbienteDao
{
	public boolean removeByHistoricoAmbiente(Long historicoAmbienteId) 
	{
		String hql = "delete from RiscoAmbiente r where r.historicoAmbiente.id=:id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", historicoAmbienteId);
		
		return query.executeUpdate() == 1;
	}

	public Collection<Risco> findRiscosByAmbienteData(Long ambienteId, Date data) 
	{
		StringBuilder hql = new StringBuilder("select new Risco(r.id, r.descricao, r.grupoRisco) ");
		hql.append("from RiscoAmbiente ra join ra.risco r join ra.historicoAmbiente ha ");
		hql.append("where ha.ambiente.id = :ambienteId ");
		hql.append("and ha.data = (select max(ha2.data) " +
								"from HistoricoAmbiente ha2 " +
								"where ha2.ambiente.id = :ambienteId and ha2.data <= :data) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("ambienteId", ambienteId);
		query.setDate("data", data);
		
		return query.list();
	}

	public Collection<String> findColaboradoresSemAmbiente(Date data, Long estabelecimentoId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select co.nome ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("join hc.colaborador co ");
		
		hql.append("where hc.estabelecimento.id = :estabelecimentoId ");
		hql.append("and hc.ambiente.id is null ");
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("                from HistoricoColaborador as hc2 ");
		hql.append("                where hc2.colaborador.id = co.id ");
		hql.append("                and hc2.data <= :data and hc2.status = :status ) ");
		hql.append("and (co.dataDesligamento >= :data or co.dataDesligamento is null)  ");
		hql.append("group by co.nome ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}

	public Collection<Long> findAmbienteAtualDosColaboradores(Date data, Long estabelecimentoId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select a.id ");
		hql.append("from HistoricoColaborador as hc ");
		hql.append("join hc.ambiente a ");
		hql.append("join hc.colaborador co ");
		
		hql.append("where hc.estabelecimento.id = :estabelecimentoId ");
		hql.append("and hc.data = (select max(hc2.data) ");
		hql.append("                from HistoricoColaborador as hc2 ");
		hql.append("                where hc2.colaborador.id = co.id ");
		hql.append("                and hc2.data <= :data  and hc2.status = :status ) ");
		hql.append("group by a.id ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("data", data);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
	}

	public Collection<RiscoAmbiente> findByAmbiente(Long ambienteId)
	{

		DetachedCriteria subQueryHa = DetachedCriteria.forClass(HistoricoAmbiente.class, "ha2")
				.setProjection(Projections.max("ha2.data"))
				.add(Restrictions.eqProperty("ha2.ambiente.id", "ha.ambiente.id"))
				.add(Restrictions.le("ha2.data", new Date()));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ra");
		criteria.createCriteria("ra.risco", "r");
		criteria.createCriteria("ra.historicoAmbiente", "ha");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ra.id"), "id");
		p.add(Projections.property("ra.grauDeRisco"), "grauDeRisco");
		p.add(Projections.property("r.descricao"), "riscoDescricao");
		p.add(Projections.property("r.grupoRisco"), "riscoGrupoRisco");
		criteria.setProjection(p);

		criteria.add(Subqueries.propertyEq("ha.data", subQueryHa));
		criteria.add(Expression.eq("ha.ambiente.id", ambienteId));
		criteria.add(Expression.isNotNull("ra.grauDeRisco"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
