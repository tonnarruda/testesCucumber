package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.GastoEmpresaItemDao;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;

@Component
public class GastoEmpresaItemDaoHibernate extends GenericDaoHibernate<GastoEmpresaItem> implements GastoEmpresaItemDao
{
	public void removeGastos(GastoEmpresa gastoEmpresa)
	{
		String queryHQL = "delete from GastoEmpresaItem g where g.gastoEmpresa.id = :valor";

		Session newSession = getSession();
		newSession.createQuery(queryHQL).setLong("valor",gastoEmpresa.getId()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Collection<GastoEmpresaItem> getGastosImportaveis(GastoEmpresa gastoEmpresa)
	{
		Criteria criteria = getSession().createCriteria(GastoEmpresaItem.class, "ge");
		criteria.createCriteria("ge.gasto", "g", CriteriaSpecification.LEFT_JOIN);

		criteria.add(Expression.eq("ge.gastoEmpresa.id", gastoEmpresa.getId()));
		criteria.add(Expression.eq("g.naoImportar", false));

		criteria.addOrder(Order.asc("g.nome"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}
}