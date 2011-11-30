package com.fortes.rh.dao.hibernate.sesmt;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Cat;

@SuppressWarnings("unchecked")
public class CatDaoHibernate extends GenericDaoHibernate<Cat> implements CatDao
{
	public Collection<Cat> findByColaborador(Colaborador colaborador)
	{
		// refactoring para reaproveitar código
		return  findCatsColaboradorByDate(colaborador, null);
	}

	public Collection<Cat> findCatsColaboradorByDate(Colaborador colaborador, Date data)
	{
		Criteria criteria = getSession().createCriteria(Cat.class,"c");
		criteria.createCriteria("c.colaborador","cc");

		ProjectionList p = Projections.projectionList().create();

		p.add(Projections.property("c.id"), "id");
		p.add(Projections.property("cc.id"), "colaboradorId");
		p.add(Projections.property("cc.nomeComercial"), "nomeComercial");
		p.add(Projections.property("c.data"), "data");
		p.add(Projections.property("c.numeroCat"), "numeroCat");
		p.add(Projections.property("c.observacao"), "observacao");
		p.add(Projections.property("c.gerouAfastamento"), "gerouAfastamento");

		criteria.setProjection(p);
		criteria.add(Expression.eq("cc.id", colaborador.getId()));

		if(data != null)
			criteria.add(Expression.le("c.data", data));

		criteria.addOrder(Order.desc("c.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Cat.class));

		return  criteria.list();
	}

	public Collection<Cat> findAllSelect(Long empresaId, Date inicio, Date fim, Long[] estabelecimentoIds, String nomeBusca, Long[] areaIds)
	{

		StringBuilder hql = new StringBuilder("select new Cat(c.id, c.data, c.numeroCat, c.observacao, c.gerouAfastamento, co.id, co.nome, es.nome, ao.id) ");

		hql.append("from Cat c ");
		hql.append("join c.colaborador co ");
		hql.append("join co.historicoColaboradors hc ");
		hql.append("join hc.estabelecimento as es ");
		hql.append("join hc.areaOrganizacional as ao ");

		hql.append("where co.empresa.id = :empresaId ");

		if (isNotBlank(nomeBusca))
			hql.append("and lower(co.nome) like :nome ");

		if (estabelecimentoIds.length > 0)
			hql.append("and es.id in (:estabelecimentoIds) ");
		
		if (areaIds.length > 0)
			hql.append("and ao.id in (:areaIds) ");

		if (inicio != null && fim != null)
			hql.append("and c.data between :inicio and :fim ");

		hql.append("and hc.data = ( ");
		hql.append("select max(hc2.data) " );
		hql.append("	from HistoricoColaborador as hc2 ");
		hql.append("	where hc2.colaborador.id = co.id ");
		hql.append("	and hc2.data <= :ultimaData and hc2.status = :status ");
		hql.append(") ");

		hql.append("ORDER BY c.data DESC, co.nome ASC");

		Query query = getSession().createQuery(hql.toString());

		if (inicio != null && fim != null)
		{
			query.setDate("inicio", inicio);
			query.setDate("fim", fim);
		}

		query.setLong("empresaId", empresaId);
		query.setDate("ultimaData", new Date());

		if (isNotBlank(nomeBusca))
			query.setString("nome", "%" + nomeBusca.toLowerCase() + "%");

		if (estabelecimentoIds.length > 0)
			query.setParameterList("estabelecimentoIds", estabelecimentoIds, Hibernate.LONG);
		
		if (areaIds.length > 0)
			query.setParameterList("areaIds", areaIds, Hibernate.LONG);

		query.setInteger("status", StatusRetornoAC.CONFIRMADO);
		
		return query.list();
	}

	public Collection<Object[]> getCatsRelatorio(Long estabelecimentoId, Date inicio, Date fim)
	{
		StringBuilder hql = new StringBuilder("select cat.data, cat.gerouAfastamento");
		hql.append(" from HistoricoColaborador hc");
		hql.append(" left join hc.colaborador as c");
		hql.append(" join c.cats cat");
		hql.append(" where hc.estabelecimento.id = :estabelecimentoId");
		hql.append(" and hc.data = (select max(hc2.data)");
		hql.append("				from HistoricoColaborador hc2");
		hql.append(" 				where hc2.data <= cat.data  and hc2.status = :status ");
		hql.append(" 				and c.id = hc2.colaborador.id)");
		hql.append(" and cat.data between :dataIni and :dataFim");
		hql.append(" order by cat.data");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("estabelecimentoId", estabelecimentoId);
		query.setDate("dataIni", inicio);
		query.setDate("dataFim", fim);
		query.setInteger("status", StatusRetornoAC.CONFIRMADO);

		return query.list();
		
	}

	public Cat findUltimoCat(Long empresaId) 
	{
		StringBuilder hql = new StringBuilder("from Cat cat where cat.colaborador.empresa.id = :empresaId order by cat.data desc");

		Query query = getSession().createQuery(hql.toString());
		query.setLong("empresaId", empresaId);
		query.setMaxResults(1);

		return (Cat) query.uniqueResult();
	}
}