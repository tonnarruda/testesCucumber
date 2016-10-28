package com.fortes.rh.dao.hibernate.sesmt;


import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.TamanhoEPIDao;
import com.fortes.rh.model.sesmt.TamanhoEPI;

@Component
public class TamanhoEPIDaoHibernate extends GenericDaoHibernate<TamanhoEPI> implements TamanhoEPIDao {

	@SuppressWarnings("unchecked")
	public Collection<TamanhoEPI> findAllByTipoEPIId(Long tipoEPIId) {
		
		Criteria criteria = getSession().createCriteria(TamanhoEPI.class, "te");
		criteria.createCriteria("te.tipoEPIs", "tpe");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("te.id"), "id");
		p.add(Projections.property("te.descricao"), "descricao");
		
		criteria.setProjection(p);
		criteria.add(Expression.eq("tpe.id", tipoEPIId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(TamanhoEPI.class));

		return criteria.list();
	}

}