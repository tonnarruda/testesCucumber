package com.fortes.rh.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.model.geral.Gasto;

@Component
@SuppressWarnings("unchecked")
public class GastoDaoHibernate extends GenericDaoHibernate<Gasto> implements GastoDao
{
	public Collection<Gasto> getGastosSemGrupo(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Gasto.class);
		criteria.add(Expression.isNull("grupoGasto"));
		criteria.addOrder(Order.asc("nome"));

		criteria.add(Expression.eq("empresa.id", empresaId));

		Collection<Gasto> result = criteria.list();

		if (result.isEmpty())
			return new ArrayList<Gasto>();
		else
			return result;
	}

	public Collection<Gasto> findGastosDoGrupo(Long id)
	{
		Criteria criteria = getSession().createCriteria(Gasto.class, "g");
		criteria.createCriteria("g.grupoGasto", "gg");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("gg.id", id));
		criteria.addOrder(Order.asc("g.nome"));


		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Gasto.class));

		return criteria.list();
	}

	public Collection<Gasto> findByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(Gasto.class, "g");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");

		criteria.setProjection(p);

		criteria.add(Expression.isNull("g.codigoAc"));
		criteria.add(Expression.eq("g.empresa.id", empresaId));
		criteria.addOrder(Order.asc("g.nome"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Gasto.class));

		return criteria.list();
	}

	public void updateGrupoGastoByGastos(Long grupoGastoId, Long[] gastosIds)
	{
		String hql = "update Gasto set grupoGasto = :grupoGastoId where id in (:gastosIds)";

		Query query = getSession().createQuery(hql);

		query.setLong("grupoGastoId", grupoGastoId);
		query.setParameterList("gastosIds", gastosIds, StandardBasicTypes.LONG);

		query.executeUpdate();
	}

	public Gasto findByIdProjection(Long gastoId)
	{
		Criteria criteria = getSession().createCriteria(Gasto.class, "g");
		criteria.createCriteria("g.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");
		p.add(Projections.property("g.naoImportar"), "naoImportar");
		p.add(Projections.property("g.codigoAc"), "codigoAc");
		p.add(Projections.property("e.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("g.id", gastoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Gasto.class));

		return (Gasto) criteria.uniqueResult();
	}

	public Collection<Gasto> findSemCodigoAC(Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "g");
		criteria.createCriteria("g.empresa", "e");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");
		p.add(Projections.property("e.nome"), "projectionEmpresaNome");

		criteria.setProjection(p);
		
		criteria.add(Expression.or(Expression.isNull("g.codigoAc"), Expression.eq("g.codigoAc","")));
		
		if(empresaId != null)
			criteria.add(Expression.eq("e.id", empresaId));

		criteria.addOrder(Order.asc("e.nome"));
		criteria.addOrder(Order.asc("g.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();	
	}
}