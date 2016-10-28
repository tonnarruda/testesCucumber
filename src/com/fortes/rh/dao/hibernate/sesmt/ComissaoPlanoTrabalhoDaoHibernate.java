package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

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
import com.fortes.rh.dao.sesmt.ComissaoPlanoTrabalhoDao;
import com.fortes.rh.model.sesmt.ComissaoPlanoTrabalho;

/**
 * @author Tiago Lopes
 *
 */
@Component
@SuppressWarnings("unchecked")
public class ComissaoPlanoTrabalhoDaoHibernate extends GenericDaoHibernate<ComissaoPlanoTrabalho> implements ComissaoPlanoTrabalhoDao
{
	public Collection<ComissaoPlanoTrabalho> findByComissao(Long comissaoId, String situacao, Long responsavelId, Long corresponsavelId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"cp");
		criteria.createCriteria("cp.responsavel", "r", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cp.corresponsavel", "cr", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cp.id"), "id");
		p.add(Projections.property("cp.prazo"), "prazo");
		p.add(Projections.property("cp.descricao"), "descricao");
		p.add(Projections.property("cp.situacao"), "situacao");
		p.add(Projections.property("cp.prioridade"), "prioridade");
		p.add(Projections.property("cp.parecer"), "parecer");
		p.add(Projections.property("cp.comissao.id"), "projectionComissaoId");
		p.add(Projections.property("r.id"), "projectionResponsavelId");
		p.add(Projections.property("r.nome"), "projectionResponsavelNome");
		p.add(Projections.property("cr.id"), "projectionCorresponsavelId");
		p.add(Projections.property("cr.nome"), "projectionCorresponsavelNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cp.comissao.id", comissaoId));
		if (situacao != null && !situacao.equals("TODAS"))
			criteria.add(Expression.eq("cp.situacao", situacao));
		if (responsavelId != null && responsavelId != -1)
			criteria.add(Expression.eq("r.id", responsavelId));
		if (corresponsavelId != null && corresponsavelId != -1)
			criteria.add(Expression.eq("cr.id", corresponsavelId));
		
		criteria.addOrder(Order.asc("cp.prazo"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return criteria.list();
	}

	public ComissaoPlanoTrabalho findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"cp");
		criteria.createCriteria("cp.responsavel", "r", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("cp.corresponsavel", "cr", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("cp.id"), "id");
		p.add(Projections.property("cp.prazo"), "prazo");
		p.add(Projections.property("cp.descricao"), "descricao");
		p.add(Projections.property("cp.detalhes"), "detalhes");
		p.add(Projections.property("cp.situacao"), "situacao");
		p.add(Projections.property("cp.prioridade"), "prioridade");
		p.add(Projections.property("cp.parecer"), "parecer");
		p.add(Projections.property("cp.comissao.id"), "projectionComissaoId");
		p.add(Projections.property("r.id"), "projectionResponsavelId");
		p.add(Projections.property("r.nome"), "projectionResponsavelNome");
		p.add(Projections.property("cr.id"), "projectionCorresponsavelId");
		p.add(Projections.property("cr.nome"), "projectionCorresponsavelNome");
		criteria.setProjection(p);

		criteria.add(Expression.eq("cp.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		return (ComissaoPlanoTrabalho) criteria.uniqueResult();
	}

	public void removeByComissao(Long comissaoId)
	{
		String hql = "delete from ComissaoPlanoTrabalho cpt where cpt.comissao.id = :comissaoId";
		Query query = getSession().createQuery(hql);
		query.setLong("comissaoId", comissaoId);
		query.executeUpdate();
	}
}