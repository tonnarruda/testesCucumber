package com.fortes.rh.dao.hibernate.sesmt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.util.DateUtil;

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
		StringBuilder sql = new StringBuilder();
		sql.append("select ei.id, ei.data, e.periodoMaxInspecao, he.localizacao, e.numeroCilindro, e.tipo ");
		sql.append("from ExtintorInspecao as ei ");
		sql.append("left join extintor as e on ei.extintor_id = e.id ");
		sql.append("inner join historicoExtintor as he on he.extintor_id = e.id ");
		sql.append("where he.estabelecimento_id = :estabelecimentoId ");
		sql.append("and e.ativo = :ativo ");
		sql.append("and :vencimento  >= (ei.data + cast((coalesce(e.periodoMaxInspecao,0) || ' month') as interval)) ");

		sql.append("and ei.data = (select max(ei2.data) ");
		sql.append("                 from ExtintorInspecao as ei2 ");
		sql.append("                 where ei2.extintor_id = e.id) ");
		
		sql.append("and he.data = (select max(he2.data) ");
		sql.append("                 from HistoricoExtintor as he2 ");
		sql.append("                 where he2.extintor_id = e.id) ");
		
		sql.append("order by he.localizacao, (ei.data + cast((coalesce(e.periodoMaxInspecao,0) || ' month') as interval)) ");

		Query query = getSession().createSQLQuery(sql.toString());

		query.setDate("vencimento", dataVencimento);
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setBoolean("ativo", true);

		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ExtintorInspecao> extintorInspecoes = new ArrayList<ExtintorInspecao>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ExtintorInspecao extintorInspecao = new ExtintorInspecao();
			extintorInspecao.setId(((BigInteger)res[0]).longValue());
			extintorInspecao.setData((Date)res[1]);
			
			Integer periodoMaxInt = (Integer)res[2];
			if(periodoMaxInt != null)
				extintorInspecao.setVencimento(DateUtil.incrementaMes(extintorInspecao.getData(), periodoMaxInt));
			
			extintorInspecao.setExtintor(new Extintor());
			extintorInspecao.getExtintor().setHistoricoExtintores(Arrays.asList(new HistoricoExtintor()));
			extintorInspecao.getExtintor().getUltimoHistorico().setLocalizacao((String)res[3]);
			extintorInspecao.getExtintor().setNumeroCilindro((Integer)res[4]);
			extintorInspecao.getExtintor().setTipo((String)res[5]);
			
			extintorInspecoes.add(extintorInspecao);
		}
		
		return extintorInspecoes;
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