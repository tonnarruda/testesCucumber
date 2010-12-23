package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoAmbienteDao;
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
}
