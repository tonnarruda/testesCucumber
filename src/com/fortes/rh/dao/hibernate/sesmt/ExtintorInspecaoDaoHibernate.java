package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;

@SuppressWarnings("unchecked")
public class ExtintorInspecaoDaoHibernate extends GenericDaoHibernate<ExtintorInspecao> implements ExtintorInspecaoDao
{
	public Collection<ExtintorInspecao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade, String localizacao)
	{
		Criteria criteria = montaConsultaFind(false, empresaId, estabelecimentoId, extintorId, inicio, fim, regularidade, localizacao);

		if(pagingSize != 0)
        {
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
        }

		return criteria.list();
	}

	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade)
	{
		Criteria criteria = montaConsultaFind(true, empresaId, estabelecimentoId, extintorId, inicio, fim, regularidade, null);

		return (Integer)criteria.uniqueResult();
	}

	private Criteria montaConsultaFind(boolean isCount, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade, String localizacao)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "i");
		criteria.createCriteria("i.extintor", "e");
		criteria.createCriteria("e.historicoExtintores", "he");
				
		DetachedCriteria maxData = DetachedCriteria.forClass(HistoricoExtintor.class, "he2")
													.setProjection( Projections.max("data") )
													.add(Restrictions.eqProperty("he2.extintor.id", "e.id"));
		
		if (isCount)
		{
			criteria.setProjection(Projections.rowCount());
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		else
		{
			ProjectionList p = Projections.projectionList().create();
			p.add(Projections.property("i.id"), "id");
			p.add(Projections.property("i.observacao"), "observacao");
			p.add(Projections.property("i.data"), "data");
			p.add(Projections.property("he.localizacao"), "projectionExtintorLocalizacao");
			p.add(Projections.property("he.estabelecimento.id"), "projectionExtintorEstabelecimentoId");
			p.add(Projections.property("e.numeroCilindro"), "projectionExtintorNumeroCilindro");
			p.add(Projections.property("e.tipo"), "projectionExtintorTipo");
			
			criteria.setProjection(p);
			criteria.setFetchMode("e", FetchMode.JOIN);
			criteria.addOrder(Order.asc("i.data"));
			criteria.setResultTransformer(new AliasToBeanResultTransformer(ExtintorInspecao.class));
		}
		
		criteria.add( Property.forName("he.data").eq(maxData) );
		criteria.add(Expression.eq("e.empresa.id", empresaId));

		if (estabelecimentoId != null)
			criteria.add(Expression.eq("he.estabelecimento.id", estabelecimentoId));

		if (extintorId != null)
			criteria.add(Expression.eq("e.id", extintorId));
		
		if (localizacao != null)
			criteria.add(Expression.like("he.localizacao", "%" + localizacao + "%").ignoreCase());

		if (inicio != null)
		{
			if (fim != null)
				criteria.add(Expression.between("data", inicio, fim));
			else
				criteria.add(Expression.eq("data", inicio));
		}

		if(regularidade == '1') {
			criteria.add(Expression.isEmpty("itens"));
		}
		
		if(regularidade == '2') {
			criteria.add(Expression.isNotEmpty("itens"));
		}
		
		return criteria;
	}

	public Collection<String> findEmpresasResponsaveisDistinct(Long empresaId)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "inspecao");
		criteria.createCriteria("inspecao.extintor", "extintor");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("inspecao.empresaResponsavel"), "empresaResponsavel");

		criteria.setProjection(p);
		criteria.setProjection(Projections.distinct(p));
		criteria.addOrder(Order.asc("inspecao.empresaResponsavel"));

		criteria.add(Expression.eq("extintor.empresa.id", empresaId));
		return criteria.list();
	}

	public Collection<ExtintorInspecao> findInspecoesVencidas(Long estabelecimentoId, Date dataVencimento)
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ExtintorInspecao(ei.id, ei.data, e.periodoMaxInspecao, he.localizacao, e.numeroCilindro, e.tipo) ");

		hql.append("from ExtintorInspecao as ei ");
		hql.append("left join ei.extintor as e ");
		hql.append("inner join e.historicoExtintores as he ");
		hql.append("where he.estabelecimento.id = :estabelecimentoId ");
		hql.append("and e.ativo = :ativo ");
		hql.append("and (:vencimento - ei.data) >= (e.periodoMaxInspecao * 30) ");

		hql.append("and ei.data = (select max(ei2.data) ");
		hql.append("                 from ExtintorInspecao as ei2 ");
		hql.append("                 where ei2.extintor.id = e.id) ");
		
		hql.append("and he.data = (select max(he2.data) ");
		hql.append("                 from HistoricoExtintor as he2 ");
		hql.append("                 where he2.extintor.id = e.id) ");
		
		hql.append("order by he.localizacao, (ei.data + (e.periodoMaxInspecao * 30))");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("vencimento", dataVencimento);
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setBoolean("ativo", true);

		return query.list();
	}

	public ExtintorInspecao findByIdProjection(Long extintorInspecaoId) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "ei");
		criteria.createCriteria("ei.extintor", "e");
		criteria.createCriteria("e.historicoExtintores", "he");
		criteria.createCriteria("he.estabelecimento", "est");

		DetachedCriteria maxData = DetachedCriteria.forClass(HistoricoExtintor.class, "he2")
													.setProjection( Projections.max("data") )
													.add(Restrictions.eqProperty("he2.extintor.id", "e.id"));
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("ei.id"), "id");
		p.add(Projections.property("ei.observacao"), "observacao");
		p.add(Projections.property("ei.data"), "data");
		p.add(Projections.property("he.localizacao"), "projectionExtintorLocalizacao");
		p.add(Projections.property("he.estabelecimento.id"), "projectionExtintorEstabelecimentoId");
		p.add(Projections.property("e.numeroCilindro"), "projectionExtintorNumeroCilindro");
		p.add(Projections.property("e.tipo"), "projectionExtintorTipo");

		criteria.setProjection(p);
		
		criteria.add( Property.forName("he.data").eq(maxData) );

		criteria.add(Expression.eq("ei.id", extintorInspecaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (ExtintorInspecao) criteria.uniqueResult();
	}
}