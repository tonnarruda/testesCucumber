package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;

public class FaturamentoMensalDaoHibernate extends GenericDaoHibernate<FaturamentoMensal> implements FaturamentoMensalDao
{

	public Collection<FaturamentoMensal> findAllSelect(Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.mesAno"), "mesAno");
		p.add(Projections.property("f.valor"), "valor");
		p.add(Projections.property("f.empresa.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("f.empresa.id", empresaId));
		criteria.addOrder(Order.asc("f.mesAno"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
}
