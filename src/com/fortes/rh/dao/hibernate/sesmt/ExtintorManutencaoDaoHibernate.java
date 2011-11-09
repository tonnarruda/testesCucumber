package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoDao;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencao;

@SuppressWarnings("unchecked")
public class ExtintorManutencaoDaoHibernate extends GenericDaoHibernate<ExtintorManutencao> implements ExtintorManutencaoDao
{
	public Collection<ExtintorManutencao> findAllSelect(int page, int pagingSize, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno, String localizacao)
	{
		Criteria criteria = montaConsultaFind(false, empresaId, estabelecimentoId, extintorId, inicio, fim, semRetorno, localizacao);

		if(pagingSize != 0)
        {
			criteria.setFirstResult(((page - 1) * pagingSize));
			criteria.setMaxResults(pagingSize);
        }

		return criteria.list();
	}

	public Integer getCount(Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno)
	{
		Criteria criteria = montaConsultaFind(true, empresaId, estabelecimentoId, extintorId, inicio, fim, semRetorno, null);

		return (Integer)criteria.uniqueResult();
	}

	private Criteria montaConsultaFind(boolean isCount, Long empresaId, Long estabelecimentoId, Long extintorId, Date inicio, Date fim, boolean semRetorno, String localizacao)
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "manutencao");
		criteria.createCriteria("manutencao.extintor", "extintor");

		if (isCount)
			criteria.setProjection(Projections.rowCount());
		else
		{
			criteria.setFetchMode("extintor", FetchMode.JOIN);
			criteria.addOrder(Order.asc("manutencao.saida"));
		}

		criteria.add(Expression.eq("extintor.empresa.id", empresaId));

		if (estabelecimentoId != null)
			criteria.add(Expression.eq("extintor.estabelecimento.id", estabelecimentoId));

		if (extintorId != null)
			criteria.add(Expression.eq("extintor.id", extintorId));
		
		if (localizacao != null)
			criteria.add(Expression.like("extintor.localizacao", "%" + localizacao + "%").ignoreCase());

		if (inicio != null)
		{
			if (fim != null)
				criteria.add(Expression.between("saida", inicio, fim));
			else
				criteria.add(Expression.eq("saida", inicio));
		}

		if (semRetorno)
			criteria.add(Expression.isNull("retorno"));

		return criteria;
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
		hql.append("select new ExtintorManutencao(em.id, em.saida, e. " + periodoMax + ", e.localizacao, e.numeroCilindro, e.tipo) ");

		hql.append("from ExtintorManutencao as em ");
		hql.append("left join em.extintor as e ");
		hql.append("where e.estabelecimento.id = :estabelecimentoId ");
		hql.append("and e.ativo = true ");
		hql.append("and (:vencimento - em.saida) >= (e. " + periodoMax + " * 30) ");
		hql.append("and em.servicos.id = :manutencaoServicoVencidoId ");
		hql.append("and em.saida = (select max(em2.saida) ");
		hql.append("                 from ExtintorManutencao as em2 ");
		hql.append("                 where em2.extintor.id = e.id ");
		hql.append("                 and em2.servicos.id = :manutencaoServicoVencidoId ");
		hql.append("                  ) ");
		
		hql.append("order by e.localizacao, (em.saida + (e. " + periodoMax + " * 30)) ");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("vencimento", dataVencimento);
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setLong("manutencaoServicoVencidoId", manutencaoServicoVencidoId);

		return query.list();
	}
}