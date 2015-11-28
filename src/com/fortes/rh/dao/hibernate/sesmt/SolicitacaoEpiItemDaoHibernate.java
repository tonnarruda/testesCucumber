package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;

/**
 * @author Tiago Lopes
 *
 */
@SuppressWarnings("unchecked")
public class SolicitacaoEpiItemDaoHibernate extends GenericDaoHibernate<SolicitacaoEpiItem> implements SolicitacaoEpiItemDao
{
	public Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"sei");
		criteria.createCriteria("sei.solicitacaoEpi", "se");
		criteria.createCriteria("sei.epi", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("sei.id"), "id");
		p.add(Projections.property("sei.qtdSolicitado"), "qtdSolicitado");
		p.add(Projections.property("sei.motivoSolicitacaoEpi"), "motivoSolicitacaoEpi");
		p.add(Projections.property("se.id"), "projectionSolicitacaoEpiId");
		p.add(Projections.property("se.data"), "projectionSolicitacaoEpiData");
		p.add(Projections.property("e.id"), "projectionEpiId");
		p.add(Projections.property("e.nome"), "projectionEpiNome");
		p.add(Projections.property("e.fabricante"), "projectionEpiFabricante");
		p.add(Projections.property("e.epiHistoricos"), "projectionEpiHistorico");
		p.add(Projections.property("e.ativo"), "projectionEpiAtivo");
		p.add(Projections.property("sei.tamanhoEPI"), "tamanhoEPI");

		criteria.setProjection(p);

		criteria.add(Expression.eq("se.id", solicitacaoEpiId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public SolicitacaoEpiItem findBySolicitacaoEpiAndEpi(Long solicitacaoEpiId, Long epiId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Expression.eq("solicitacaoEpi.id", solicitacaoEpiId));
		criteria.add(Expression.eq("epi.id", epiId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (SolicitacaoEpiItem) criteria.uniqueResult();
	}

	public void removeAllBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		String hql = "delete SolicitacaoEpiItem item where item.solicitacaoEpi.id = :id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", solicitacaoEpiId);
		query.executeUpdate();
	}

	public Collection<SolicitacaoEpiItem> findAllEntregasBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"sei");
		criteria.createCriteria("sei.solicitacaoEpiItemEntregas", "seie", Criteria.LEFT_JOIN);
		criteria.createCriteria("sei.epi", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("sei.id"), "id");
		p.add(Projections.property("seie.qtdEntregue"), "totalEntregue");
		p.add(Projections.property("sei.qtdSolicitado"), "qtdSolicitado");
		p.add(Projections.property("e.nome"), "projectionEpiNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("sei.solicitacaoEpi.id", solicitacaoEpiId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}
	
	public Collection<SolicitacaoEpiItem> findAllDevolucoesBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"sei");
		criteria.createCriteria("sei.solicitacaoEpiItemDevolucoes", "seid", Criteria.LEFT_JOIN);
		criteria.createCriteria("sei.solicitacaoEpiItemEntregas", "seie", Criteria.LEFT_JOIN);
		criteria.createCriteria("sei.epi", "e", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("sei.id"), "id");
		p.add(Projections.property("seid.qtdDevolvida"), "totalDevolvido");
		p.add(Projections.property("sei.qtdSolicitado"), "qtdSolicitado");
		p.add(Projections.property("seie.qtdEntregue"), "totalEntregue");
		p.add(Projections.property("e.nome"), "projectionEpiNome");

		criteria.setProjection(p);

		criteria.add(Expression.eq("sei.solicitacaoEpi.id", solicitacaoEpiId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public SolicitacaoEpiItem findByIdProjection(Long id) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"item");
		criteria.createCriteria("item.epi", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("item.id"), "id");
		p.add(Projections.property("e.id"), "projectionEpiId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("item.id", id));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (SolicitacaoEpiItem) criteria.uniqueResult();
	}
	
	public Integer countByTipoEPIAndTamanhoEPI(Long tipoEPIId, Long tamanhoEPIId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"sei");
		criteria.createCriteria("sei.epi", "e");

		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Expression.eq("e.tipoEPI.id", tipoEPIId));
		criteria.add(Expression.eq("sei.tamanhoEPI.id", tamanhoEPIId));

		return (Integer) criteria.uniqueResult();
	}
}