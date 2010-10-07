package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoMedicaoRiscoDao;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

@SuppressWarnings("unchecked")
public class RiscoMedicaoRiscoDaoHibernate extends GenericDaoHibernate<RiscoMedicaoRisco> implements RiscoMedicaoRiscoDao
{
	public boolean removeByMedicaoRisco(Long medicaoRiscoId) 
	{
		String hql = "delete from RiscoMedicaoRisco r where r.medicaoRisco.id=:id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", medicaoRiscoId);
		
		return query.executeUpdate() == 1;
	}

	public Collection<RiscoMedicaoRisco> findMedicoesDeRiscosDoAmbiente(Long ambienteId, Date data) 
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new RiscoMedicaoRisco(rm.descricaoPpra, rm.descricaoLtcat, rm.tecnicaUtilizada, rm.intensidadeMedida, r.descricao, r.grupoRisco, r, m.data) ");
		hql.append("from RiscoMedicaoRisco rm ");
		hql.append("	join rm.risco r ");
		hql.append("	join rm.medicaoRisco m ");
		hql.append("where m.ambiente.id = :ambienteId ");
		hql.append("	and m.data = ( select max(m2.data) from MedicaoRisco m2 where m2.data <= :data and m2.ambiente.id = m.ambiente.id) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("ambienteId", ambienteId);

		return query.list();
	}
	
	public Collection<RiscoMedicaoRisco> findByRiscoAteData(Long riscoId, Long ambienteId, Date dataAmbienteFim)
	{
		StringBuilder hql = new StringBuilder("select new RiscoMedicaoRisco(rm.tecnicaUtilizada, rm.intensidadeMedida, m.id, m.data) ");
		hql.append("from RiscoMedicaoRisco rm ");
		hql.append("join rm.medicaoRisco m ");
		hql.append("where rm.risco.id = :riscoId ");
		hql.append("and m.ambiente.id = :ambienteId ");
		hql.append("and m.data < :dataFim ");
		hql.append("order by m.data desc ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("riscoId", riscoId);
		query.setLong("ambienteId", ambienteId);
		query.setDate("dataFim", dataAmbienteFim);
		
		return query.list();
	}

	public MedicaoRisco findUltimaAteData(Long ambienteId, Date historicoAmbienteData)
	{
		Criteria criteria = getSession().createCriteria(MedicaoRisco.class, "m");
		
		criteria.add(Expression.eq("m.ambiente.id", ambienteId));
		criteria.add(Expression.le("m.data", historicoAmbienteData));
		criteria.addOrder(Order.desc("m.data"));
		
		criteria.setMaxResults(1);
		return (MedicaoRisco)criteria.uniqueResult();
	}
}
