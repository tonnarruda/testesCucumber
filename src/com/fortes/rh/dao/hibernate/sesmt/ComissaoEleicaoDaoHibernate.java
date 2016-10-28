package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoEleicaoDao;
import com.fortes.rh.model.sesmt.ComissaoEleicao;

@Component
@SuppressWarnings("unchecked")
public class ComissaoEleicaoDaoHibernate extends GenericDaoHibernate<ComissaoEleicao> implements ComissaoEleicaoDao
{
	public Collection<ComissaoEleicao> findByEleicao(Long eleicaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");
		criteria = criteria.createCriteria("c.colaborador", "colab");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.funcao"), "funcao");
		p.add(Projections.property("c.eleicao.id"), "projectionEleicaoId");
		p.add(Projections.property("colab.id"), "projectionColaboradorId");
		p.add(Projections.property("colab.nome"), "projectionColaboradorNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.eleicao.id", eleicaoId));

		criteria.addOrder( Order.asc("colab.nome") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public ComissaoEleicao findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.funcao"), "funcao");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (ComissaoEleicao) criteria.uniqueResult();
	}

	public void updateFuncao(Long id, String funcao)
	{
		String hql = "update ComissaoEleicao set funcao = :funcao where id = :id";

		Query query = getSession().createQuery(hql);

		query.setLong("id", id);
		query.setString("funcao", funcao);

		query.executeUpdate();
	}

	public void removeByEleicao(Long eleicaoId)
	{
		String hql = "delete from ComissaoEleicao where eleicao_id = :eleicaoId";

		Query query = getSession().createQuery(hql);

		query.setLong("eleicaoId", eleicaoId);

		query.executeUpdate();
	}
}