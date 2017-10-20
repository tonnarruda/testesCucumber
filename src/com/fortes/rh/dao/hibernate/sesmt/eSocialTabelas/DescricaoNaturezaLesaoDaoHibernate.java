package com.fortes.rh.dao.hibernate.sesmt.eSocialTabelas;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.eSocialTabelas.DescricaoNaturezaLesaoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.DescricaoNaturezaLesao;

public class DescricaoNaturezaLesaoDaoHibernate extends GenericDaoHibernate<DescricaoNaturezaLesao> implements DescricaoNaturezaLesaoDao
{
	@SuppressWarnings("unchecked")
	public Collection<DescricaoNaturezaLesao> findAllSelect() {
		Criteria criteria = getSession().createCriteria(DescricaoNaturezaLesao.class,"d");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("d.id"), "id");
		p.add(Projections.property("d.codigo"), "codigo");
		p.add(Projections.property("d.descricao"), "descricao");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("d.codigo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(DescricaoNaturezaLesao.class));
		
		return criteria.list();
	}
}
