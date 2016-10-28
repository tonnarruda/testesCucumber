package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EpcDao;
import com.fortes.rh.model.sesmt.Epc;

@Component
@SuppressWarnings("unchecked")
public class EpcDaoHibernate extends GenericDaoHibernate<Epc> implements EpcDao
{
	public Epc findByIdProjection(Long epcId)
	{
		Criteria criteria = getSession().createCriteria(Epc.class, "e");
		criteria.createCriteria("e.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.codigo"), "codigo");
		p.add(Projections.property("e.descricao"), "descricao");
		p.add(Projections.property("emp.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", epcId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(Epc.class));

		return (Epc) criteria.uniqueResult();
	}

	public Collection<Epc> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Epc.class, "e");
		criteria.createCriteria("e.empresa", "emp");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.codigo"), "codigo");
		p.add(Projections.property("e.descricao"), "descricao");
		p.add(Projections.property("emp.id"), "empresaIdProjection");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("emp.id", empresaId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Epc.class));
		
		return criteria.list();
	}

	public Collection<Epc> findByAmbiente(Long ambienteId) 
	{
		Criteria criteria = getSession().createCriteria(Epc.class, "e");
		criteria.createCriteria("e.ambientes", "ambiente");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.codigo"), "codigo");
		p.add(Projections.property("e.descricao"), "descricao");
		
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("ambiente.id", ambienteId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Epc.class));
		
		return criteria.list();
	}
	
	public Collection<Epc> findEpcsDoAmbiente(Long ambienteId, Date data)
	{
		StringBuilder hql = new StringBuilder();
		
		hql.append("select new Epc(e.id, e.codigo, e.descricao) ");
		hql.append("from HistoricoAmbiente ha ");
		hql.append("join ha.epcs e ");
		hql.append("where ha.ambiente.id = :ambienteId ");
		hql.append("	and ha.data = (select max(ha2.data) from HistoricoAmbiente ha2 where ha2.data <= :data and ha2.ambiente.id = ha.ambiente.id) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setDate("data", data);
		query.setLong("ambienteId", ambienteId);

		return query.list();
	}
}