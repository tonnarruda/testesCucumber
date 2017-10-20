package com.fortes.rh.dao.hibernate.sesmt.eSocialTabelas;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.SituacaoGeradoraAcidenteTrabalho;

public class SituacaoGeradoraAcidenteTrabalhoDaoHibernate extends GenericDaoHibernate<SituacaoGeradoraAcidenteTrabalho> implements SituacaoGeradoraAcidenteTrabalhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<SituacaoGeradoraAcidenteTrabalho> findAllSelect() {
		Criteria criteria = getSession().createCriteria(SituacaoGeradoraAcidenteTrabalho.class,"s");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("s.id"), "id");
		p.add(Projections.property("s.codigo"), "codigo");
		p.add(Projections.property("s.descricao"), "descricao");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("s.codigo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(SituacaoGeradoraAcidenteTrabalho.class));
		
		return criteria.list();
	}
}
