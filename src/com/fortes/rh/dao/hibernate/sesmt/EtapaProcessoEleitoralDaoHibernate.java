package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManagerImpl;
import com.fortes.rh.dao.sesmt.EtapaProcessoEleitoralDao;
import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;

@Component
@SuppressWarnings("unchecked")
public class EtapaProcessoEleitoralDaoHibernate extends GenericDaoHibernate<EtapaProcessoEleitoral> implements EtapaProcessoEleitoralDao
{
	/**
	 * @see EtapaProcessoEleitoralManagerImpl
	 */
	public Collection<EtapaProcessoEleitoral> findAllSelect(Long empresaId, Long eleicaoId, String orderBy)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.empresa", "emp");
		criteria.createCriteria("e.eleicao", "el", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.prazoLegal"), "prazoLegal");
		p.add(Projections.property("e.prazo"), "prazo");
		p.add(Projections.property("e.data"), "data");
		p.add(Projections.property("emp.id"), "empresaIdProjection");
		p.add(Projections.property("el.id"), "projectionEleicaoId");
		p.add(Projections.property("el.posse"), "projectionEleicaoPosse");

		criteria.setProjection(p);

		if (empresaId != null)
			criteria.add(Expression.eq("emp.id", empresaId));
		if (eleicaoId != null)
			criteria.add(Expression.eq("el.id", eleicaoId));
		else
			criteria.add(Expression.isNull(("e.eleicao")));

		// A ordem é por "prazo", na tela das Etapas
		// e por "data", no calendário da eleição.
		if (StringUtils.isNotBlank(orderBy))
			criteria.addOrder(Order.asc("e." + orderBy));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(EtapaProcessoEleitoral.class));

		return criteria.list();
	}

	public void removeByEleicao(Long eleicaoId)
	{
		String hql = "delete from EtapaProcessoEleitoral where eleicao_id = :eleicaoId";
		Query query = getSession().createQuery(hql);
		query.setLong("eleicaoId", eleicaoId);
		query.executeUpdate();
	}
}