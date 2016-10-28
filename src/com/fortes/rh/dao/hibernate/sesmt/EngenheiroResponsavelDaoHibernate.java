package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;

@Component
@SuppressWarnings("unchecked")
public class EngenheiroResponsavelDaoHibernate extends GenericDaoHibernate<EngenheiroResponsavel> implements EngenheiroResponsavelDao
{
	public EngenheiroResponsavel findByIdProjection(Long engenheiroResponsavelId)
	{
		Criteria criteria = getSession().createCriteria(EngenheiroResponsavel.class, "e");
		criteria.createCriteria("e.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.inicio"), "inicio");
		p.add(Projections.property("e.fim"), "fim");
		p.add(Projections.property("e.crea"), "crea");
		p.add(Projections.property("e.nit"), "nit");
		p.add(Projections.property("emp.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", engenheiroResponsavelId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(EngenheiroResponsavel.class));

		return (EngenheiroResponsavel) criteria.uniqueResult();
	}
	
	public Collection<EngenheiroResponsavel> findAllByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(EngenheiroResponsavel.class, "e");
		criteria.createCriteria("e.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.inicio"), "inicio");
		p.add(Projections.property("e.fim"), "fim");
		p.add(Projections.property("e.crea"), "crea");
		p.add(Projections.property("e.nit"), "nit");
		p.add(Projections.property("emp.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("emp.id", empresaId));
		
		// Esta ordem é importante para a montagem do relatório
		criteria.addOrder(Order.asc("e.inicio"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(EngenheiroResponsavel.class));

		return criteria.list();
	}
}