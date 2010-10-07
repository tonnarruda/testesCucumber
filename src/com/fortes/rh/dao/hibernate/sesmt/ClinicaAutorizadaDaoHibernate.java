package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ClinicaAutorizadaDao;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;

@SuppressWarnings("unchecked")
public class ClinicaAutorizadaDaoHibernate extends GenericDaoHibernate<ClinicaAutorizada> implements ClinicaAutorizadaDao
{
	public Collection<ClinicaAutorizada> findClinicasAtivasByDataEmpresa(Long empresaId, Date data)
	{
		Criteria criteria = getSession().createCriteria(ClinicaAutorizada.class, "ca");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ca.id"), "id");
		p.add(Projections.property("ca.nome"), "nome");
		p.add(Projections.property("ca.crm"), "crm");
		p.add(Projections.property("ca.cnpj"), "cnpj");
		p.add(Projections.property("ca.tipo"), "tipo");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ca.empresa.id", empresaId));
		criteria.add(Expression.le("ca.data", data));
		criteria.add(Expression.isNull("ca.dataInativa"));

		criteria.addOrder(Order.asc("ca.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ClinicaAutorizada.class));

		return criteria.list();
	}

	public Collection<ClinicaAutorizada> findByExame(Long empresaId, Long exameId, Date data)
	{
		Criteria criteria = getSession().createCriteria(ClinicaAutorizada.class, "ca");
		criteria.createCriteria("ca.exames", "ex");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ca.id"), "id");
		p.add(Projections.property("ca.nome"), "nome");
		p.add(Projections.property("ca.crm"), "crm");
		p.add(Projections.property("ca.cnpj"), "cnpj");
		p.add(Projections.property("ca.tipo"), "tipo");

		criteria.setProjection(p);

		criteria.add(Expression.eq("ca.empresa.id", empresaId));
		criteria.add(Expression.le("ca.data", data));
		criteria.add(Expression.isNull("ca.dataInativa"));
		criteria.add(Expression.eq("ex.id", exameId));

		criteria.addOrder(Order.asc("ca.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ClinicaAutorizada.class));

		return criteria.list();
	}
}