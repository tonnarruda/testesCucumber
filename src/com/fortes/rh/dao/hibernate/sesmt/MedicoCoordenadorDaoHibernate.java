package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

@SuppressWarnings("unchecked")
public class MedicoCoordenadorDaoHibernate extends GenericDaoHibernate<MedicoCoordenador> implements MedicoCoordenadorDao
{
	public MedicoCoordenador findByDataEmpresa(Long empresaId, Date data)
	{
		Criteria criteria = getSession().createCriteria(MedicoCoordenador.class, "m");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("m.id"), "id");
		p.add(Projections.property("m.nome"), "nome");
		p.add(Projections.property("m.crm"), "crm");
		p.add(Projections.property("m.nit"), "nit");

		criteria.setProjection(p);

		criteria.add(Expression.eq("m.empresa.id", empresaId));
		criteria.add(Expression.le("m.inicio", data));

		criteria.addOrder(Order.desc("m.inicio"));

		criteria.setMaxResults(1);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(MedicoCoordenador.class));

		return (MedicoCoordenador) criteria.uniqueResult();
	}

	public Collection<MedicoCoordenador> findResponsaveisPorEstabelecimento(Long empresaId, Long estabelecimentoId)
	{
		Date hoje = new Date();
		Criteria criteria = getSession().createCriteria(MedicoCoordenador.class, "m");
		criteria.createCriteria("m.estabelecimentos", "est", Criteria.LEFT_JOIN);

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("m.id"), "id");
		p.add(Projections.property("m.inicio"), "inicio");
		p.add(Projections.property("m.fim"), "fim");
		p.add(Projections.property("m.nit"), "nit");
		p.add(Projections.property("m.nome"), "nome");
		p.add(Projections.property("m.crm"), "crm");
		p.add(Projections.property("m.registro"), "registro");
		p.add(Projections.property("m.estabelecimentoResponsavel"), "estabelecimentoResponsavel");

		criteria.setProjection(p);

		criteria.add(Expression.eq("m.empresa.id", empresaId));
		criteria.add(Expression.le("m.inicio", hoje));
		criteria.add(Expression.or(Expression.eq("m.estabelecimentoResponsavel", TipoEstabelecimentoResponsavel.TODOS), Expression.eq("est.id", estabelecimentoId)));
		
		criteria.addOrder(Order.asc("m.inicio"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(MedicoCoordenador.class));

		return criteria.list();
	}

	public MedicoCoordenador findByIdProjection(Long medicoCoordenadorId)
	{
		Criteria criteria = getSession().createCriteria(MedicoCoordenador.class, "m");
		criteria.createCriteria("m.empresa", "e");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("m.id"), "id");
		p.add(Projections.property("m.nome"), "nome");
		p.add(Projections.property("m.inicio"), "inicio");
		p.add(Projections.property("m.fim"), "fim");
		p.add(Projections.property("m.crm"), "crm");
		p.add(Projections.property("m.nit"), "nit");
		p.add(Projections.property("m.registro"), "registro");
		p.add(Projections.property("m.especialidade"), "especialidade");
		p.add(Projections.property("m.estabelecimentoResponsavel"), "estabelecimentoResponsavel");
		p.add(Projections.property("e.id"), "projectionEmpresaId");

		criteria.setProjection(p);

		criteria.add(Expression.eq("m.id", medicoCoordenadorId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(MedicoCoordenador.class));

		return (MedicoCoordenador) criteria.uniqueResult();
	}

}