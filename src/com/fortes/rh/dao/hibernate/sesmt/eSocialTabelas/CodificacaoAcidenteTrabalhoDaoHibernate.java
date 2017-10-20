package com.fortes.rh.dao.hibernate.sesmt.eSocialTabelas;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.CodificacaoAcidenteTrabalho;

public class CodificacaoAcidenteTrabalhoDaoHibernate extends GenericDaoHibernate<CodificacaoAcidenteTrabalho> implements CodificacaoAcidenteTrabalhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<CodificacaoAcidenteTrabalho> findAllSelect() {
		Criteria criteria = getSession().createCriteria(CodificacaoAcidenteTrabalho.class,"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.codigo"), "codigo");
		p.add(Projections.property("c.descricao"), "descricao");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("c.codigo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(CodificacaoAcidenteTrabalho.class));
		
		return criteria.list();
	}
}
