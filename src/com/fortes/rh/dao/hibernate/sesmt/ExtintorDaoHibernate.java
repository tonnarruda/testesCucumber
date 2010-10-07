package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.model.sesmt.Extintor;

@SuppressWarnings("unchecked")
public class ExtintorDaoHibernate extends GenericDaoHibernate<Extintor> implements ExtintorDao
{
	public Integer getCount(Long empresaId, String tipoBusca, Integer numeroBusca, Boolean ativo)
	{
		Criteria criteria = montaConsultaFind(true, empresaId, tipoBusca, numeroBusca, ativo);

		return (Integer)criteria.uniqueResult();
	}

	private Criteria montaConsultaFind(boolean isCount, Long empresaId, String tipoBusca, Integer numeroBusca, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");

		if (isCount)
			criteria.setProjection(Projections.rowCount());
		else
		{
			ProjectionList p = Projections.projectionList().create();
			p.add(Projections.property("e.id"), "id");
			p.add(Projections.property("e.localizacao"), "localizacao");
			p.add(Projections.property("e.numeroCilindro"), "numeroCilindro");
			p.add(Projections.property("e.tipo"), "tipo");

			criteria.setProjection(p);

			criteria.addOrder(Order.asc("e.tipo"));
			criteria.addOrder(Order.asc("e.localizacao"));
			criteria.addOrder(Order.asc("e.numeroCilindro"));

			criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		}

		criteria.add(Expression.eq("e.empresa.id", empresaId));

		if (isNotBlank(tipoBusca))
			criteria.add(Expression.eq("e.tipo", tipoBusca));

		if (numeroBusca != null)
			criteria.add(Expression.eq("e.numeroCilindro", numeroBusca));

		if (ativo != null)
			criteria.add(Expression.eq("e.ativo", ativo));

		return criteria;
	}

	public Collection<Extintor> findAllSelect(int page, int pagingSize, Long empresaId, String tipoBusca, Integer numeroBusca, Boolean ativo)
	{
		Criteria criteria = montaConsultaFind(false, empresaId, tipoBusca, numeroBusca, ativo);

		if(pagingSize != 0)
        {
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
        }

		return criteria.list();
	}

	public Collection<Extintor> findByEstabelecimento(Long estabelecimentoId, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.localizacao"), "localizacao");
		p.add(Projections.property("e.numeroCilindro"), "numeroCilindro");
		p.add(Projections.property("e.tipo"), "tipo");

		criteria.setProjection(p);
		criteria.addOrder(Order.asc("e.tipo"));
		criteria.addOrder(Order.asc("e.localizacao"));
		criteria.addOrder(Order.asc("e.numeroCilindro"));

		criteria.add(Expression.eq("e.estabelecimento.id", estabelecimentoId));

		if (ativo != null)
			criteria.add(Expression.eq("e.ativo", ativo));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Collection<String> findFabricantesDistinctByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.fabricante"), "fabricante");
		
		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("e.fabricante"));
		
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		return criteria.list();
	}

	public Collection<String> findLocalizacoesDistinctByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.localizacao"), "localizacao");
		
		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("e.localizacao"));
		
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		return criteria.list();
	}
}