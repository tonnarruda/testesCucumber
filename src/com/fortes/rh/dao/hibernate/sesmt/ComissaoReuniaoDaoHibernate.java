package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.model.sesmt.ComissaoReuniao;

/**
 * @author Tiago Teixeira Lopes
 *
 */
@Component
@SuppressWarnings("unchecked")
public class ComissaoReuniaoDaoHibernate extends GenericDaoHibernate<ComissaoReuniao> implements ComissaoReuniaoDao
{
	public ComissaoReuniao findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"reuniao");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("reuniao.id"), "id");
		p.add(Projections.property("reuniao.data"), "data");
		p.add(Projections.property("reuniao.descricao"), "descricao");
		p.add(Projections.property("reuniao.horario"), "horario");
		p.add(Projections.property("reuniao.localizacao"), "localizacao");
		p.add(Projections.property("reuniao.tipo"), "tipo");
		p.add(Projections.property("reuniao.ata"), "ata");
		p.add(Projections.property("reuniao.obsReuniaoAnterior"), "obsReuniaoAnterior");
		p.add(Projections.property("reuniao.comissao.id"), "projectionComissaoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("reuniao.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (ComissaoReuniao) criteria.uniqueResult();
	}

	public Collection<ComissaoReuniao> findByComissao(Long comissaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"reuniao");
		criteria.createCriteria("reuniao.comissao", "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("reuniao.id"), "id");
		p.add(Projections.property("reuniao.data"), "data");
		p.add(Projections.property("reuniao.descricao"), "descricao");
		p.add(Projections.property("reuniao.horario"), "horario");
		p.add(Projections.property("reuniao.localizacao"), "localizacao");
		p.add(Projections.property("reuniao.tipo"), "tipo");
		p.add(Projections.property("c.id"), "projectionComissaoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("c.id", comissaoId));

		criteria.addOrder(Order.asc("reuniao.data"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}
}