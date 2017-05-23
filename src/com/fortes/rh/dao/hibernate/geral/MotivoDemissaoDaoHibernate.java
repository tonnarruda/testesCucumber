package com.fortes.rh.dao.hibernate.geral;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.model.geral.MotivoDemissao;

public class MotivoDemissaoDaoHibernate extends GenericDaoHibernate<MotivoDemissao> implements MotivoDemissaoDao
{
	@SuppressWarnings("unchecked")
	public Collection<MotivoDemissao> findAllSelect(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(MotivoDemissao.class,"m");
		criteria.createCriteria("m.empresa", "emp", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("m.id"), "id");
		p.add(Projections.property("m.motivo"), "motivo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("emp.id", empresaId));

		criteria.addOrder(Order.asc("m.motivo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(MotivoDemissao.class));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<MotivoDemissao> findMotivoDemissao(Integer page, Integer pagingSize, Long empresaId, String motivo, Boolean ativo) {
		Criteria criteria = getSession().createCriteria(MotivoDemissao.class, "m");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("m.id"), "id");
		p.add(Projections.property("m.motivo"), "motivo");
		p.add(Projections.property("m.reducaoDeQuadro"), "reducaoDeQuadro");
		p.add(Projections.property("m.turnover"), "turnover");
		p.add(Projections.property("m.ativo"), "ativo");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("m.empresa.id", empresaId));

		if(motivo != null && !"".equals(motivo.trim()))
			criteria.add(Expression.ilike("m.motivo","%"+ motivo.trim() +"%"));

		if(ativo != null)
			criteria.add(Expression.eq("m.ativo", ativo));

		criteria.addOrder(Order.asc("m.motivo"));

		if(pagingSize != null && pagingSize > 0){
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
		}

		criteria.setResultTransformer(new AliasToBeanResultTransformer(MotivoDemissao.class));

		return criteria.list();
	}
}