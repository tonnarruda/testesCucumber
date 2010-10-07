package com.fortes.rh.dao.hibernate.cargosalario;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.cargosalario.GrupoOcupacionalDao;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;

public class GrupoOcupacionalDaoHibernate extends GenericDaoHibernate<GrupoOcupacional> implements GrupoOcupacionalDao
{
	public Integer getCount(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");
		criteria.setProjection(Projections.rowCount());

		montaConsulta(criteria, empresaId);

		return (Integer) criteria.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public Collection<GrupoOcupacional> findAllSelect(int page, int pagingSize, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");

		criteria.setProjection(p);

		montaConsulta(criteria, empresaId);

		criteria.addOrder(Order.asc("g.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoOcupacional.class));

		// Se page e pagingSize = 0, chanada do método sobrecarregado sem paginação
		if(pagingSize > 0)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		return criteria.list();
	}

	private void montaConsulta(Criteria criteria, Long empresaId)
	{
		criteria.add(Expression.eq("g.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

	public GrupoOcupacional findByIdProjection(Long grupoOcupacionalId)
	{
		Criteria criteria = getSession().createCriteria(GrupoOcupacional.class,"g");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("g.id"), "id");
		p.add(Projections.property("g.nome"), "nome");
		p.add(Projections.property("g.empresa.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("g.id", grupoOcupacionalId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(GrupoOcupacional.class));

		return (GrupoOcupacional) criteria.uniqueResult();
	}
}
