package com.fortes.rh.dao.hibernate.desenvolvimento;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.DNTDao;
import com.fortes.rh.model.desenvolvimento.DNT;

@Component
public class DNTDaoHibernate extends GenericDaoHibernate<DNT> implements DNTDao
{
	public DNT getUltimaDNT(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(DNT.class,"dnt");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("dnt.id"), "id");
		p.add(Projections.property("dnt.empresa.id"), "projectionEmpresaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("dnt.empresa.id", empresaId));

		criteria.addOrder( Order.desc("dnt.data") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(DNT.class));

		criteria.setMaxResults(1);
		return (DNT) criteria.uniqueResult();
	}
}