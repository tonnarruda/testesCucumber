package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.FatorDeRiscoDao;
import com.fortes.rh.model.sesmt.FatorDeRisco;

public class FatorDeRiscoDaoHibernate extends GenericDaoHibernate<FatorDeRisco> implements FatorDeRiscoDao{
	
	@SuppressWarnings("unchecked")
	public Collection<FatorDeRisco> findByGrupoRiscoESocial(String codigoGrupoRiscoESocial){

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("fatorRisco.id"), "id");
		p.add(Projections.property("fatorRisco.codigo"), "codigo");
		p.add(Projections.property("fatorRisco.descricao"), "descricao");
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "fatorRisco");
		if(StringUtils.isNotBlank(codigoGrupoRiscoESocial))
			criteria.add(Restrictions.like("fatorRisco.codigo", codigoGrupoRiscoESocial, MatchMode.START));

		criteria.setProjection(p);
		criteria.addOrder(Order.asc("fatorRisco.codigo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(FatorDeRisco.class));
		return criteria.list();
	}

}
