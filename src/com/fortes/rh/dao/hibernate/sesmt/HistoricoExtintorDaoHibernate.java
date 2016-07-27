package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

@SuppressWarnings("unchecked")
public class HistoricoExtintorDaoHibernate extends GenericDaoHibernate<HistoricoExtintor> implements HistoricoExtintorDao
{
	public Collection<HistoricoExtintor> findByExtintor(Long extintorId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoExtintor.class, "hist");
		criteria.createCriteria("hist.estabelecimento", "est");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hist.id"), "id");
		p.add(Projections.property("hist.data"), "data");
		p.add(Projections.property("hist.localizacao"), "localizacao");
		p.add(Projections.property("est.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("est.nome"), "projectionEstabelecimentoNome");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("hist.extintor.id", extintorId));
		
		criteria.addOrder(Order.asc("hist.data"));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoExtintor.class));
		
		return criteria.list();
	}

	public void removeByExtintor(Long extintorId) 
	{
		Query query = getSession().createQuery("delete from HistoricoExtintor hist where hist.extintor.id=:extintorId");
		query.setLong("extintorId", extintorId);
		
		query.executeUpdate();
	}

	public Collection<HistoricoExtintor> findAllHistoricosAtuais(Integer page, Integer pagingSize, Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Extintor.class, "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("he.id"), "id");
		p.add(Projections.property("he.data"), "data");
		p.add(Projections.property("he.localizacao"), "localizacao");
		p.add(Projections.property("e.id"), "projectionExtintorId");
		p.add(Projections.property("e.tipo"), "projectionExtintorTipo");
		p.add(Projections.property("e.numeroCilindro"), "projectionExtintorNumeroCilindro");
		p.add(Projections.property("est.id"), "projectionEstabelecimentoId");
		p.add(Projections.property("est.nome"), "projectionEstabelecimentoNome");
		
		criteria.setProjection(p);
		montaConsultaHistoricoAtual(data, localizacao, extintorTipo, estabelecimentoId, empresaId, criteria);
		
		criteria.addOrder(Order.desc("he.data"));
		criteria.addOrder(Order.asc("he.localizacao"));
		criteria.addOrder(Order.asc("e.tipo"));
		if(page != null)
		{
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);			
		}
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoExtintor.class));

		return criteria.list();
	}

	public Integer countAllHistoricosAtuais(Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId) 
	{
		Criteria criteria = getSession().createCriteria(Extintor.class, "e");
		criteria.setProjection(Projections.rowCount());
		montaConsultaHistoricoAtual(data, localizacao, extintorTipo, estabelecimentoId,empresaId, criteria);
		
		return (Integer) criteria.list().get(0);
	}
	
	private void montaConsultaHistoricoAtual(Date data, String localizacao, String extintorTipo, Long estabelecimentoId, Long empresaId, Criteria criteria) 
	{
		criteria.createCriteria("e.historicoExtintores", "he", Criteria.LEFT_JOIN);
		criteria.createCriteria("he.estabelecimento", "est");
		
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoExtintor.class, "he2");
        ProjectionList pSub = Projections.projectionList().create();

        pSub.add(Projections.max("he2.data"));
        subQuery.setProjection(pSub);
        
        if (data != null) 
        	subQuery.add(Expression.ge("he2.data", data));
        subQuery.add(Restrictions.sqlRestriction("this0__.extintor_id=this_.id"));
		
		criteria.add(Expression.eq("est.empresa.id", empresaId));
		criteria.add(Subqueries.propertyEq("he.data", subQuery));

		if(localizacao != null && !localizacao.equals(""))
			criteria.add(Restrictions.sqlRestriction("normalizar(he1_.localizacao) ilike  normalizar(?)", "%" + localizacao.trim() + "%", Hibernate.STRING));

		if(extintorTipo != null && !extintorTipo.equals(""))
			criteria.add(Expression.eq("e.tipo", extintorTipo));
		
		if(estabelecimentoId != null)
			criteria.add(Expression.eq("est.id", estabelecimentoId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}
}