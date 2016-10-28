package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.AgendaDao;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;

@Component
@SuppressWarnings("unchecked")
public class AgendaDaoHibernate extends GenericDaoHibernate<Agenda> implements AgendaDao
{

	public Collection<Agenda> findByPeriodo(Date dataIni, Date dataFim, Long empresaId, Estabelecimento estabelecimento)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"a");
		criteria.createCriteria("a.evento", "e");
		criteria.createCriteria("a.estabelecimento", "est");

		ProjectionList p = geraProjectionDosAtributosSimples("a");
		p.add(Projections.property("e.id"), "projectionEventoId");
		p.add(Projections.property("e.nome"), "projectionEventoNome");
		criteria.setProjection(p);

		if(estabelecimento == null || estabelecimento.getId() == null)
			criteria.add(Expression.eq("est.empresa.id", empresaId));
		else
			criteria.add(Expression.eq("est.id", estabelecimento.getId()));
		
		if(dataIni != null && dataFim != null)
			criteria.add(Expression.between("a.data", dataIni, dataFim));
		
		criteria.addOrder(Order.asc("a.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Agenda findByIdProjection(Long id)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"a");
		criteria.createCriteria("a.evento", "e");
		criteria.createCriteria("a.estabelecimento", "est");
		
		ProjectionList p = geraProjectionDosAtributosSimples("a");
		
		p.add(Projections.property("e.id"), "projectionEventoId");
		p.add(Projections.property("e.nome"), "projectionEventoNome");
		p.add(Projections.property("est.id"), "projectionEstabelecimentoId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("a.id", id));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return (Agenda) criteria.uniqueResult();
	}

	public Collection<Agenda> findByPeriodoEvento(Date dataIni, Date dataFim, Estabelecimento estabelecimento, Evento evento)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"a");
		criteria.createCriteria("a.evento", "e");
		criteria.createCriteria("a.estabelecimento", "est");

		ProjectionList p = geraProjectionDosAtributosSimples("a");
		p.add(Projections.property("e.id"), "projectionEventoId");
		p.add(Projections.property("e.nome"), "projectionEventoNome");
		p.add(Projections.property("est.id"), "projectionEstabelecimentoId");
		criteria.setProjection(p);

		criteria.add(Expression.eq("est.id", estabelecimento.getId()));
		criteria.add(Expression.eq("e.id", evento.getId()));
		
		if(dataIni != null && dataFim != null)
			criteria.add(Expression.between("a.data", dataIni, dataFim));
		
		criteria.addOrder(Order.asc("a.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public void deleteByEstabelecimento(Long[] estabelecimentoIds) throws Exception {
		if(estabelecimentoIds != null && estabelecimentoIds.length > 0)
		{
			String hql = "delete Agenda where estabelecimento.id in (:estabelecimentoIds)";
			Query query = getSession().createQuery(hql);

			query.setParameterList("estabelecimentoIds", estabelecimentoIds, StandardBasicTypes.LONG);
			query.executeUpdate();		
		}
	}		
}