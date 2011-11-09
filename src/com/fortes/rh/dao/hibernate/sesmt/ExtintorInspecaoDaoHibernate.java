package com.fortes.rh.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.model.sesmt.ExtintorInspecao;

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
		Criteria criteria = getSession().createCriteria(getEntityClass(), "inspecao");
		criteria.createCriteria("inspecao.extintor", "extintor");
				
		if (isCount)
			criteria.setProjection(Projections.rowCount());
		else
		{
			criteria.setFetchMode("extintor", FetchMode.JOIN);
			criteria.addOrder(Order.asc("inspecao.data"));
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
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						
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
		hql.append("select new ExtintorInspecao(ei.id, ei.data, e.periodoMaxInspecao, e.localizacao, e.numeroCilindro, e.tipo) ");

		hql.append("from ExtintorInspecao as ei ");
		hql.append("left join ei.extintor as e ");
		hql.append("where e.estabelecimento.id = :estabelecimentoId ");
		hql.append("and e.ativo = :ativo ");
		hql.append("and (:vencimento - ei.data) >= (e.periodoMaxInspecao * 30) ");

		hql.append("and ei.data = (select max(ei2.data) ");
		hql.append("                 from ExtintorInspecao as ei2 ");
		hql.append("                 where ei2.extintor.id = e.id) ");
		
		hql.append("order by e.localizacao, (ei.data + (e.periodoMaxInspecao * 30))");

		Query query = getSession().createQuery(hql.toString());

		query.setDate("vencimento", dataVencimento);
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setBoolean("ativo", true);

		return query.list();
	}
}