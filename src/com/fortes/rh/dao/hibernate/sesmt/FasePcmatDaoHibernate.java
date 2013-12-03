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
import com.fortes.rh.dao.sesmt.FasePcmatDao;
import com.fortes.rh.model.sesmt.FasePcmat;

public class FasePcmatDaoHibernate extends GenericDaoHibernate<FasePcmat> implements FasePcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<FasePcmat> findByPcmat(Long pcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fp");
		criteria.createCriteria("fp.fase", "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fp.id"), "id");
		p.add(Projections.property("fp.ordem"), "ordem");
		p.add(Projections.property("f.descricao"), "faseDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("fp.pcmat.id", pcmatId));

		criteria.addOrder(Order.asc("fp.ordem"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public FasePcmat findByIdProjection(Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fp");
		criteria.createCriteria("fp.fase", "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fp.id"), "id");
		p.add(Projections.property("fp.ordem"), "ordem");
		p.add(Projections.property("fp.pcmat.id"), "pcmatId");
		p.add(Projections.property("f.descricao"), "faseDescricao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("fp.id", id));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (FasePcmat) criteria.uniqueResult();
	}

	public void updateOrdem(Long fasePcmatId, int ordem) 
	{
		Query query = getSession().createQuery("update FasePcmat set ordem = :ordem where id = :id");
		query.setLong("id", fasePcmatId);
		query.setInteger("ordem", ordem);
		query.executeUpdate();
	}
}
