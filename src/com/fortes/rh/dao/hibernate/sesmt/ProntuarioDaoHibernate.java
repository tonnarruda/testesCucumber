package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ProntuarioDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Prontuario;

@Component
@SuppressWarnings("unchecked")
public class ProntuarioDaoHibernate extends GenericDaoHibernate<Prontuario> implements ProntuarioDao
{
	public Collection<Prontuario> findByColaborador(Colaborador colaborador)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"p");
		criteria.createCriteria("p.usuario", "u", CriteriaSpecification.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("p.id"), "id");
		p.add(Projections.property("p.data"), "data");
		p.add(Projections.property("p.descricao"), "descricao");
		p.add(Projections.property("u.login"), "projectionUsuarioLogin");
		criteria.setProjection(p);

		if(colaborador != null && colaborador.getId() != null)
			criteria.add(Expression.eq("p.colaborador.id", colaborador.getId()));

		criteria.addOrder( Order.desc("p.data") );
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return criteria.list();
	}

	public Integer findQtdByEmpresa(Long empresaId, Date dataIni, Date dataFim)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(),"p");
		criteria.createCriteria("p.colaborador", "c");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.count("p.id"));
		criteria.setProjection(p);
		
		criteria.add(Expression.between("p.data", dataIni, dataFim));
		criteria.add(Expression.eq("c.empresa.id", empresaId));
		
		return (Integer) criteria.uniqueResult();
	}
}