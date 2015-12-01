package com.fortes.rh.dao.hibernate.desenvolvimento;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;

public class ColaboradorCertificacaoDaoHibernate extends GenericDaoHibernate<ColaboradorCertificacao> implements ColaboradorCertificacaoDao
{
	@SuppressWarnings("unchecked")
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) 
	{
		Criteria criteria = getSession().createCriteria(ColaboradorCertificacao.class, "cc");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cc.id"), "id");
		p.add(Projections.property("cc.data"), "data");

		criteria.setProjection(p);

		criteria.add(Expression.eq("cc.colaborador.id",colaboradorId));
		criteria.add(Expression.eq("cc.certificacao.id" , certificacaoId));

		criteria.addOrder(Order.asc("cc.data"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(ColaboradorCertificacao.class));

		return criteria.list();
	}
}
