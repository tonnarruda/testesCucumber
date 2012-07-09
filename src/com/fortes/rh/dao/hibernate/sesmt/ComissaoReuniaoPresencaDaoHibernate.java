package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class ComissaoReuniaoPresencaDaoHibernate extends GenericDaoHibernate<ComissaoReuniaoPresenca> implements ComissaoReuniaoPresencaDao
{
	@SuppressWarnings("unchecked")
	public Collection<ComissaoReuniaoPresenca> findByReuniao(Long comissaoReuniaoId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"presenca");
		criteria.createCriteria("presenca.comissaoReuniao", "cr");
		criteria.createCriteria("presenca.colaborador", "co");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("presenca.id"), "id");
		p.add(Projections.property("presenca.presente"), "presente");
		p.add(Projections.property("presenca.justificativaFalta"), "justificativaFalta");
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("cr.id"), "projectionComissaoReuniaoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cr.id", comissaoReuniaoId));

		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public void removeByReuniao(Long comissaoReuniaoId)
	{
		String hql = "delete from ComissaoReuniaoPresenca crp WHERE crp.comissaoReuniao.id = :comissaoReuniaoId";
		Query query = getSession().createQuery(hql);
		query.setLong("comissaoReuniaoId", comissaoReuniaoId);
		query.executeUpdate();
	}

	public Collection<ComissaoReuniaoPresenca> findByComissao(Long comissaoId, boolean ordenarPorDataNome)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"presenca");
		criteria.createCriteria("presenca.comissaoReuniao", "cr");
		criteria.createCriteria("presenca.colaborador", "co");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("presenca.id"), "id");
		p.add(Projections.property("presenca.presente"), "presente");
		p.add(Projections.property("presenca.justificativaFalta"), "justificativaFalta");
		p.add(Projections.property("co.id"), "projectionColaboradorId");
		p.add(Projections.property("co.nome"), "projectionColaboradorNome");
		p.add(Projections.property("cr.id"), "projectionComissaoReuniaoId");
		p.add(Projections.property("cr.data"), "projectionComissaoReuniaoData");
		p.add(Projections.property("cr.horario"), "projectionComissaoReuniaoHora");
		p.add(Projections.property("cr.descricao"), "projectionComissaoReuniaoDescricao");
		p.add(Projections.property("cr.tipo"), "projectionComissaoReuniaoTipo");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cr.comissao.id", comissaoId));

		if (ordenarPorDataNome)
			criteria.addOrder(Order.asc("cr.data"));

		criteria.addOrder(Order.asc("co.nome"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public boolean existeReuniaoPresensa(Long comissaoId,	Collection<Long> colaboradorIds) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"presenca");
		criteria.createCriteria("presenca.comissaoReuniao", "cr");
		criteria.createCriteria("presenca.colaborador", "co");

		criteria.add(Expression.eq("cr.comissao.id", comissaoId));
		criteria.add(Expression.in("presenca.colaborador.id", colaboradorIds));

		return criteria.list().size() > 0;
	}
}