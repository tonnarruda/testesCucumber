package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.EngenheiroResponsavelDao;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;

@SuppressWarnings("unchecked")
public class EngenheiroResponsavelDaoHibernate extends GenericDaoHibernate<EngenheiroResponsavel> implements EngenheiroResponsavelDao
{
	public EngenheiroResponsavel findByIdProjection(Long engenheiroResponsavelId)
	{
		Criteria criteria = getSession().createCriteria(EngenheiroResponsavel.class, "e");
		criteria.createCriteria("e.empresa", "emp");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("e.nome"), "nome");
		p.add(Projections.property("e.inicio"), "inicio");
		p.add(Projections.property("e.fim"), "fim");
		p.add(Projections.property("e.crea"), "crea");
		p.add(Projections.property("e.nit"), "nit");
		p.add(Projections.property("e.estabelecimentoResponsavel"), "estabelecimentoResponsavel");
		p.add(Projections.property("emp.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("e.id", engenheiroResponsavelId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(EngenheiroResponsavel.class));

		return (EngenheiroResponsavel) criteria.uniqueResult();
	}
	
	public Collection<EngenheiroResponsavel> findResponsaveisPorEstabelecimento(Long empresaId, Long estabelecimentoId)
	{
		Criteria criteria = getSession().createCriteria(EngenheiroResponsavel.class, "eng");
		criteria.createCriteria("eng.estabelecimentos", "est", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("eng.id"), "id");
		p.add(Projections.property("eng.nome"), "nome");
		p.add(Projections.property("eng.inicio"), "inicio");
		p.add(Projections.property("eng.fim"), "fim");
		p.add(Projections.property("eng.crea"), "crea");
		p.add(Projections.property("eng.nit"), "nit");
		p.add(Projections.property("eng.estabelecimentoResponsavel"), "estabelecimentoResponsavel");
		p.add(Projections.property("eng.empresa.id"), "empresaIdProjection");

		criteria.setProjection(p);

		criteria.add(Expression.eq("eng.empresa.id", empresaId));
		criteria.add(Expression.or(Expression.eq("eng.estabelecimentoResponsavel", TipoEstabelecimentoResponsavel.TODOS), Expression.eq("est.id", estabelecimentoId)));
		
		// Esta ordem é importante para a montagem do relatório
		criteria.addOrder(Order.asc("eng.inicio"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(EngenheiroResponsavel.class));

		return criteria.list();
	}
}