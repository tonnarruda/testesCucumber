package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.MedidaRiscoFasePcmatDao;

public class MedidaRiscoFasePcmatDaoHibernate extends GenericDaoHibernate<MedidaRiscoFasePcmat> implements MedidaRiscoFasePcmatDao
{
	@SuppressWarnings("unchecked")
	public Collection<MedidaRiscoFasePcmat> findByRiscoFasePcmat(Long riscoFasePcmatId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "mrfp");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("mrfp.id"), "id");
		p.add(Projections.property("mrfp.medidaSeguranca.id"), "medidaSegurancaId");
		p.add(Projections.property("mrfp.riscoFasePcmat.id"), "riscoFasePcmatId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("mrfp.riscoFasePcmat.id", riscoFasePcmatId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public void deleteByRiscoFasePcmat(Long riscoFasePcmatId) 
	{
		Query query = getSession().createQuery("delete from MedidaRiscoFasePcmat where riscoFasePcmat.id = :riscoFasePcmatId");
		query.setLong("riscoFasePcmatId", riscoFasePcmatId);
		query.executeUpdate();
	}
}
