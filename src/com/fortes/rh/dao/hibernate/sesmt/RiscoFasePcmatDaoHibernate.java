package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.RiscoFasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;

@Component
public class RiscoFasePcmatDaoHibernate extends GenericDaoHibernate<RiscoFasePcmat> implements RiscoFasePcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<RiscoFasePcmat> findByFasePcmat(Long fasePcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "rfp");
		criteria.createCriteria("rfp.risco", "r");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("rfp.id"), "id");
		p.add(Projections.property("rfp.fasePcmat.id"), "fasePcmatId");
		p.add(Projections.property("r.id"), "riscoId");
		p.add(Projections.property("r.descricao"), "riscoDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("rfp.fasePcmat.id", fasePcmatId));
		criteria.addOrder(Order.asc("r.descricao"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void removeByFasePcmatRisco(Long fasePcmatId, Collection<Long> riscosIds) 
	{
		Query query = getSession().createQuery("delete from RiscoFasePcmat where fasePcmat.id = :fasePcmatId and risco.id in (:riscosIds)");
		query.setLong("fasePcmatId", fasePcmatId);
		query.setParameterList("riscosIds", riscosIds);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<RiscoFasePcmat> findByPcmat(Long pcmatId) 
	{
		Criteria criteria = getSession().createCriteria(FasePcmat.class, "fp");
		criteria.createCriteria("fp.riscosFasePcmat", "rfp", Criteria.LEFT_JOIN);
		criteria.createCriteria("rfp.risco", "r", Criteria.LEFT_JOIN);
		criteria.createCriteria("fp.fase", "f");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("rfp.id"), "id");
		p.add(Projections.property("fp.id"), "fasePcmatId");
		p.add(Projections.property("fp.descricao"), "fasePcmatDescricao");
		p.add(Projections.property("r.id"), "riscoId");
		p.add(Projections.property("r.descricao"), "riscoDescricao");
		p.add(Projections.property("r.grupoRisco"), "riscoGrupoRisco");
		p.add(Projections.property("f.id"), "faseId");
		p.add(Projections.property("f.descricao"), "faseDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("fp.pcmat.id", pcmatId));
		
		criteria.addOrder(Order.asc("f.descricao"));
		criteria.addOrder(Order.asc("r.descricao"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
