package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EpiHistoricoDao;
import com.fortes.rh.model.sesmt.EpiHistorico;

@Component
@SuppressWarnings("unchecked")
public class EpiHistoricoDaoHibernate extends GenericDaoHibernate<EpiHistorico> implements EpiHistoricoDao
{
	public Collection<EpiHistorico> findByData(Date data, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(EpiHistorico.class, "eh");
		criteria.createCriteria("eh.epi", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("eh.id"), "id");
		p.add(Projections.property("eh.CA"), "CA");
		p.add(Projections.property("e.id"), "projectionEpiId");
		p.add(Projections.property("e.nome"), "projectionEpiNome");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.le("eh.data", data));
		criteria.add(Expression.eq("e.empresa.id", empresaId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.desc("eh.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(EpiHistorico.class));
		
		return criteria.list();
	}

	public Collection<EpiHistorico> getHistoricosEpi(Long idEpi, Date dataInicio, Date dataFim)
	{
		Criteria criteria = getSession().createCriteria(EpiHistorico.class, "eh");
		criteria.createCriteria("eh.epi", "e");

		criteria.add(Expression.le("eh.data", dataFim));
		criteria.add(Expression.ge("eh.vencimentoCA", dataInicio));
		criteria.add(Expression.eq("e.id", idEpi));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.desc("eh.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	public Collection<EpiHistorico> findByEpi(Long epiId)
	{
		Criteria criteria = getSession().createCriteria(EpiHistorico.class, "eh");
		criteria.createCriteria("eh.epi", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("eh.id"), "id");
		p.add(Projections.property("eh.CA"), "CA");
		p.add(Projections.property("eh.atenuacao"), "atenuacao");
		p.add(Projections.property("eh.vencimentoCA"), "vencimentoCA");
		p.add(Projections.property("eh.validadeUso"), "validadeUso");
		p.add(Projections.property("eh.data"), "data");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("eh.epi.id", epiId));
		criteria.addOrder(Order.desc("eh.data"));//data é importante no combo da entrega de epi
		criteria.setResultTransformer(new AliasToBeanResultTransformer(EpiHistorico.class));

		return criteria.list();		
	}

	public EpiHistorico findUltimoByEpiId(Long epiId) 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new EpiHistorico(hist.id, hist.atenuacao, hist.vencimentoCA, hist.validadeUso, hist.CA, hist.epi.id, hist.data) ");
		hql.append("from EpiHistorico as hist ");
		hql.append("where hist.epi.id = :epiId ");
		hql.append("  and hist.data = (select max(eh.data) from EpiHistorico eh where eh.epi.id = :epiId) ");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("epiId", epiId);

		return (EpiHistorico) query.uniqueResult();
	}

	public void removeByEpi(Long epiId) 
	{
		Query query = getSession().createQuery("delete from EpiHistorico where epi.id = :epiId");
		query.setLong("epiId", epiId);
		query.executeUpdate();
	}
}