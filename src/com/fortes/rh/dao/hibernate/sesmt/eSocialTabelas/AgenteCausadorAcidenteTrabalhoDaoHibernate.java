package com.fortes.rh.dao.hibernate.sesmt.eSocialTabelas;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalhoDao;
import com.fortes.rh.model.sesmt.eSocialTabelas.AgenteCausadorAcidenteTrabalho;

public class AgenteCausadorAcidenteTrabalhoDaoHibernate extends GenericDaoHibernate<AgenteCausadorAcidenteTrabalho> implements AgenteCausadorAcidenteTrabalhoDao
{
	@SuppressWarnings("unchecked")
	public Collection<AgenteCausadorAcidenteTrabalho> findAllSelect() {
		Criteria criteria = getSession().createCriteria(AgenteCausadorAcidenteTrabalho.class,"a");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("a.id"), "id");
		p.add(Projections.property("a.codigo"), "codigo");
		p.add(Projections.property("a.descricao"), "descricao");
		criteria.setProjection(p);

		criteria.addOrder(Order.asc("a.codigo"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AgenteCausadorAcidenteTrabalho.class));
		
		return criteria.list();
	}
}
