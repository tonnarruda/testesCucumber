package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ComposicaoSesmtDao;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;

@Component
@SuppressWarnings("unchecked")
public class ComposicaoSesmtDaoHibernate extends GenericDaoHibernate<ComposicaoSesmt> implements ComposicaoSesmtDao
{
	public Collection<ComposicaoSesmt> findAllSelect(Long empresaId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.data"), "data");
		p.add(Projections.property("c.qtdTecnicosSeguranca"), "qtdTecnicosSeguranca");   
		p.add(Projections.property("c.qtdEngenheirosSeguranca"), "qtdEngenheirosSeguranca");
		p.add(Projections.property("c.qtdAuxiliaresEnfermagem"), "qtdAuxiliaresEnfermagem");
		p.add(Projections.property("c.qtdEnfermeiros"), "qtdEnfermeiros");         
		p.add(Projections.property("c.qtdMedicos"), "qtdMedicos");             
		p.add(Projections.property("c.empresa.id"), "projectionEmpresaId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.addOrder(Order.desc("c.data"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public ComposicaoSesmt findByIdProjection(Long composicaoSesmtId) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.data"), "data");
		p.add(Projections.property("c.qtdTecnicosSeguranca"), "qtdTecnicosSeguranca");   
		p.add(Projections.property("c.qtdEngenheirosSeguranca"), "qtdEngenheirosSeguranca");
		p.add(Projections.property("c.qtdAuxiliaresEnfermagem"), "qtdAuxiliaresEnfermagem");
		p.add(Projections.property("c.qtdEnfermeiros"), "qtdEnfermeiros");         
		p.add(Projections.property("c.qtdMedicos"), "qtdMedicos");             
		p.add(Projections.property("c.empresa.id"), "projectionEmpresaId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("c.id", composicaoSesmtId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (ComposicaoSesmt) criteria.uniqueResult();
	}

	public ComposicaoSesmt findByData(Long empresaId, Date data) {
		Criteria criteria = getSession().createCriteria(getEntityClass(), "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("c.data"), "data");
		p.add(Projections.property("c.qtdTecnicosSeguranca"), "qtdTecnicosSeguranca");   
		p.add(Projections.property("c.qtdEngenheirosSeguranca"), "qtdEngenheirosSeguranca");
		p.add(Projections.property("c.qtdAuxiliaresEnfermagem"), "qtdAuxiliaresEnfermagem");
		p.add(Projections.property("c.qtdEnfermeiros"), "qtdEnfermeiros");         
		p.add(Projections.property("c.qtdMedicos"), "qtdMedicos");             
		p.add(Projections.property("c.empresa.id"), "projectionEmpresaId");
		criteria.setProjection(p);
		
		criteria.add(Expression.le("c.data", data));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		criteria.addOrder(Order.desc("c.data"));
		criteria.setMaxResults(1);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (ComposicaoSesmt) criteria.uniqueResult();
	}
}
