package com.fortes.rh.dao.hibernate.sesmt;


import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.TipoEPIDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.TipoEPI;

@Component
public class TipoEPIDaoHibernate extends GenericDaoHibernate<TipoEPI> implements TipoEPIDao
{
	public Collection<TipoEPI> findCollectionTipoEPI(Long empresId) 
	{
		Criteria criteria = getSession().createCriteria(TipoEPI.class, "te");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("te.id"), "id");
		p.add(Projections.property("te.nome"), "nome");

		criteria.setProjection(p);
		criteria.add(Expression.eq("te.empresa.id", empresId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(TipoEPI.class));

		return criteria.list();
	}

	public TipoEPI findTipoEPI(Long tipoEPIId) 
	{
		Criteria criteria = getSession().createCriteria(TipoEPI.class, "te");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("te.id"), "id");
		p.add(Projections.property("te.nome"), "nome");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("te.id", tipoEPIId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(TipoEPI.class));
		
		return (TipoEPI) criteria.uniqueResult();
	}

	public Long findTipoEPIId(Long epiId) 
	{
		Criteria criteria = getSession().createCriteria(Epi.class, "e");
		criteria.createCriteria("e.tipoEPI", "te");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("te.id"), "id");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("e.id", epiId));
		
		return (Long) criteria.uniqueResult();
	}
}