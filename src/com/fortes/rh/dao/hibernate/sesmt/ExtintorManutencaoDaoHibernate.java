package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoDao;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;

@Component
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
		
		StringBuilder hql = new StringBuilder();
		hql.append("select new ExtintorManutencao(em.id, em.saida, e. " + periodoMax + ", he.localizacao, e.numeroCilindro, e.tipo) ");

		hql.append("from ExtintorManutencao as em ");
		hql.append("left join em.extintor as e ");
		hql.append("inner join e.historicoExtintores as he ");
		hql.append("where he.estabelecimento.id = :estabelecimentoId ");
		hql.append("and e.ativo = true ");
		hql.append("and (:vencimento - em.saida) >= (e. " + periodoMax + " * 30) ");
		hql.append("and em.servicos.id = :manutencaoServicoVencidoId ");
		
		hql.append("and em.saida = (select max(em2.saida) ");
		hql.append("                 from ExtintorManutencao as em2 ");
		hql.append("                 where em2.extintor.id = e.id ");
		hql.append("                 and em2.servicos.id = :manutencaoServicoVencidoId ");
		hql.append("                  ) ");

		hql.append("and he.data = (select max(he2.data) ");
		hql.append("                 from HistoricoExtintor as he2 ");
		hql.append("                 where he2.extintor.id = e.id ");
		hql.append("                  ) ");
		
		hql.append("order by he.localizacao, (em.saida + (e. " + periodoMax + " * 30)) ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("vencimento", dataVencimento);
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setLong("manutencaoServicoVencidoId", manutencaoServicoVencidoId);

		return query.list();
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