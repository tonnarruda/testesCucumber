package com.fortes.rh.dao.hibernate.sesmt.eSocialTabelas;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissionalDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraDoencaProfissional;

public class SituacaoGeradoraDoencaProfissionalDaoHibernate extends GenericDaoHibernate<SituacaoGeradoraDoencaProfissional> implements SituacaoGeradoraDoencaProfissionalDao
{
	@SuppressWarnings("unchecked")
	public Collection<SituacaoGeradoraDoencaProfissional> findAllSelect() {
		Criteria criteria = getSession().createCriteria(SituacaoGeradoraDoencaProfissional.class,"s");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.codigo"), "codigo");
		p.add(Projections.property("s.descricao"), "descricao");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("s.codigo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SituacaoGeradoraDoencaProfissional.class));
		
		return criteria.list();
	}
}
