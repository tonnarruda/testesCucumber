package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

@SuppressWarnings("unchecked")
public class HistoricoExtintorDaoHibernate extends GenericDaoHibernate<HistoricoExtintor> implements HistoricoExtintorDao
{
	public Collection<HistoricoExtintor> findByExtintor(Long extintorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoExtintor.class, "hist");
		criteria.createCriteria("hist.estabelecimento", "est");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hist.id"), "id");
		p.add(Projections.property("hist.data"), "data");
		p.add(Projections.property("hist.localizacao"), "localizacao");
		p.add(Projections.property("est.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("est.nome"), "projectionEstabelecimentoNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("hist.extintor.id", extintorId));
		
		criteria.addOrder(Order.asc("hist.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoExtintor.class));
		
		return criteria.list();
	}

	public void removeByExtintor(Long extintorId) 
	{
		Query query = getSession().createQuery("delete from HistoricoExtintor hist where hist.extintor.id=:extintorId");
		query.setLong("extintorId", extintorId);
		
		query.executeUpdate();
	}
}