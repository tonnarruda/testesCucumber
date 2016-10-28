package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.model.sesmt.ExtintorInspecao;

@Component
@SuppressWarnings("unchecked")
public class ExtintorInspecaoDaoHibernate extends GenericDaoHibernate<ExtintorInspecao> implements ExtintorInspecaoDao
{
	public Collection<ExtintorInspecao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade, String localizacao)
	{
		Query query = montaQueryFind(false, empresaId, estabelecimentoId, extintorId, inicio, fim, regularidade, localizacao);
		
		if(pagingSize != 0)
        {
			query.setFirstResult(((page - 1) * pagingSize));
			query.setMaxResults(pagingSize);
        }

		return query.list();
	}

	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade)
	{
		Query query = montaQueryFind(true, empresaId, estabelecimentoId, extintorId, inicio, fim, regularidade, null);

		return (Integer)query.uniqueResult();
	}

	private Query montaQueryFind(boolean isCount, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, char regularidade, String localizacao)
	{
		StringBuffer hql = new StringBuffer();
		
		hql.append("SELECT ");
		
		if (isCount)
			hql.append("COUNT(*) ");
		else
			hql.append("new ExtintorInspecao(ei, he) ");
				
		hql.append("FROM ExtintorInspecao ei ");
		hql.append("LEFT JOIN ei.extintor e ");
		hql.append("LEFT JOIN e.historicoExtintores he ");
		hql.append("LEFT JOIN he.estabelecimento est ");
		hql.append("WHERE he.data = (SELECT MAX(he2.data) FROM HistoricoExtintor he2 WHERE he2.extintor.id = e.id) ");
		hql.append("AND e.empresa.id = :empresaId ");
		
		if (estabelecimentoId != null)
			hql.append("AND he.estabelecimento.id = :estabelecimentoId ");

		if (extintorId != null)
			hql.append("AND e.id = :extintorId ");
		
		if (localizacao != null && !"".equals(localizacao))
			hql.append("AND LOWER(he.localizacao) LIKE :localizacao ");
		
		if (inicio != null)
		{
			if (fim != null)
				hql.append("AND ei.data BETWEEN :inicio AND :fim ");
			else
				hql.append("AND ei.data = :inicio ");
		}
		
		if (regularidade == '1')
			hql.append("AND ei.itens IS EMPTY ");

		if (regularidade == '2')
			hql.append("AND ei.itens IS NOT EMPTY ");
		
		if (!isCount)
		{
			hql.append("GROUP BY ei.id, he.id, ei.data ");
			hql.append("ORDER BY ei.data");
		}
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		
		if (estabelecimentoId != null)
			query.setLong("estabelecimentoId", estabelecimentoId);
		
		if (extintorId != null)
			query.setLong("extintorId", extintorId);
		
		if (localizacao != null && !"".equals(localizacao))
			query.setString("localizacao", "%" + localizacao.toLowerCase() + "%");
		
		if (inicio != null)
		{
			query.setDate("inicio", inicio);
			if (fim != null)
				query.setDate("fim", fim);
		}
		
		return query;
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

	public ExtintorInspecao findByIdProjection(Long extintorInspecaoId) 
	{
		Query query = getSession().createQuery("SELECT new ExtintorInspecao(ei, he) FROM ExtintorInspecao ei " +
				"LEFT JOIN ei.itens i " +
				"LEFT JOIN ei.extintor e " +
				"LEFT JOIN e.historicoExtintores he " +
				"LEFT JOIN he.estabelecimento est " +
				"WHERE ei.id = :extintorInspecaoId " +
				"AND he.data = (SELECT MAX(he2.data) FROM HistoricoExtintor he2 WHERE he2.extintor.id = e.id) " +
		"GROUP BY ei.id, he.id ");
		
		query.setLong("extintorInspecaoId", extintorInspecaoId);
		
		return (ExtintorInspecao) query.uniqueResult();
	}
}