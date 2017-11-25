package com.fortes.rh.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class HistoricoFuncaoDaoHibernate extends GenericDaoHibernate<HistoricoFuncao> implements HistoricoFuncaoDao
{
	public Collection<HistoricoFuncao> findHistoricoByFuncoesId(Collection<Long> idsFuncoes, Date dataAdmissao, Date dataPpp)
	{
		String hql = " select new HistoricoFuncao(hf.data, hf.descricao, hf.funcao)"+
		 " from HistoricoFuncao hf "+
		 " left join hf.funcao f "+
		 " where f.id in (:funIds) "+
		 " and hf.data < :dataFim "+
		 " and hf.data >= (select max(hf2.data) "+
				  " from HistoricoFuncao hf2 "+
				  " where hf2.funcao.id = hf.funcao.id "+
				  " and hf2.data <= :dataIni)" +
	     "order by hf.data " ;

		Query query = getSession().createQuery(hql);
		query.setDate("dataIni", dataAdmissao);
		query.setDate("dataFim", dataPpp);
		query.setParameterList("funIds", idsFuncoes, Hibernate.LONG);

		return query.list();
	}

	public Collection<HistoricoFuncao> getHistoricosByDateFuncaos(Collection<Long> funcaoIds, Date data)
	{
		Criteria criteria = getSession().createCriteria(HistoricoFuncao.class,"hf");
		criteria.createCriteria("hf.funcao", "f");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hf.id"), "id");
		p.add(Projections.property("hf.data"), "data");
		p.add(Projections.property("hf.descricao"), "descricao");
		p.add(Projections.property("f.id"), "projectionFuncaoId");
		p.add(Projections.property("f.nome"), "projectionFuncaoNome");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.in("f.id", funcaoIds));
		criteria.add(Expression.le("hf.data", data));

		criteria.addOrder(Order.asc("f.nome"));
		criteria.addOrder(Order.desc("hf.data"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoFuncao.class));

		return criteria.list();
	}

	public void removeByFuncoes(Long[] funcaoIds)
	{
		String hql = "delete HistoricoFuncao where funcao.id in (:funcaoIds)";

		Query query = getSession().createQuery(hql);
		query.setParameterList("funcaoIds", funcaoIds, Hibernate.LONG);

		query.executeUpdate();
	}

	public HistoricoFuncao findByIdProjection(Long historicoFuncaoId)
	{
		Criteria criteria = getSession().createCriteria(HistoricoFuncao.class, "hf");

		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hf.id"), "id");
		p.add(Projections.property("hf.data"), "data");
		p.add(Projections.property("hf.descricao"), "descricao");
		p.add(Projections.property("hf.funcao.id"), "projectionFuncaoId");
		criteria.setProjection(Projections.distinct(p));

		criteria.add(Expression.eq("hf.id", historicoFuncaoId));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoFuncao.class));

		return (HistoricoFuncao) criteria.uniqueResult();
	}

	public HistoricoFuncao findUltimoHistoricoAteData(Long funcaoId, Date data)
	{
		DetachedCriteria subQuery = DetachedCriteria.forClass(HistoricoFuncao.class, "hf2")
				.setProjection(Projections.max("hf2.data"))
				.add(Restrictions.eqProperty("hf2.funcao.id", "f.id"));
		
		if(data != null)
			subQuery.add(Restrictions.le("hf2.data", data));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "hf");
		criteria.createCriteria("hf.funcao", "f", Criteria.INNER_JOIN);
		criteria.createCriteria("hf.riscoFuncaos", "rf", Criteria.LEFT_JOIN);
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "funcaoId");
		p.add(Projections.property("hf.codigoCbo"), "codigoCbo");
		p.add(Projections.property("rf.id"), "ricoFuncaoId");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("f.id", funcaoId));
		criteria.add(Subqueries.propertyGe("hf.data", subQuery));
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setMaxResults(1);
		
		return (HistoricoFuncao) criteria.uniqueResult();
	}

	public Collection<HistoricoFuncao> findEpis(Collection<Long> funcaoIds, Date data)
	{
		if(funcaoIds == null || funcaoIds.isEmpty())
			return null;
		
		StringBuilder hql = new StringBuilder("select new HistoricoFuncao(f.id, hf.funcaoNome, e.id, e.nome) ");
		hql.append("from HistoricoFuncao hf ");
		hql.append("left join hf.funcao f ");
		hql.append("left join hf.epis e ");
			hql.append("where hf.data = (select max(hf2.data) ");
							hql.append("from HistoricoFuncao hf2 ");
							hql.append("where hf2.funcao.id = hf.funcao.id ");
							hql.append("and hf2.data <= :dataHist) ");
			hql.append("and f.id in (:funcaoIds) ");
			hql.append("and e.id is not null ");
		hql.append("order by f.nome, e.nome ");

		Query query = getSession().createQuery(hql.toString());
		query.setDate("dataHist", data);
		query.setParameterList("funcaoIds", funcaoIds, Hibernate.LONG);

		Collection<HistoricoFuncao> historicosDistinct = new ArrayList<HistoricoFuncao>();
		List<HistoricoFuncao> historicos = query.list();
		
		Map<Funcao, Collection<Epi>> episPorFuncao = new HashMap<Funcao, Collection<Epi>>();
		
		for (HistoricoFuncao historicoFuncao : historicos)
		{
			if(episPorFuncao.get(historicoFuncao.getFuncao()) == null)
				episPorFuncao.put(historicoFuncao.getFuncao(), new ArrayList<Epi>());

			episPorFuncao.get(historicoFuncao.getFuncao()).add(historicoFuncao.getEpi());
		}
		
		Collection<Funcao> funcaos = episPorFuncao.keySet();
		
		for (Funcao chave : funcaos)
		{
			HistoricoFuncao historicoFuncao = new HistoricoFuncao();
			historicoFuncao.setFuncao(chave);
			historicoFuncao.setEpis(episPorFuncao.get(chave));
			
			historicosDistinct.add(historicoFuncao);			
		}
		
		return historicosDistinct;
	}

	public HistoricoFuncao findByData(Date data, Long historicoFuncaoId, Long funcaoId) 
	{
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "hf");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("descricao"), "descricao");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("hf.funcao.id", funcaoId));
		criteria.add(Expression.eq("hf.data", data));
		
		if (historicoFuncaoId != null)
			criteria.add(Expression.ne("hf.id", historicoFuncaoId));
		
		criteria.setMaxResults(1);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (HistoricoFuncao)criteria.uniqueResult();
	}

	public Collection<Funcao> findByFuncoes(Date data, Long[] funcoesCheck) 
	{
		DetachedCriteria maxData = DetachedCriteria.forClass(HistoricoFuncao.class, "hf2")
				.setProjection( Projections.max("data") )
				.add(Restrictions.le("hf2.data", data))
				.add(Restrictions.eqProperty("hf2.funcao.id", "f.id"));

		Criteria criteria = getSession().createCriteria(getEntityClass(), "hf");
		criteria.createCriteria("hf.funcao", "f");
		criteria.createCriteria("hf.exames", "e");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("f.id"), "id");
		p.add(Projections.property("f.nome"), "nome");
		p.add(Projections.property("e.nome"), "exameNome");
		criteria.setProjection(p);
		
		if (LongUtil.arrayIsNotEmpty(funcoesCheck))
			criteria.add(Expression.in("f.id", funcoesCheck));
		
		criteria.add( Property.forName("hf.data").eq(maxData) );

		criteria.addOrder(Order.asc("f.nome"));
		criteria.addOrder(Order.asc("e.nome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Funcao.class));

		return criteria.list();
	}

	public Collection<HistoricoFuncao> findByFuncao(Long funcaoId) 
	{
		Criteria criteria = getSession().createCriteria(getEntityClass(), "hf");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("hf.id"), "id");
		p.add(Projections.property("hf.data"), "data");
		p.add(Projections.property("hf.descricao"), "descricao");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("hf.funcao.id", funcaoId));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(HistoricoFuncao.class));
		
		return criteria.list();
	}
	
	public HistoricoFuncao findByFuncaoAndData(Long funcaoId, Date data) {
		DetachedCriteria subQueryHf = DetachedCriteria.forClass(HistoricoFuncao.class, "hf2")
																.setProjection(Projections.max("hf2.data"))
																.add(Restrictions.eq("hf2.funcao.id", funcaoId))
																.add(Restrictions.le("hf2.data", data));
		
		Criteria criteria = getSession().createCriteria(getEntityClass(), "hf");
		
		ProjectionList p = Projections.projectionList().create();
		p.add(Projections.property("id"), "id");
		p.add(Projections.property("funcaoNome"), "funcaoNome");
		p.add(Projections.property("descricao"), "descricao");
		p.add(Projections.property("normasInternas"), "normasInternas");
		criteria.setProjection(p);
		
		criteria.add(Expression.eq("hf.funcao.id", funcaoId));
		criteria.add(Property.forName("hf.data").eq(subQueryHf));
		
		criteria.setMaxResults(1);
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getEntityClass()));

		return (HistoricoFuncao)criteria.uniqueResult();
	}

	public List<DadosAmbienteOuFuncaoRisco> findDadosNoPeriodo(Long funcaoId, Date dataIni, Date dataFim) 
	{
		StringBuilder hql = new StringBuilder("select new com.fortes.rh.model.sesmt.relatorio.DadosAmbienteOuFuncaoRisco(hf.funcao.id, r.id, r.descricao, r.grupoRisco, rf.epcEficaz, hf.data) ");
		hql.append("from HistoricoFuncao hf ");
		hql.append("join hf.riscoFuncaos rf ");
		hql.append("join rf.risco r ");
		hql.append("where hf.funcao.id = :funcaoId ");
		hql.append("and hf.data < :dataFim ");
		hql.append("and hf.data >= (select max(hf2.data) from HistoricoFuncao hf2 ");
		hql.append("				where hf2.funcao.id = :funcaoId ");
		hql.append("				and hf2.data <= :dataIni) ");
		hql.append("order by hf.data ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setLong("funcaoId", funcaoId);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		
		return query.list();
	}
}