package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.model.dicionario.TipoExtintor;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

@Component
@SuppressWarnings("unchecked")
public class ExtintorDaoHibernate extends GenericDaoHibernate<Extintor> implements ExtintorDao
{
	public Integer getCount(Long empresaId, String tipoBusca, Integer numeroBusca, Boolean ativo)
	{
		Criteria criteria = montaConsultaFind(true, empresaId, tipoBusca, numeroBusca, ativo);

		return (Integer)criteria.uniqueResult();
	}

	private Criteria montaConsultaFind(boolean isCount, Long empresaId, String tipoBusca, Integer numeroBusca, Boolean ativo)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.historicoExtintores","he");

		DetachedCriteria maxData = DetachedCriteria.forClass(HistoricoExtintor.class, "he2")
				.setProjection( Projections.max("data") )
				.add(Restrictions.eqProperty("he2.extintor.id", "e.id"));
		
		criteria.add( Property.forName("he.data").eq(maxData) );
		
		if (isCount)
			criteria.setProjection(Projections.rowCount());
		else
		{
			ProjectionList p = Projections.projectionList().create();
			p.add(Projections.property("e.id"), "id");
			p.add(Projections.property("he.localizacao"), "localizacaoProjection");
			p.add(Projections.property("e.numeroCilindro"), "numeroCilindro");
			p.add(Projections.property("e.tipo"), "tipo");
			p.add(Projections.property("e.ativo"), "ativo");

			criteria.setProjection(p);

			criteria.addOrder(Order.asc("e.tipo"));
			criteria.addOrder(Order.asc("he.localizacao"));
			criteria.addOrder(Order.asc("e.numeroCilindro"));

			criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		}

		criteria.add(Expression.eq("e.empresa.id", empresaId));

		if (isNotBlank(tipoBusca))
		{
			if(tipoBusca.equals(TipoExtintor.PO_QUIMICO_SECO))
				criteria.add(Expression.or(Expression.eq("e.tipo", TipoExtintor.PO_QUIMICO_SECO), Expression.eq("e.tipo", TipoExtintor.PO_QUIMICO_SECO_ABC)));
			else
				criteria.add(Expression.eq("e.tipo", tipoBusca));
		}

		if (numeroBusca != null)
			criteria.add(Expression.eq("e.numeroCilindro", numeroBusca));

		if (ativo != null)
			criteria.add(Expression.eq("e.ativo", ativo));

		return criteria;
	}

	public Collection<Extintor> findAllSelect(int page, int pagingSize, Long empresaId, String tipoBusca, Integer numeroBusca, Boolean ativo)
	{
		Criteria criteria = montaConsultaFind(false, empresaId, tipoBusca, numeroBusca, ativo);

		if(pagingSize != 0)
        {
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
        }

		return criteria.list();
	}

	public Collection<Extintor> findAllComHistAtual(Boolean ativo, Long estabelecimentoId, Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.historicoExtintores", "he");
		criteria.createCriteria("he.estabelecimento", "est");

		DetachedCriteria maxData = DetachedCriteria.forClass(HistoricoExtintor.class, "he2")
													.setProjection( Projections.max("data") )
													.add(Restrictions.eqProperty("he2.extintor.id", "e.id"));
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.id"), "id");
		p.add(Projections.property("he.localizacao"), "localizacaoProjection");
		p.add(Projections.property("est.id"), "estabelecimentoIdProjection");
		p.add(Projections.property("e.numeroCilindro"), "numeroCilindro");
		p.add(Projections.property("e.tipo"), "tipo");

		criteria.setProjection(p);
		criteria.addOrder(Order.asc("e.tipo"));
		criteria.addOrder(Order.asc("he.localizacao"));
		criteria.addOrder(Order.asc("e.numeroCilindro"));
		
		criteria.add( Property.forName("he.data").eq(maxData) );

		if (ativo != null)
			criteria.add(Expression.eq("e.ativo", ativo));

		if (estabelecimentoId != null && !estabelecimentoId.equals(""))
			criteria.add(Expression.eq("he.estabelecimento.id", estabelecimentoId));

		if (empresaId != null)
			criteria.add(Expression.eq("est.empresa.id", empresaId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		
		return criteria.list();
	}

	public Collection<String> findFabricantesDistinctByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("e.fabricante"), "fabricante");
		
		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("e.fabricante"));
		
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		return criteria.list();
	}

	public Collection<String> findLocalizacoesDistinctByEmpresa(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "e");
		criteria.createCriteria("e.historicoExtintores","he");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("he.localizacao"), "localizacao");
		
		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("he.localizacao"));
		
		criteria.add(Expression.eq("e.empresa.id", empresaId));
		return criteria.list();
	}
}