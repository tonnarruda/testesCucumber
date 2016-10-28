/* Autor: Igo Coelho
 * Data: 29/06/2006
 * Requisito: RFA020 */
package com.fortes.rh.dao.hibernate.captacao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.captacao.EmpresaBdsDao;
import com.fortes.rh.model.captacao.EmpresaBds;

@Component
public class EmpresaBdsDaoHibernate extends GenericDaoHibernate<EmpresaBds> implements EmpresaBdsDao
{
	public Collection<EmpresaBds> findAllProjection(Long[] empresaBdsIds)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.email"), "email");
		criteria.setProjection(p);

		criteria.add(Expression.in("e.id", empresaBdsIds));

		criteria.setProjection(Projections.distinct(p));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public EmpresaBds findByIdProjection(Long empresaBdsId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.empresa", "emp", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.contato"), "contato");
		p.add(Projections.property("e.fone"), "fone");
		p.add(Projections.property("e.email"), "email");
		p.add(Projections.property("e.ddd"), "ddd");
		p.add(Projections.property("emp.id"), "projectionEmpresaId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", empresaBdsId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (EmpresaBds) criteria.uniqueResult();
	}

}