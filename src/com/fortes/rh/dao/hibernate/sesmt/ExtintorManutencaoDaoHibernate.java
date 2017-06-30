package com.fortes.rh.dao.hibernate.sesmt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoDao;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("unchecked")
public class ExtintorManutencaoDaoHibernate extends GenericDaoHibernate<ExtintorManutencao> implements ExtintorManutencaoDao
{
	public Collection<ExtintorManutencao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno, String localizacao)
	{
		Query query = montaQueryFind(false, empresaId, estabelecimentoId, extintorId, inicio, fim, semRetorno, localizacao);

		if(pagingSize != 0)
        {
			query.setFirstResult(((page - 1) * pagingSize));
			query.setMaxResults(pagingSize);
        }

		return query.list();
	}

	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno)
	{
		Query query = montaQueryFind(true, empresaId, estabelecimentoId, extintorId, inicio, fim, semRetorno, null);

		return (Integer)query.uniqueResult();
	}

	private Query montaQueryFind(boolean isCount, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno, String localizacao)
	{
		StringBuffer hql = new StringBuffer();
		
		hql.append("SELECT ");
		
		if (isCount)
			hql.append("COUNT(*) ");
		else
			hql.append("new ExtintorManutencao(em, he) ");
				
		hql.append("FROM ExtintorManutencao em ");
		hql.append("LEFT JOIN em.extintor e ");
		hql.append("LEFT JOIN e.historicoExtintores he ");
		hql.append("LEFT JOIN he.estabelecimento est ");
		hql.append("WHERE he.data = (SELECT MAX(he2.data) FROM HistoricoExtintor he2 WHERE he2.extintor.id = e.id) ");
		hql.append("AND e.empresa.id = :empresaId ");
		
		if (estabelecimentoId != null)
			hql.append("AND he.estabelecimento.id = :estabelecimentoId ");

		if (extintorId != null)
			hql.append("AND e.id = :extintorId ");
		
		if (localizacao != null)
			hql.append("AND LOWER(he.localizacao) LIKE :localizacao ");
		
		if (inicio != null)
		{
			if (fim != null)
				hql.append("AND em.saida BETWEEN :inicio AND :fim ");
			else
				hql.append("AND em.saida = :inicio ");
		}
		
		if (semRetorno)
			hql.append("AND em.retorno IS NULL ");
		
		if (!isCount)
		{
			hql.append("GROUP BY em.id, he.id, em.saida ");
			hql.append("ORDER BY em.saida");
		}
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		
		if (estabelecimentoId != null)
			query.setLong("estabelecimentoId", estabelecimentoId);
		
		if (extintorId != null)
			query.setLong("extintorId", extintorId);
		
		if (localizacao != null)
			query.setString("localizacao", "%" + localizacao.toLowerCase() + "%");
		
		if (inicio != null)
		{
			query.setDate("inicio", inicio);
			if (fim != null)
				query.setDate("fim", fim);
		}
		
		return query;
	}
	
	public Collection<ExtintorManutencao> findManutencaoVencida(Long estabelecimentoId, Date dataVencimento, String motivo)
	{
		String periodoMax = "periodoMaxRecarga";
		Long manutencaoServicoVencidoId = 1L; // ID fixo do ExtintorManutencaoServico
		
		if(motivo.equals(MotivoExtintorManutencao.PRAZO_HIDROSTATICO))
		{
			periodoMax = "periodoMaxHidrostatico";
			manutencaoServicoVencidoId = 3L;
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("select em.id as id, em.saida as saida, e. " + periodoMax + " as periodo, he.localizacao as local, e.numeroCilindro as numeroCilindro, e.tipo as tipo ");

		sql.append("from ExtintorManutencao as em ");
		sql.append("left join extintor as e on em.extintor_id = e.id ");
		sql.append("inner join historicoExtintor as he on he.extintor_id = e.id ");
		sql.append("left join extintormanutencao_extintormanutencaoservico ee on ee.ExtintorManutencao_id = em.id ");
		sql.append("where he.estabelecimento_id = :estabelecimentoId ");
		sql.append("and e.ativo = true ");
		sql.append("and :vencimento  >= (em.saida + cast((coalesce(e." + periodoMax + ",0) || ' month') as interval)) ");
		sql.append("and ee.servicos_id = :manutencaoServicoVencidoId ");
		
		sql.append("and em.saida = (select max(em2.saida) ");
		sql.append("                 from ExtintorManutencao as em2 ");
		sql.append(" 				 left join extintormanutencao_extintormanutencaoservico ee2 on ee2.ExtintorManutencao_id = em2.id ");
		sql.append("                 where em2.extintor_id = e.id ");
		sql.append("                 and ee2.servicos_id = :manutencaoServicoVencidoId ");
		sql.append("                  ) ");

		sql.append("and he.data = (select max(he2.data) ");
		sql.append("                 from HistoricoExtintor as he2 ");
		sql.append("                 where he2.extintor_id = e.id ");
		sql.append("                  ) ");
		
		sql.append("order by he.localizacao, (em.saida + cast((coalesce(e." + periodoMax + ",0) || ' month') as interval)) ");

		Query query = getSession().createSQLQuery(sql.toString());

		query.setDate("vencimento", dataVencimento);
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setLong("manutencaoServicoVencidoId", manutencaoServicoVencidoId);

		@SuppressWarnings("rawtypes")
		List resultado = query.list();
		Collection<ExtintorManutencao> extintorManutencoes = new ArrayList<ExtintorManutencao>();
		
		for (Iterator<Object[]> it = resultado.iterator(); it.hasNext();){
			Object[] res = it.next();
			ExtintorManutencao extintorManutencao = new ExtintorManutencao();
			extintorManutencao.setId(((BigInteger)res[0]).longValue());
			extintorManutencao.setSaida((Date)res[1]);
			
			Integer periodoMaxInt = (Integer)res[2];
			if(periodoMaxInt != null)
				extintorManutencao.setVencimento(DateUtil.incrementaMes(extintorManutencao.getSaida(), periodoMaxInt));
			
			extintorManutencao.setExtintor(new Extintor());
			extintorManutencao.getExtintor().setHistoricoExtintores(Arrays.asList(new HistoricoExtintor()));
			extintorManutencao.getExtintor().getUltimoHistorico().setLocalizacao((String)res[3]);
			extintorManutencao.getExtintor().setNumeroCilindro((Integer)res[4]);
			extintorManutencao.getExtintor().setTipo((String)res[5]);
			
			extintorManutencoes.add(extintorManutencao);
		}
		
		return extintorManutencoes;
	}

	public ExtintorManutencao findByIdProjection(Long extintorManutencaoId) 
	{
		Query query = getSession().createQuery("SELECT new ExtintorManutencao(em, he) FROM ExtintorManutencao em " +
				"LEFT JOIN em.servicos s " +
				"LEFT JOIN em.extintor e " +
				"LEFT JOIN e.historicoExtintores he " +
				"LEFT JOIN he.estabelecimento est " +
				"WHERE em.id = :extintorManutencaoId " +
				"AND he.data = (SELECT MAX(he2.data) FROM HistoricoExtintor he2 WHERE he2.extintor.id = e.id) " +
				"GROUP BY em.id, he.id");

		query.setLong("extintorManutencaoId", extintorManutencaoId);
		
		return (ExtintorManutencao) query.uniqueResult();
	}
}