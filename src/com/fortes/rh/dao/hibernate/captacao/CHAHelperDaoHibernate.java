package com.fortes.rh.dao.hibernate.captacao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.model.AbstractModel;


public class CHAHelperDaoHibernate {
	
	public static Criteria montafindByIdProjection(Long Id, Session session, Class<? extends AbstractModel> entity)
	{
		Criteria criteria = session.createCriteria(entity, "entity");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("entity.id"), "id");
		p.add(Projections.property("entity.nome"), "nome");
		p.add(Projections.property("entity.observacao"), "observacao");
		p.add(Projections.property("entity.peso"), "peso");
		p.add(Projections.property("entity.empresa.id"), "empresaId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("entity.id", Id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(entity));
		
		return criteria;
	}

}
